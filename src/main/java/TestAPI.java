import org.bson.Document;
import java.net.UnknownHostException;
import com.mongodb.*;

public class TestAPI {

	private API api;

	public TestAPI(API api) {
		this.api = api;
	}

	public static void main(String[] args) throws MongoException, UnknownHostException {

	}

	public void test() throws MongoException, UnknownHostException {
		try {
			// Testing in progess
			
			
			String user1 = "test1";
			String user2 = "test2";

			// Test upsert user
			Document User1 = api.users.upsertUser(user1);
			//System.out.println(User1.get("userID"));

			// Test user tokens
			String token1 = api.userTokens.setUserTokenForNewSession(user1);
			//System.out.println(token1);
			//System.out.println("User1 has been created");

			// This user will buy items from User1
			Document User2 = api.users.upsertUser(user2);
			String token2 = api.userTokens.setUserTokenForNewSession(user2);

			System.out.println(api.userTokens.testUserTokenForUser(user1, "moo"));
			System.out.println(api.userTokens.testUserTokenForUser(user1, token1));

			//System.out.println(token2);
			//System.out.println("User2 has been created");


			/**api.usersCollection.updateOne(new Document("userID", user2),
					new Document("$set", new Document("numPendingPurchases", 0)));
			api.usersCollection.updateOne(new Document("userID", user1),
					new Document("$set", new Document("numPendingPurchases", 0)));

			// Test upload item
			Document book1 = new Document().append("name", "Hamlet")
					.append("description", "Shakespearean novel")
					.append("price", "5.99")
					.append("tags", "")
					.append("imageURL", "exeter.edu");
			Map<String, String> map = api.items.upload(user1, token1, book1);
			//System.out.println(map.get("status"));
			String book1ID = map.get("itemID");
			System.out.println(book1ID);

			Document book2 = new Document().append("name", "Cow Figurine")
					.append("description", "Plastic toy shaped like a cow")
					.append("price", "10.00")
					.append("tags", "")
					.append("imageURL", "moo.com");
			api.items.upload(user1, token1, book2);
			//System.out.println(api.items.upload(book2, "User1", token1).get("status"));
			String book2ID = map.get("itemID");**/

			/**for (Document item : api.items.getItemsUploadedByUser(user1, token1)) {
				System.out.println(item.get("itemID") + " " + item.get("itemName") + " " + item.get("sellerID") + " " + item.get("status"));
			}**/

			// Test getItemsUploadedByID
			//book1 = api.items.getItemByID(book1ID);
			//System.out.println(book1.get("itemPrice"));
			//System.out.println(book1.get("tags"));
			//System.out.println(book1.get("status"));

			// Test getItemsUploadedByID
			//book2 = api.items.getItemByID(book2ID);
			//System.out.println(book1.get("status"));

			//map = api.sales.buy(user2, token2, book1ID);
			//System.out.println(map.get("status") + " " + map.get("numPendingPurchases") + " " + map.get("dateBought"));
			//System.out.println(api.usersCollection.find(new Document("userID", user2)).first().get("numPendingPurchases"));
			//book1 = api.items.getItemByID(book1ID);
			//System.out.println(book1.get("buyerID") + " " + book1.get("status"));

			/**
			// test sold
			map = api.sales.buy(user2, book2ID, token2);
			System.out.println(map.get("status") + " " + map.get("numPendingPurchases") + " " + map.get("dateBought"));
			System.out.println(api.usersCollection.find(new Document("userID", user2)).first().get("numPendingPurchases"));
			book2 = api.items.getItemByID(book2ID);
			System.out.println(book2.get("buyerID") + " " + book2.get("status"));**/


			// test sold
			//map = api.sales.sell(user2, token2, book1ID);
			//System.out.println(map.get("status"));
			//System.out.println(api.usersCollection.find(new Document("userID", user2)).first().get("numPendingPurchases"));


			/**test refuse sale
			map = api.sales.refuseSale(book1ID, user1, token2);
			System.out.println(map.get("status") + " " + map.get("numPendingPurchases"));
			System.out.println(api.usersCollection.find(new Document("userID", user2)).first().get("numPendingPurchases"));**?

			map = api.sales.refuseSale(book1ID, user1, token1);
			System.out.println(map.get("status") + " " + map.get("numPendingPurchases"));
			System.out.println(api.usersCollection.find(new Document("userID", user2)).first().get("numPendingPurchases"));

			/** test cancel pending sale
			map = api.sales.cancelPendingSale(book1ID, user2, token2);
			System.out.println(map.get("status") + " " + map.get("numPendingPurchases"));
			System.out.println(api.usersCollection.find(new Document("userID", user2)).first().get("numPendingPurchases"));
			book1 = api.items.getItemByID(book1ID);
			System.out.println(book1.get("status") + " " + book1.get("buyerID")); **/


			/** test searchby: now successful
			for (Document item : api.items.searchItemsByText("King")) {
				System.out.println(item.get("itemID") + " " + item.get("name") + " " + item.get("sellerID") + " " + item.get("status"));
			} **/

			/** test buy function
			map = api.sales.buy(user2, book1ID, token2);
			System.out.println(map.get("status") + " " + map.get("numPendingPurchases") + " " + map.get("dateBought"));
			book1 = api.items.getItemByID(book1ID);
			System.out.println(book1.get("buyerID") + " " + book1.get("status"));

			map = api.sales.buy(user2, book2ID, token2);
			System.out.println(map.get("status") + " " + map.get("numPendingPurchases") + " " + map.get("dateBought"));
			book1 = api.items.getItemByID(book1ID);
			System.out.println(book1.get("buyerID") + " " + book1.get("status"));**/



			/**TEST UNLIST
			api.items.unlist(book2ID, "User1", token1);
			System.out.println(api.items.getItemByID(book2ID).get("status"))**/

			/**System.out.println("TEST: getBuyableItems");
			// Test getBuyable Items
			for (Document item : api.items.getBuyableItems()) {
				System.out.println(item.get("itemID") + " " + item.get("itemName") + " " + item.get("sellerID") + " " + item.get("status"));
			}

			System.out.println("TEST: getUploadedByUserItems");
			// Test getBuyable Items
			for (Document item : api.items.getItemsUploadedByUser("User1", token1)) {
				System.out.println(item.get("itemID") + " " + item.get("itemName") + " " + item.get("sellerID") + " " + item.get("status"));
			}**/


			/** TEST BAN USER2
			System.out.println(api.usersCollection.find(new Document("userID", "User2")).first().get("banned"));
			api.users.ban("User2");
			System.out.println(api.usersCollection.find(new Document("userID", "User2")).first().get("banned"));**/


			/**TEST LOGOUT
			api.users.unban("User2");
			System.out.println(token2);
			api.users.logout("User2", token2);
			System.out.println(api.usersCollection.find(new Document("userID", "User2")).first().get("userToken"));**/

			/***
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



}
