import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class UserTokenController {

	private MongoCollection<Document> usersCollection;

	public UserTokenController(MongoCollection<Document> usersCollection) {
		this.usersCollection = usersCollection;
	}

	public String setUserTokenForNewSession(String userID) {
		String newUserToken = Util.generateUUID();
		usersCollection.updateOne(new Document("userID", userID),
				new Document("$set", new Document("userToken", newUserToken)));
		return newUserToken;
	}

	public void endSession(String userID) {
		usersCollection.updateOne(new Document("userID", userID),
				new Document("$set", new Document("userToken", null)));
	}

	public boolean testUserTokenForUser(String userID, String userToken) {
		return usersCollection.find(new Document("userID", userID)).first().get("userToken") == userToken;
	}

}