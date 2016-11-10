import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class UserTokenController {

	private API api;
	
	public UserTokenController(API api) {
		this.api = api;
	}

	public String setUserTokenForNewSession(String userID) {
		String newUserToken = Util.generateUUID();
		System.out.println("newUserToken");
		api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$set", new Document("userToken", newUserToken)));
		return newUserToken;
	}

	public void endSession(String userID) {
		api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$set", new Document("userToken", null)));
	}
	
	public boolean testUserTokenForUser(String userID, String userToken) {
		Document item = api.usersCollection.find(new Document("userID", userID)).first();
		return item.isEmpty() || item.get("userToken").equals(userToken);
	}

}
