import org.bson.Document;

public class UserTokenController {
	
	private final long MS_IN_A_WEEK = 1000 * 60 * 60 * 24 * 7;

	private API api;

	public UserTokenController(API api) {
		this.api = api;
	}

	// TESTED AS PART OF UPSERTUSER: SUCCESS
	public String setUserTokenForNewSession(String userID) {
		String newUserToken = Util.generateUUID() + "@" + System.currentTimeMillis();
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
		String foundUserToken = item.getString("userToken");
		return item.isEmpty() || (foundUserToken.equals(userToken)
				&& (System.currentTimeMillis() - Long.parseLong(foundUserToken.split("@")[0]) < MS_IN_A_WEEK));
	}

}
