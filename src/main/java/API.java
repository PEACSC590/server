import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import spark.Request;

public class API {

	private DB db;
	private DBCollection usersCollection;
	private DBCollection itemsCollection;

	public API(DB db) {
		this.db = db;

		this.usersCollection = this.db.getCollection("users");
		this.itemsCollection = this.db.getCollection("items");
	}

	public List<String> getItems() {
		List<String> itemStrings = new LinkedList<String>();

		List<DBObject> items = itemsCollection.find().toArray();
		for (DBObject item : items)
			itemStrings.add(item.toMap().toString());

		return itemStrings;
	}

	public Map<String, String> getBody(Request request) {
		// convert request.body() (structured=sdf&like=234&this=3) into a
		// hashmap
		Map<String, String> body = Util.queryStringToHashMap(request.body());
		return body;
	}

}
