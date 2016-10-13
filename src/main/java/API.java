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

	public List<String> getItems(Bson query) {
		List<String> itemStrings = new LinkedList<String>();

		FindIterable<Document> items = itemsCollection.find(query);
		for (Document item : items)
			itemStrings.add(item.toJson());

		return itemStrings;
	}

	public Map<String, String> getBody(Request request) {
		// convert request.body() (structured=sdf&like=234&this=3) into a
		// hashmap
		Map<String, String> body = Util.queryStringToHashMap(request.body());
		return body;
	}

}
