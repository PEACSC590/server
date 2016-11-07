import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.net.UnknownHostException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.ResponseTransformer;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class TestAPI {

	private static API api;

	public TestAPI(API api) {
		this.api = api;
	}

	public static void test() throws MongoException, UnknownHostException {
		try {
			// Testing in progess

			// Test upsert user
			Document User1 = api.users.upsertUser("User1");

			String token1 = api.userTokens.setUserTokenForNewSession("User1");
			System.out.println(token1);
			System.out.println("User1 has been created");
			Document User2 = api.users.upsertUser("User2");
			String token2 = api.userTokens.setUserTokenForNewSession("User2");
			System.out.println(token2);
			System.out.println("User2 has been created");

			/**

			// Test upload item
			Document book1 = new Document().append("name", "Hamlet")
					.append("description", "King Hamlet")
					.append("price", "5.99")
					.append("tags", "[classic, english]")
					.append("imageURL", "website");
			Map map = api.items.upload(book1, "User1", token1);
			System.out.println(map.get("status"));

			Document book2 = new Document().append("name", "Cow")
					.append("description", "King Cow")
					.append("price", "5.99")
					.append("tags", "[classic, english]")
					.append("imageURL", "website");
			System.out.println(api.items.upload(book2, "User1", token1).get("status"));

			// Test getItemsUploadedByID
			book1 = api.items.getItemsUploadedByUser("User1", token1).get(0);
			book2 = api.items.getItemsUploadedByUser("User1", token1).get(0);
			System.out.println(book1.get("price"));
			System.out.println(book1.get("tags"));
			System.out.println(book1.get("status"));
			String itemID1 = book1.getString("itemID");
			String itemID2 = book2.getString("itemID");
			System.out.println(itemID1);

			// Test getItems functions
			System.out.println(api.items.getBuyableItems());
			System.out.println(api.items.getItemByID(itemID1));

			// Test unlist function
			Map unlist = api.sales.unlist(itemID1, "User1", token1);
			System.out.println(unlist.get("status"));
			System.out.println(api.items.getItemByID(itemID1).get("status"));

			// Test buy function
			try {
				Map buy = api.sales.buy("User2", itemID2, token2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Check numPendingPurchases and status of bought item
			FindIterable<Document> cursor = api.usersCollection.find(new Document("username", "User2"));
			if (cursor.first() != null) {
				User2 = cursor.first();
				// # purchases should = 1
				System.out.println(User2.get("numPendingPurchases"));
				book2 = api.items.getItemByID(itemID2);
				System.out.println(book2.get("buyerID"));
				System.out.println(book2.get("status"));
			}

			//  Test seller approve sale
			//Map sellerApprove = api.sales.sellerApproveSale(itemID2);

			// should say approved
			//System.out.println(sellerApprove.get("status"));
			book2 = api.items.getItemByID(itemID2);
			System.out.println(book2.get("status"));**/
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws MongoException, UnknownHostException {

	}

}
