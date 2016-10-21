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
