import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;

import spark.ModelAndView;

public class Items {
	
	private API api;
	
	public Items(API api) {
		this.api = api;
	}
	
	// TESTED AS PART OF GETITEMSBYID: SUCCESS
	public List<Document> getItems(Bson query) {
		List<Document> documents = new LinkedList<Document>();

		FindIterable<Document> items = api.itemsCollection.find(query);
		for (Document item : items) {
			documents.add(item);
		}
			

		return documents;
	}
	
	// TESTED: SUCCESS
	public List<Document> getBuyableItems() {
		return getItems(new Document("status", "listed"));
	}

	// TESTED: SUCCESS
	public List<Document> getItemsUploadedByUser(String userID, String userToken) {
		return getItems(new Document("sellerID", userID));
	}
	
	// SEMI-TESTED: SHOULD WORK
	public List<Document> getItemsBoughtByUser(String userID) {
		// 10.16.16: the item attribute might not be "boughtByUserID", but I'll
		// use it for now
		return getItems(new Document("buyerID", userID));
	}

	// TESTED: SUCCESS
	public Document getItemByID(String itemID) {
		List<Document> items = getItems(new Document("itemID", itemID));
		return items.size() > 0 ? items.get(0) : null;
		// return the first item matched
	}
	
	// TESTED AS PART OF UPLOAD: SUCCESS
	public Document insertItem(String username, String itemName, String itemDescription, Double itemPrice,
			String[] tags, String imageURL) {

		Document itemDocument = createItemDocument(username, itemName, itemDescription, itemPrice, tags, imageURL);
		api.itemsCollection.insertOne(itemDocument);

		return itemDocument;
	}
	
	// TESTED: SUCCESS
	// TODO: EXCEPT FOR TAGS WITH ACTUAL ARRAY OF TAGS
	public Map<String, String> upload(Document item, String userID, String userToken) {
		Map<String, String> output = new HashMap<>();

		if (api.userTokens.testUserTokenForUser(userID, userToken)){
			try {
				String itemName = item.getString("name");
				String itemDescription = item.getString("description");
				double itemPrice = Double.parseDouble(item.getString("price"));
				// TODO: need to test this
				String[] tags = (String[]) JSON.parse(item.getString("tags"));
				String imageURL = item.getString("imageURL");
				Document itemget = insertItem(userID, itemName, itemDescription, itemPrice, tags, imageURL);
				
				String itemID = itemget.getString("itemID");

				Document status = new Document().append("status", "listed");
				api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", status));

				output.put("status", "listed");
				output.put("itemID", itemID);
			} catch (Exception e) {
				output.put("status", "illegal");
				output.put("error", "internal error: " + e.getMessage());
			}
		} else {
			System.out.println("token incorrect");
			output.put("status", "illegal");
			output.put("error", "invalid user token for user id");
		}
		
		return output;
	}
	
	// TESTED AS PART OF UPLOAD: SUCCESS
	// TODO: DO WE NEED TO FIX ATTRIBUTE NAMES?
	private Document createItemDocument(String username, String itemName, String itemDescription, Double itemPrice,
			String[] tags, String imageURL) {
		Document itemDocument = new Document();
		// assign random integer between 0 and 10,000, we can find a better way
		// to assign item id
		String itemID = Util.generateUUID();
		itemDocument.append("itemID", itemID);
		itemDocument.append("buyerID", null);
		itemDocument.append("sellerID", username);
		itemDocument.append("name", itemName);
		itemDocument.append("description", itemDescription);
		itemDocument.append("price", itemPrice);
		itemDocument.append("tags", tags);
		itemDocument.append("imageURL", imageURL);
		// true denotes unsold item
		itemDocument.append("status", "uploaded");
		itemDocument.append("dateBought", null);
		// is that all?
		return itemDocument;
	}

	// TESTED: SUCCESS
	public Map<String, String> unlist(String itemID, String sellerID, String userToken) {
		boolean success = api.userTokens.testUserTokenForUser(sellerID, userToken);
		if (success) {
	
			Document unlist = new Document();
			unlist.put("status", "hidden");
			api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", unlist));

			Map<String, String> output = new HashMap<>();
			output.put("status", "hidden");
			return output;
		}
		else {
			Map<String, String> output = new HashMap<>();
			output.put("status", "listed");
			return output;
		}
	}
	
	// TESTED: FAIL
	public List<Document> searchItemsByText(String searchBy) {
		return getItems(new Document("$text", new Document("$search", searchBy)));
	}
}
