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
	
	public List<Document> getItems(Bson query) {
		List<Document> documents = new LinkedList<Document>();

		FindIterable<Document> items = api.itemsCollection.find(query);
		for (Document item : items)
			documents.add(item);

		return documents;
	}
	
	// get buyable items, items which are not hidden
	public List<Document> getBuyableItems() {
		return getItems(new Document("status", "listed"));
	}

	public List<Document> getItemsUploadedByUser(String userID, String userToken) {
		return getItems(new Document("sellerID", userID));
	}
	
	public List<Document> getItemsBoughtByUser(String userID) {
		// 10.16.16: the item attribute might not be "boughtByUserID", but I'll
		// use it for now
		return getItems(new Document("boughtByUserID", userID));
	}

	public Document getItemByID(String itemID) {
		List<Document> items = getItems(new Document("itemID", itemID));
		return items.size() > 0 ? items.get(0) : null;
		// return the first item matched
	}

	public Document insertItem(String username, String itemName, String itemDescription, Double itemPrice,
			String[] tags, String imageURL) {

		Document itemDocument = createItemDocument(username, itemName, itemDescription, itemPrice, tags, imageURL);
		api.itemsCollection.insertOne(itemDocument);

		return itemDocument;
	}

	public Map<String, String> upload(Document item, String userID, String userToken) {
		Map<String, String> output = new HashMap<>();
		
		System.out.println("Begin to upload item " + userID + " " + userToken);

		if (api.userTokens.testUserTokenForUser(userID, userToken)){
			try {
				String itemName = item.getString("name");
				String itemDescription = item.getString("description");
				double itemPrice = item.getDouble("price");
				// TODO: need to test this
				String[] tags = (String[]) JSON.parse(item.getString("tags"));
	
				String imageURL = item.getString("imageURL");

				System.out.println("item info parsed succesfuly");
				String itemID = insertItem(userID, itemName, itemDescription, itemPrice, tags, imageURL)
						.getString("itemID");

				Document status = new Document().append("status", "listed");
				// set status to listed
				api.itemsCollection.updateOne(new Document("itemID", itemID), new Document("$set", status));

				output.put("status", "listed");
			} catch (Exception e) {
				output.put("status", "illegal");
				output.put("error", "internal error: " + e.getMessage());
			}
		} else {
			output.put("status", "illegal");
			output.put("error", "invalid user token for user id");
		}
		
		return output;
	}
	
	private Document createItemDocument(String username, String itemName, String itemDescription, Double itemPrice,
			String[] tags, String imageURL) {
		Document itemDocument = new Document();
		// assign random integer between 0 and 10,000, we can find a better way
		// to assign item id
		String itemID = Util.generateUUID();
		itemDocument.append("itemID", itemID);
		itemDocument.append("buyerID", null);
		itemDocument.append("sellerID", username);
		itemDocument.append("itemName", itemName);
		itemDocument.append("itemDescription", itemDescription);
		itemDocument.append("itemPrice", itemPrice);
		itemDocument.append("tags", tags);
		itemDocument.append("imageURL", imageURL);
		// true denotes unsold item
		itemDocument.append("status", "uploaded");
		itemDocument.append("dateBought", null);
		// is that all?
		return itemDocument;
	}

	public List<Document> searchItemsByText(String searchBy) {
		return getItems(new Document("$text", new Document("$search", searchBy)));
	}
}
