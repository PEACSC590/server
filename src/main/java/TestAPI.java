import java.util.*;
import org.bson.Document;
import java.net.UnknownHostException;
import com.mongodb.*;

public class TestAPI {

	private API api;

	public TestAPI(API api) {
		this.api = api;
	}

	public void test() throws MongoException, UnknownHostException {
		try {
			// Testing in progess
						
			// Test upsert user
			Document User1 = api.users.upsertUser("User1");
			//System.out.println(User1.get("userID"));
			
			// Test user tokens
			String token1 = api.userTokens.setUserTokenForNewSession("User1");
			//System.out.println(token1);
			//System.out.println("User1 has been created");
			
			// This user will buy items from User1
			Document User2 = api.users.upsertUser("User2");
			String token2 = api.userTokens.setUserTokenForNewSession("User2");
			//System.out.println(token2);
			//System.out.println("User2 has been created");

			// Test upload item
			Document book1 = new Document().append("name", "Hamlet")
					.append("description", "King Hamlet")
					.append("price", "5.99")
					.append("tags", "")
					.append("imageURL", "website");
			Map<String, String> map = api.items.upload(book1, "User1", token1);
			//System.out.println(map.get("status"));
			String book1ID = map.get("itemID");
			//System.out.println(book1ID);
			
			Document book2 = new Document().append("name", "Cow")
					.append("description", "King Cow")
					.append("price", "4100.99")
					.append("tags", "")
					.append("imageURL", "website");
			//System.out.println(api.items.upload(book2, "User1", token1).get("status"));
			String book2ID = map.get("itemID");
			
			// Test getItemsUploadedByID
			book1 = api.items.getItemByID(book1ID);
			//System.out.println(book1.get("itemPrice"));
			//System.out.println(book1.get("tags"));
			//System.out.println(book1.get("status"));
			
			// Test getItemsUploadedByID
			book2 = api.items.getItemByID(book2ID);
			//System.out.println(book1.get("status"));

			// test searchby
			for (Document item : api.items.searchItemsByText("Cow")) {
				System.out.println(item.get("itemID") + " " + item.get("itemName") + " " + item.get("sellerID") + " " + item.get("status"));
			}
			
			
			
			
			
			
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

	public static void main(String[] args) throws MongoException, UnknownHostException {

	}

}
