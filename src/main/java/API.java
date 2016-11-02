import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import spark.Request;

public class API {

	private static final int MAX_PENDING_PURCHASES = 5;

	private MongoDatabase db;
	private MongoCollection<Document> usersCollection;
	private MongoCollection<Document> itemsCollection;

	private UserTokenController userTokens;

	public API(MongoDatabase db) {
		this.db = db;

		this.usersCollection = this.db.getCollection("users");
		this.itemsCollection = this.db.getCollection("items");

		userTokens = new UserTokenController(usersCollection);
	}

	// to access a file's attribute:
	// usersCollection.find(new Document("username", username));

	// Users

	// login api function
	public Map<String, String> login(String userID, String password) {
		Map<String, String> output = new HashMap<>();

		boolean correct = Login.login(userID, password);

		// if the login is correct,
		if (correct) {
			// add the user to the collection by their username
			upsertUser(userID);
			// and assign a new user token for the user for this session
			String userToken = userTokens.setUserTokenForNewSession(userID);

			output.put("success", "true");
			output.put("userToken", userToken);
		} else {
			output.put("success", "false");
		}

		return output;
	}

	public Map<String, String> logout(String userID, String userToken) {
		// if the userToken is valid for the username,
		boolean success = userTokens.testUserTokenForUser(userID, userToken);
		// remove the userToken for the user
		if (success)
			userTokens.endSession(userID);

		Map<String, String> output = new HashMap<>();
		output.put("success", success + "");
		return output;
	}

	// Create and return a user file if nonexistent in the collection; otherwise
	// return it
	public Document upsertUser(String username) {
		// if exists
		FindIterable<Document> cursor = usersCollection.find(new Document("username", username));
		if (cursor.first() != null) {
			return cursor.first();
		}

		Document userDocument = createUserDocument(username);
		usersCollection.insertOne(userDocument);

		return userDocument;
	}

	private Document createUserDocument(String username) {
		Document userDocument = new Document();
		userDocument.append("username", username);
		userDocument.append("banned", false);
		userDocument.append("numPendingPurchases", 0);
		// is that all?
		return userDocument;
	}

	// Items
	public List<Document> getItems(Bson query) {
		List<Document> documents = new LinkedList<Document>();

		FindIterable<Document> items = itemsCollection.find(query);
		for (Document item : items)
			documents.add(item);

		return documents;
	}
	


	public List<Document> getItemsUploadedByUser(String userID) {
		return getItems(new Document("userID", userID));
	}
	
	public List<Document> getItemsBoughtByUser(String userID) {
		// 10.16.16: the item attribute might not be "boughtByUserID", but I'll
		// use it for now
		return getItems(new Document("boughtByUserID", userID));
	}

	public Document getItemByID(int itemID) {
		List<Document> items = getItems(new Document("itemID", itemID));
		return items.size() > 0 ? items.get(0) : null;
		// return the first item matched
	}

	public Document insertItem(String username, String itemName, String itemDescription, Double itemPrice,
			String[] tags, String imageURL) {

		Document itemDocument = createItemDocument(username, itemName, itemDescription, itemPrice, tags, imageURL);
		itemsCollection.insertOne(itemDocument);

		return itemDocument;
	}

	private Document createItemDocument(String username, String itemName, String itemDescription, Double itemPrice,
			String[] tags, String imageURL) {
		Document itemDocument = new Document();
		// assign random integer between 0 and 10,000, we can find a better way
		// to assign item id
		UUID itemID = UUID.randomUUID();
		itemDocument.append("itemID", itemID);
		itemDocument.append("buyerID", null);
		itemDocument.append("userID", username);
		itemDocument.append("itemName", itemName);
		itemDocument.append("itemDescription", itemDescription);
		itemDocument.append("itemPrice", itemPrice);
		itemDocument.append("tags", tags);
		itemDocument.append("imageURL", imageURL);
		// true denotes unsold item
		itemDocument.append("status", true);
		itemDocument.append("dateBought", null);
		// is that all?
		return itemDocument;
	}

