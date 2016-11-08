import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public class Users {
	
	private API api;
	
	public Users(API api) {
		this.api = api;
	}

	// login api function
	public Map<String, String> login(String userID, String password) {
		Map<String, String> output = new HashMap<>();

		boolean correct = Login.login(userID, password);

		// if the login is correct,
		if (correct) {
			// add the user to the collection by their username
			upsertUser(userID);
			// and assign a new user token for the user for this session
			String userToken = api.userTokens.setUserTokenForNewSession(userID);

			output.put("success", "true");
			output.put("userToken", userToken);
		} else {
			output.put("success", "false");
		}

		return output;
	}

	public Map<String, String> logout(String userID, String userToken) {
		// if the userToken is valid for the username,
		boolean success = api.userTokens.testUserTokenForUser(userID, userToken);
		// remove the userToken for the user
		if (success)
			api.userTokens.endSession(userID);

		Map<String, String> output = new HashMap<>();
		output.put("success", success + "");
		return output;
	}

	// Create and return a user file if nonexistent in the collection; otherwise
	// return it
	public Document upsertUser(String username) {
		// if exists
		FindIterable<Document> cursor = api.usersCollection.find(new Document("userID", username));
		if (cursor.first() != null) {
			System.out.println("user exists");
			return cursor.first();
		}

		Document userDocument = createUserDocument(username);
		api.usersCollection.insertOne(userDocument);

		return userDocument;
	}

	private Document createUserDocument(String username) {
		Document userDocument = new Document();
		userDocument.append("userID", username);
		userDocument.append("banned", false);
		userDocument.append("numPendingPurchases", 0);
		// is that all?
		return userDocument;
	}
	
	// ban a user
	public Map<String, String> ban(String userID) {
		Document updates = new Document();
		updates.put("banned", true);
		api.usersCollection.updateOne(new Document("userID", userID), new Document("$set", updates));

		Map<String, String> output = new HashMap<>();
		output.put("status", "banned");
		return output;
	}
	
}
