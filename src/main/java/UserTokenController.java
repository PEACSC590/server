import org.bson.Document;

public class UserTokenController {

	private API api;
	
	public UserTokenController(API api) {
		this.api = api;
	}
	
	// TESTED AS PART OF UPSERTUSER: SUCCESS
	public String setUserTokenForNewSession(String userID) {
		String newUserToken = Util.generateUUID();
		api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$set", new Document("userToken", newUserToken)));
		return newUserToken;
	}

	// TESTED: SUCCESS
	public void endSession(String userID) {
		api.usersCollection.updateOne(new Document("userID", userID),
				new Document("$set", new Document("userToken", null)));
	}
	
	// TESTED AS PART OF UPLOAD: SUCCESS
	public boolean testUserTokenForUser(String userID, String userToken) {
		Document item = api.usersCollection.find(new Document("userID", userID)).first();
		return item.isEmpty() || item.get("userToken").equals(userToken);
	}

}
