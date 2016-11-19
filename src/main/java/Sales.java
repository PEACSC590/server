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
	public Map<String, String> buy(String buyerID, String userToken, String itemID) throws Exception {
		Map<String, String> output = new HashMap<>();
		boolean authenticated = api.userTokens.testUserTokenForUser(buyerID, userToken);

		if (authenticated) {
			Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
			if (userDocument == null) {
				throw new Exception("Could not find user " + buyerID);
			}
			
			//System.out.println("checkpoitn 1");
			// if `numPendingPurchases` is greater than the maximum allowed,
			// error
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			if (numPendingPurchases >= MAX_PENDING_PURCHASES) {
				output.put("status", "listed");
				output.put("error", "MAXIMUM ALLOWED PURCHASES REACHED");
				output.put("numPendingPurchases", numPendingPurchases + "");
				output.put("dateBought", null);
				return output;
			}
			//System.out.println("checkpoitn 2");
			api.usersCollection.updateOne(new Document("userID", buyerID),
					new Document("$inc", new Document("numPendingPurchases", 1)));

			//System.out.println("checkpoitn 3");
			
			long dateBought = System.currentTimeMillis();
			Document updates = new Document().append("buyerID", buyerID).append("dateBought", dateBought)
					.append("status", "pending");
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));

			//System.out.println("checkpoitn 4");
			Document item = api.items.getItemByID(itemID);
			String itemName = item.getString("name");
			String sellerID = item.getString("sellerID");

			//System.out.println("checkpoitn 5");
			
			// BUYER CONFIRMATION EMAIL
			Email.send(buyerID + "@exeter.edu", "Pending purchase", "Your purchase of " + itemName + " is pending.");

			// SELLER CONFIRMATION EMAIL
			Email.send(sellerID + "@exeter.edu", "Confirm your sale", "Buyer " + buyerID + " has bought your item "
					+ itemName + ". Please confirm your sale of the item.");

			//System.out.println("checkpoitn 6");
			output.put("status", "pending");
			output.put("numPendingPurchases", (numPendingPurchases + 1) + "");
			output.put("dateBought", dateBought + "");
			//System.out.println("checkpoitn 7");
		} else {
			Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			output.put("status", "listed");
			output.put("error", "INVALID USERTOKEN");
			output.put("numPendingPurchases", numPendingPurchases + "");
			output.put("dateBought", null);
		}

		return output;
	}

	// TESTED: SUCCESS
	public Map<String, String> cancelPendingSale(String buyerID, String userToken, String itemID) {
		Map<String, String> output = new HashMap<>();
		boolean authenticated = api.userTokens.testUserTokenForUser(buyerID, userToken);

		Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
		if (authenticated) {
			Document cancelSale = new Document();
			cancelSale.put("status", "listed");
			cancelSale.put("buyerID", null);
			cancelSale.put("dateBought", null);
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", cancelSale));

			api.usersCollection.updateOne(new Document("userID", buyerID),
					new Document("$inc", new Document("numPendingPurchases", -1)));
			userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());

			Document item = api.items.getItemByID(itemID);
			String itemName = item.getString("name");
			String sellerID = item.getString("sellerID");

			// BUYER CONFIRMATION EMAIL
			Email.send(buyerID + "@exeter.edu", "You have cancelled a purchase",
					"You have cancelled purchase of " + itemName + ".");

			// SELLER CONFIRMATION EMAIL
			Email.send(sellerID + "@exeter.edu", "Buyer has cancelled your sale", "Buyer has cancelled your sale of "
					+ itemName + ". Your item has automatically been marked as listed.");

			output.put("status", "listed");
			output.put("numPendingPurchases", numPendingPurchases + "");
		} else {
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			output.put("status", "pending");
			output.put("error", "INVALID USERTOKEN");
			output.put("numPendingPurchases", numPendingPurchases + "");
		}

		return output;
	}

	// TESTED: SUCCESS
	public Map<String, String> refuseSale(String sellerID, String userToken, String itemID) {
		Map<String, String> output = new HashMap<>();
		boolean authenticated = api.userTokens.testUserTokenForUser(sellerID, userToken);

		if (authenticated) {
			String buyerID = api.items.getItemByID(itemID).getString("buyerID");
			Document refuseSale = new Document();
			refuseSale.put("status", "listed");
			refuseSale.put("buyerID", null);
			refuseSale.put("dateBought", null);
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", refuseSale));

			api.usersCollection.updateOne(new Document("userID", buyerID),
					new Document("$inc", new Document("numPendingPurchases", -1)));

			Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());

			Document item = api.items.getItemByID(itemID);
			String itemName = item.getString("name");

			// BUYER CONFIRMATION EMAIL
			Email.send(buyerID + "@exeter.edu", "Seller has cancelled your purchase",
					"Your purchase of " + itemName + " has been cancelled by seller " + sellerID + ".");

			// SELLER CONFIRMATION EMAIL
			Email.send(sellerID + "@exeter.edu", "You have refused a sale", "You have refused a sale of " + itemName
					+ " to " + buyerID + ". Your item has automatically been marked as listed.");

			output.put("status", "listed");
			output.put("numPendingPurchases", numPendingPurchases + "");
		} else {
			String buyerID = api.items.getItemByID(itemID).getString("buyerID");
			Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
			int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
			Document item = api.items.getItemByID(itemID);
			String status = item.getString("status");
			output.put("status", status);
			output.put("error", "INVALID USERTOKEN");
			output.put("numPendingPurchases", numPendingPurchases + "");
		}
		return output;
	}

	// TESTED: SUCCESS
	public Map<String, String> sell(String buyerID, String userToken, String itemID) {
		Map<String, String> output = new HashMap<>();
		boolean authenticated = api.userTokens.testUserTokenForUser(buyerID, userToken);

		Document item = api.items.getItemByID(itemID);

		if (authenticated) {
			if (item.getString("status").equals("cancelled")) {
				output.put("status", "error");
				output.put("error", "EXPIRED ITEM");
			} else if (item.getString("status").equals("listed")) {
				output.put("status", "error");
				output.put("error", "ITEM HAS NOT BEEN BOUGHT");
			} else if (item.getString("status").equals("pending")) {
				Document updates = new Document();
				updates.put("status", "sold");
				api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));
				api.usersCollection.updateOne(new Document("userID", buyerID),
						new Document("$inc", new Document("numPendingPurchases", -1)));

				String itemName = item.getString("name");
				String sellerID = item.getString("sellerID");

				try {
					// BUYER EMAIL
					Email.send(buyerID + "@exeter.edu", "Successful purchase",
							"Successful purchase of " + itemName + ".");

					// SELLER EMAIL
					Email.send(sellerID + "@exeter.edu", "Successful sale", "Successful sale of " + itemName + ".");
				} catch (Exception e) {
					e.printStackTrace();
				}

				Document userDocument = api.usersCollection.find(new Document("userID", buyerID)).first();
				int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());

				output.put("status", "sold");
				output.put("numPendingPurchases", numPendingPurchases + "");
			} else {
				output.put("status", "error");
				output.put("error", "STATUS ILLEGAL");
			}
		} else {
			String status = item.getString("status");
			output.put("status", status);
			output.put("error", "INVALID USERTOKEN");
		}
		return output;
	}
}
