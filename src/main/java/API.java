import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import spark.Request;

public class API {

	private static final int PENDING_PURCHASES_TIMEOUT = 1000 * 60 * 60 * 24 * 3;
	// 3 days; in ms

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

		userTokens = new UserTokenController(usersCollection);

		users = new Users(this);
		items = new Items(this);
		sales = new Sales(this);
		
		new Thread(() -> {
		    while (true) {
		    	try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	refreshItems();
		    }
		}).start();
	}

	public void refreshItems() {
		FindIterable<Document> items = itemsCollection.find(new Document("status", "pending"));
		for (Document item : items) {
			int time = (int) item.get("dateBought");
			String buyerID = item.getString("buyerID");
			String itemName = item.getString("itemName");
			if (System.currentTimeMillis() - time > PENDING_PURCHASES_TIMEOUT) {
				usersCollection.updateOne(new Document("userID", buyerID),
						new Document("$inc", new Document("numPendingPurchases", -1)));

				Document cancelSale = new Document();
				cancelSale.put("status", "cancelled");
				cancelSale.put("buyerID", null);
				cancelSale.put("dateBought", null);
				itemsCollection.updateOne(item, new Document("$set", cancelSale));

				Email.send(buyerID, "Your purchase has been cancelled",
						"Your purchase of " + itemName + " has been cancelled due to seller inactivity.");
			}
		}
	}

	// Request/Response Utilities
	public Map<String, String> getBody(Request request) {
		// convert request.body() (structured=sdf&like=234&this=3) into a
		// hashmap
		Map<String, String> body = Util.queryStringToHashMap(request.body());
		return body;
	}

}
