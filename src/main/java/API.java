import java.util.Set;

import com.mongodb.DB;

public class API {
	
	private DB db;
	public API(DB db) {
		this.db = db;
	}

	public Set<String> getItems() {
		return db.getCollectionNames();
	}

}
