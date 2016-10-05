import java.util.Map;
import java.util.Set;

import com.mongodb.DB;

import spark.Request;

public class API {

	private DB db;

	public API(DB db) {
		this.db = db;
	}

	public Set<String> getItems() {
		return db.getCollectionNames();
	}

	public Map<String, String> getBody(Request request) {
		// convert request.body() (structured=sdf&like=234&this=3) into a
		// hashmap
		Map<String, String> body = Util.queryStringToHashMap(request.body());
		return body;
	}

}
