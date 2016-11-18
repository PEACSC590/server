import org.bson.Document;

public class UserTokenController {

	private final long MS_IN_A_WEEK = 1000 * 60 * 60 * 24 * 7;

	private API api;

	public UserTokenController(API api) {
		this.api = api;
	}

	// TESTED AS PART OF UPSERTUSER: SUCCESS
	public String setUserTokenForNewSession(String userID) {
		String newUserToken = Util.generateUUID() + "_" + System.currentTimeMillis();
		api.usersCollection.updateOne(new Document("userID", userID.toLowerCase()),
				new Document("$set", new Document("userToken", newUserToken)));
		return newUserToken;
	}

	// TESTED: SUCCESS
	public void endSession(String userID) {
		api.usersCollection.updateOne(new Document("userID", userID.toLowerCase()),
				new Document("$set", new Document("userToken", null)));
	}

	// TESTED AS PART OF UPLOAD: SUCCESS
	public boolean testUserTokenForUser(String userID, String userToken) {
		Document user = api.usersCollection.find(new Document("userID", userID.toLowerCase())).first();
		if (user.isEmpty()) return false;
		
		String foundUserToken = user.getString("userToken");
		return (foundUserToken.equals(userToken)
				&& (System.currentTimeMillis() - Long.parseLong(foundUserToken.split("_")[1]) < MS_IN_A_WEEK));
	}

}