	public List<Document> searchItemsByText(String searchBy) {
		return getItems(new Document("$text", new Document("$search", searchBy)));
	}

	// Request/Response Utilities
	public Map<String, String> getBody(Request request) {
		// convert request.body() (structured=sdf&like=234&this=3) into a
		// hashmap
		Map<String, String> body = Util.queryStringToHashMap(request.body());
		return body;
	}

	public Map<String, String> buy(String userID, String itemID) throws Exception {

		Document userDocument = usersCollection.find(new Document("username", userID)).first();
		if (userDocument == null)
			throw new Exception("Could not find user " + userID);

		// if `numPendingPurchases` is greater than the maximum allowed, error
		int numPendingPurchases = Integer.parseInt(userDocument.get("numPendingPurchases").toString());
		if (numPendingPurchases >= MAX_PENDING_PURCHASES)
			throw new Exception("Reached maximum number of currently pending purchases allowed");

		// increment `numPendingPurchases`
		usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", 1)));

		// update the item to be bought with the buyer id, the date this is
		// being processed, and the new status of the item
		long dateBought = System.currentTimeMillis();
		Document updates = new Document().append("buyerID", userID).append("dateBought", dateBought).append("status",
				"pending");
		itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));

		Map<String, String> output = new HashMap<>();
		output.put("status", "pending");
		output.put("numPendingPurchases", numPendingPurchases + 1 + "");
		return output;
	}

	public Map<String, String> cancelPendingSale(String itemID, String userID) {
		Document cancelSale = new Document();
		cancelSale.put("status", "listed");
		cancelSale.put("buyerID", null);
		cancelSale.put("dateBought", null);
		itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", cancelSale));

		usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", -1)));

		Map<String, String> output = new HashMap<>();
		output.put("status", "listed");
		return output;

	}

	public Map<String, String> refuseSale(String itemID, String userID) {
		Document refuseSale = new Document();
		refuseSale.put("status", "listed");
		refuseSale.put("buyerID", null);
		refuseSale.put("dateBought", null);
		itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", refuseSale));

		usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", -1)));

		Map<String, String> output = new HashMap<>();
		output.put("status", "listed");
		return output;
	}

	public Map<String, String> unlist(String itemID) {
		Document unlist = new Document();
		unlist.put("status", "hidden");
		itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", unlist));

		Map<String, String> output = new HashMap<>();
		output.put("status", "hidden");
		return output;
	}

	// this is run after the buyer has paid for the item and has received it?
	public Map<String, String> sell(String itemID, String userID) {
		Document updates = new Document();
		updates.put("status", "sold");
		itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", updates));
		usersCollection.updateOne(new Document("userID", userID),
				new Document("$inc", new Document("numPendingPurchases", -1)));

		Map<String, String> output = new HashMap<>();
		output.put("status", "sold");
		return output;
	}

	// ban a user
	public Map<String, String> ban(String userID) {
		Document updates = new Document();
		updates.put("banned", true);
		usersCollection.updateOne(new Document("userID", userID), new Document("$set", updates));

		Map<String, String> output = new HashMap<>();
		output.put("status", "banned");
		return output;
	}
	
	// method for seller to approve sale within 3 days
	public Map<String, String> sellerApproveSale(int itemID, String userID) {
		Document item = getItemByID(itemID);
		long time = (long) item.get("dateBought");
		String buyerID = item.getString("buyerID");
		String itemName = item.getString("itemName");
		
		// check if 3 days have passed
		if ((System.currentTimeMillis() - time) < 259200) {
			Email.send(buyerID + "@exeter.edu", "Seller has confirmed your purchase", "Seller has confirmed your purchase of item " + itemName + ". Please contact the seller for payment.");
		} else {
			
		}
		
		Map<String, String> output = new HashMap<>();
		output.put("status", "approved");
		return output;
	}
	
}
