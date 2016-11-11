import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public class Users {
	
	private API api;
	
	public Users(API api) {
		this.api = api;
	}

	// TESTED: SUCCESS
	public Map<String, String> login(String userID, String password) {
		Map<String, String> output = new HashMap<>();

		boolean correct = Login.login(userID, password);

		if (correct) {
			upsertUser(userID);
			String userToken = api.userTokens.setUserTokenForNewSession(userID);
			output.put("success", "true");
			output.put("userToken", userToken);
		} else {
			output.put("success", "false");
		}

		return output;
	}

	// TESTED: SUCCESS
	public Map<String, String> logout(String userID, String userToken) {
		boolean success = api.userTokens.testUserTokenForUser(userID, userToken);
		if (success) {
			api.userTokens.endSession(userID);
		}

		Map<String, String> output = new HashMap<>();
		//TODO: WHAT IS THIS?
		output.put("success", "logout " + success);
		return output;
	}

	// TESTED: SUCCESS
	public Document upsertUser(String username) {
		// if exists
		FindIterable<Document> cursor = api.usersCollection.find(new Document("userID", username));
		if (cursor.first() != null) {
			//System.out.println("user exists");
			return cursor.first();
		}

		Document userDocument = createUserDocument(username);
		api.usersCollection.insertOne(userDocument);

		return userDocument;
	}
	
	// TESTED AS PART OF UPSERTUSER: SUCCESS
	private Document createUserDocument(String username) {
		Document userDocument = new Document();
		userDocument.append("userID", username);
		userDocument.append("banned", false);
		userDocument.append("numPendingPurchases", "0");
		// TODO: IS THAT ALL?
		return userDocument;
	}
	
	// TESTED: SUCCESS
	// TODO: NEED TO GO OVER ALL API FUNCTIONS AND ADD CONDITION FOR USER BANNED
	public Map<String, String> ban(String userID) {
		Document updates = new Document();
		updates.put("banned", true);
		api.usersCollection.updateOne(new Document("userID", userID), new Document("$set", updates));

		Map<String, String> output = new HashMap<>();
		output.put("status", "banned");
		return output;
	}
	
	// TESTED: SUCCESS
	public Map<String, String> unban(String userID) {
		Document updates = new Document();
		updates.put("banned", false);
		api.usersCollection.updateOne(new Document("userID", userID), new Document("$set", updates));

		Map<String, String> output = new HashMap<>();
		output.put("status", "unbanned");
		return output;
	}
	
}
