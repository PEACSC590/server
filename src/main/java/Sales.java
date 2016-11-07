import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import org.bson.Document;

public class Sales {

	private static final int MAX_PENDING_PURCHASES = 5;
	
	private API api;

	public Sales(API api) {
		this.api = api;
	}

	public Map<String, String> buy(String userID, String itemID, String userToken) throws Exception {
		boolean success = api.userTokens.testUserTokenForUser(userID, userToken);
		if (success){
			
			Document userDocument = api.usersCollection.find(new Document("username", userID)).first();
			if (userDocument == null)
				throw new Exception("Could not find user " + userID);

			// if `numPendingPurchases` is greater than the maximum allowed, error
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			if (numPendingPurchases >= MAX_PENDING_PURCHASES)
				throw new Exception("Reached maximum number of currently pending purchases allowed");

			// increment `numPendingPurchases`
			api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", 1)));

			// update the item to be bought with the buyer id, the date this is
			// being processed, and the new status of the item
			long dateBought = System.currentTimeMillis();
			Document updates = new Document().append("buyerID", userID).append("dateBought", dateBought).append("status",
				"pending");
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));

			Document item = api.items.getItemByID(itemID);
			String itemName = item.getString("itemName");
			String sellerID = item.getString("sellerID");

			// send e-mail to buyer for confirmation
			Email.send(userID, "Pending purchase", "Your purchase of " + itemName + " is pending.");

			// send e-mail to seller notifying bought item
			Email.send(sellerID, "Buyer has bought item",
				"Buyer " + userID + " has bought your item " + itemName + ". Please confirm your sale of the item.");

			Map<String, String> output = new HashMap<>();
			output.put("status", "pending");
			output.put("numPendingPurchases", numPendingPurchases + 1 + "");
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date purchaseDate = new Date();
			output.put("dateBought", purchaseDate + "");
			return output;
		}
		else {
			Document userDocument = api.usersCollection.find(new Document("username", userID)).first();
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			Map<String, String> output = new HashMap<>();
			output.put("status", "listed");
			output.put("numPendingPurchases", numPendingPurchases + "");
			output.put("dateBought", null);
			return output;
		}
	}

	// userID = buyerID?
	public Map<String, String> cancelPendingSale(String itemID, String userID, String userToken) {
		boolean success = api.userTokens.testUserTokenForUser(userID, userToken);
		if (success){
			Document cancelSale = new Document();
			cancelSale.put("status", "listed");
			cancelSale.put("buyerID", null);
			cancelSale.put("dateBought", null);
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", cancelSale));

			api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", -1)));

			Map<String, String> output = new HashMap<>();
			output.put("status", "listed");
			return output;
		}
		else {
			Map<String, String> output = new HashMap<>();
			output.put("status", "pending");
			return output;
		}

	}

	// userID = buyerID i think?
	public Map<String, String> refuseSale(String itemID, String buyerID, String sellerID, String userToken) {
		Map<String, String> output = new HashMap<>();
		boolean success = api.userTokens.testUserTokenForUser(sellerID, userToken);
		if (success) {

		Document refuseSale = new Document();
		refuseSale.put("status", "listed");
		refuseSale.put("buyerID", null);
		refuseSale.put("dateBought", null);
		api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", refuseSale));

		api.usersCollection.updateOne(new Document("userID", buyerID),
				new Document("$inc", new Document("numPendingPurchases", -1)));
		output.put("status", "listed");
		
		}
		else {
			output.put("status", "pending");
		}
		return output;
	}

	public Map<String, String> unlist(String itemID, String sellerID, String userToken) {
		boolean success = api.userTokens.testUserTokenForUser(sellerID, userToken);
		if (success) {
	
			Document unlist = new Document();
			unlist.put("status", "hidden");
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", unlist));

			Map<String, String> output = new HashMap<>();
			output.put("status", "hidden");
			return output;
		}
		else {
			Map<String, String> output = new HashMap<>();
			output.put("status", "listed");
			return output;
		}
	}

	// this is run after the buyer has paid for the item and has received it
	public Map<String, String> sell(String itemID, String userID, String userToken) {
		boolean success = api.userTokens.testUserTokenForUser(userID, userToken);
		if (success) {
			
			Document updates = new Document();
			updates.put("status", "sold");
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));
			api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", -1)));

			Document item = api.items.getItemByID(itemID);
			String itemName = item.getString("itemName");
			String sellerID = item.getString("sellerID");

		// send e-mail to buyer
			Email.send(userID, "Successful purchase", "Successful purchase of " + itemName + ".");

			Email.send(sellerID, "Successful sale", "Successful sale of " + itemName + ".");
			Map<String, String> output = new HashMap<>();
			output.put("status", "sold");
			return output;
		}
		else{
			Map<String, String> output = new HashMap<>();
			output.put("status", "pending");
			return output;
		}
	}
	
	// method for seller to approve sale within 3 days
	public Map<String, String> sellerApproveSale(String itemID) {

		Document item = api.items.getItemByID(itemID);
		String buyerID = item.getString("buyerID");
		String itemName = item.getString("itemName");

		// check if the item has been cancelled due to inactivity
		if (item.getString("status").equals("cancelled")) {
			Map<String, String> output = new HashMap<>();
			output.put("status", "error");
			output.put("error", "seller has passed time window");
			return output;
		} else {
			Email.send(buyerID + "@exeter.edu", "Seller has confirmed your purchase",
					"Seller has confirmed your purchase of item " + itemName
							+ ". Please contact the seller for payment.");
			Map<String, String> output = new HashMap<>();
			output.put("status", "approved");
			return output;

		}

	}
}
