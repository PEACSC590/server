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

	private MongoDatabase db;
	private MongoCollection<Document> usersCollection;
	private MongoCollection<Document> itemsCollection;

	public API(MongoDatabase db) {
		this.db = db;

		this.usersCollection = this.db.getCollection("users");
		this.itemsCollection = this.db.getCollection("items");
	}

	
	public Document upsertItem(String username, String itemName, String itemDescription, Double itemPrice, String[] tags, String imageURL) {

		Document itemDocument = createItemDocument(username, itemName, itemDescription, itemPrice, tags, imageURL);
		itemsCollection.insertOne(itemDocument);

		return userDocument;
	}
	
	private Document createItemDocument(String username, String itemName, String itemDescription, Double itemPrice, String[] tags, String imageURL) {
		Document itemDocument = new Document();
		//assign random integer between 0 and 10,000, we can find a better way to assign item id
		int itemID = (int) (Math.random() * (10000 - 0)) + 0;
		FindIterable<Document> cursor = itemsCollection.find(new Document("itemID", itemID));
		if(cursor.first() != null){
			itemID = (int) (Math.random() * (10000 - 0)) + 0;
		}
		itemDocument.append("itemID", itemID);
		itemDocument.append("buyerID", null);
		itemDocument.append("sellerID", username);
		itemDocument.append("itemName", itemName);
		itemDocument.append("itemDescription", itemDescription);
		itemDocument.append("itemPrice", itemPrice);
		itemDocument.append("tags", tags);
		itemDocument.append("imageURL", imageURL);
		//true denotes unsold item
		itemDocument.append("status", pending);
		itemDocument.append("dateBought", null);
		// is that all?
		return itemDocument;
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
	private Document createUserDocument(String username, int itemsbought) {
		itemsbought = 0;
		Document userDocument = new Document();
		userDocument.append("username", username);
		userDocument.append("banned", false);
		userDocument.append("numPendingPurchases", itemsbought);
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

	// Request/Response Utilities
	public Map<String, String> getBody(Request request) {
		// convert request.body() (structured=sdf&like=234&this=3) into a
		// hashmap
		Map<String, String> body = Util.queryStringToHashMap(request.body());
		return body;
	}

}
