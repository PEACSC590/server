import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	public API(MongoDatabase db) {
		this.db = db;

		this.usersCollection = this.db.getCollection("users");
		this.itemsCollection = this.db.getCollection("items");
	}

	// to access a file's attribute:
	// usersCollection.find(new Document("username", username));
	
	// Users

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

	public Document upsertItem(String username, String itemName, String itemDescription, Double itemPrice,
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
		int itemID = (int) (Math.random() * (10000 - 0)) + 0;
		FindIterable<Document> cursor = itemsCollection.find(new Document("itemID", itemID));
		if (cursor.first() != null) {
			itemID = (int) (Math.random() * (10000 - 0)) + 0;
		}
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
		Document updates = new Document().append("buyerID", userID).append("dateBought", dateBought)
				.append("status", "pending");
		itemsCollection.updateOne(new Document("itemID", itemID),
				new Document("$set", updates));

		Map<String, String> output = new HashMap<>();
		output.put("status", "pending");
		output.put("numPendingPurchases", numPendingPurchases + 1 + "");
		output.put("dateBought", dateBought + "");
		return output;
	}
<<<<<<< HEAD
	public Map<String, String> sell(String itemID) {
		Document updates = new Document();
		updates.put("status", "sold");
		itemsCollection.updateOne(new Document("itemID", itemID),
				new Document("$set", updates));

		Document itemDocument = itemsCollection.find(new Document("itemID", itemID)).first(),
				userDocument = usersCollection.find(new Document("userID", itemDocument.get("buyerID"))).first();
		
=======
	
	public Map<String, String> sold(String itemID){
		//we should think about having this be the place where date bought is changed, but I'll leave it for now
		Document itemDocument = itemsCollection.find(new Document("itemID", itemID)).first();
		long dateBought = System.currentTimeMillis();
>>>>>>> 6f3bcba903f9fe6613fcd59e584be8cfc5ed6dd3
		Map<String, String> output = new HashMap<>();
		output.put("status", "sold");
		output.put("numPendingPurchases", userDocument.get("numPendingPurchases") + "");
		output.put("dateBought", itemDocument.get("dateBought") + "");
		return output;
		
		
		
	}

}
