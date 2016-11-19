import java.util.*;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import spark.Request;

public class API {

	private static final long PENDING_PURCHASES_TIMEOUT = 1000 * 60 * 60 * 24 * 3;

	private MongoDatabase db;

	MongoCollection<Document> usersCollection;
	MongoCollection<Document> itemsCollection;

	UserTokenController userTokens;
	Users users;
	Items items;
	Sales sales;

	public API(MongoDatabase db) {
		this.db = db;

		usersCollection = this.db.getCollection("users");
		itemsCollection = this.db.getCollection("items");

		users = new Users(this);
		items = new Items(this);
		sales = new Sales(this);
		userTokens = new UserTokenController(this);

	}

	// TESTED: SUCCESS
	public void refreshItems() {
		List<Document> list = items.getItems(new Document("status", "pending"));
		System.out.println(list.size());
		for (Document item : list) {
			System.out.println("checking item");
			long time = (long) item.get("dateBought");
			String buyerID = item.getString("buyerID");
			String sellerID = item.getString("sellerID");
			String itemName = item.getString("name");
			if (System.currentTimeMillis() - time > PENDING_PURCHASES_TIMEOUT) {
				usersCollection.updateOne(new Document("userID", buyerID),
						new Document("$inc", new Document("numPendingPurchases", -1)));

				Document cancelSale = new Document();
				cancelSale.put("status", "cancelled");
				cancelSale.put("buyerID", null);
				cancelSale.put("dateBought", null);
				itemsCollection.updateOne(item, new Document("$set", cancelSale));

				// BUYER EMAIL
				Email.send(buyerID + "@exeter.edu", "PURCHASE CANCELLED",
						"Your purchase of " + itemName + " has been cancelled due to seller inactivity.");

				// SELLER EMAIL
				Email.send(sellerID + "@exeter.edu", "SALE CANCELLED",
						"Your sale of " + itemName + " has been cancelled due to seller inactivity.");
			}
		}
	}

	// Request/Response Utilities
	public Map<String, String> getBody(Request request) {
		// convert request.body() or request.queryString()
		// (structured=sdf&like=234&this=3) into a hashmap
		String body = request.body();
		if (body.isEmpty())
			body = request.queryString();
		return Util.queryStringToHashMap(body);
	}

}
