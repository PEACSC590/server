import java.util.HashMap;
import java.util.Map;


import org.bson.Document;

public class Sales {

	private static final int MAX_PENDING_PURCHASES = 5;
	
	private API api;

	public Sales(API api) {
		this.api = api;
	}
	
	// TESTED: SUCCESS
	public Map<String, String> buy(String buyerID, String itemID, String userToken) throws Exception {
		boolean success = api.userTokens.testUserTokenForUser(buyerID, userToken);
		
		if (success){
			Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
			if (userDocument == null) {
				throw new Exception("Could not find user " + buyerID);
			}

			// if `numPendingPurchases` is greater than the maximum allowed, error
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			if (numPendingPurchases >= MAX_PENDING_PURCHASES) {
				Map<String, String> output = new HashMap<>();
				output.put("status", "listed");
				output.put("error", "MAXIMUM ALLOWED PURCHASES REACHED");
				output.put("numPendingPurchases", numPendingPurchases + "");
				output.put("dateBought", null);
				return output;
			}

			api.usersCollection.updateOne(new Document("userID", buyerID),
				new Document("$inc", new Document("numPendingPurchases", 1)));

			long dateBought = System.currentTimeMillis();
			Document updates = new Document().append("buyerID", buyerID).append("dateBought", dateBought).append("status",
				"pending");
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));

			Document item = api.items.getItemByID(itemID);
			String itemName = item.getString("name");
			String sellerID = item.getString("sellerID");

			// BUYER CONFIRMATION EMAIL
			Email.send(buyerID + "@exeter.edu", "Pending purchase", "Your purchase of " + itemName + " is pending.");

			// SELLER CONFIRMATION EMAIL
			Email.send(sellerID + "@exeter.edu", "Confirm your sale",
				"Buyer " + buyerID + " has bought your item " + itemName + ". Please confirm your sale of the item.");

			Map<String, String> output = new HashMap<>();
			output.put("status", "pending");
			output.put("numPendingPurchases", numPendingPurchases + 1 + "");
			output.put("dateBought", dateBought + "");
			return output;
		}
		else {
			Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			Map<String, String> output = new HashMap<>();
			output.put("status", "listed");
			output.put("numPendingPurchases", numPendingPurchases + "");
			output.put("dateBought", null);
			return output;
		}
	}

	public Map<String, String> cancelPendingSale(String itemID, String userID, String userToken) {
		boolean success = api.userTokens.testUserTokenForUser(userID, userToken);
		Document userDocument = api.usersCollection.find(new Document("username", userID)).first();
		if (success){
			Document cancelSale = new Document();
			cancelSale.put("status", "listed");
			cancelSale.put("buyerID", null);
			cancelSale.put("dateBought", null);
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", cancelSale));

			api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", -1)));
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());

			Map<String, String> output = new HashMap<>();
			output.put("status", "listed");
			output.put("numPendingPurchases", numPendingPurchases + "");
			return output;
		}
		else {
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			Map<String, String> output = new HashMap<>();
			output.put("status", "pending");
			output.put("numPendingPurchases", numPendingPurchases + "");
			return output;
		}

	}

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

	// this is run after the buyer has paid for the item and has received it
	public Map<String, String> sell(String itemID, String userID, String userToken) {
		Document item = api.items.getItemByID(itemID);
		boolean success = api.userTokens.testUserTokenForUser(userID, userToken);
		if (success) {
			if (item.getString("status").equals("listed")) {
				Map<String, String> output = new HashMap<>();
				output.put("status", "error");
				output.put("error", "seller has passed time window");
				return output;
			}
			
			else {
				Document updates = new Document();
				updates.put("status", "sold");
				api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));
				api.usersCollection.updateOne(new Document("userID", userID),
						new Document("$inc", new Document("numPendingPurchases", -1)));

				String itemName = item.getString("itemName");
				String sellerID = item.getString("sellerID");

				// send e-mail to buyer
				Email.send(userID, "Successful purchase", "Successful purchase of " + itemName + ".");

				Email.send(sellerID, "Successful sale", "Successful sale of " + itemName + ".");
				Map<String, String> output = new HashMap<>();
				output.put("status", "sold");
				return output;
			}
		}
		else{
			Map<String, String> output = new HashMap<>();
			output.put("status", "pending");
			return output;
		}
	}
}
