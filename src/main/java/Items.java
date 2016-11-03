import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class Items {

	MongoCollection<Document> itemsCollection;
	
	public Items(API api) {
		itemsCollection = api.itemsCollection;
	}
	
	public List<Document> getItems(Bson query) {
		List<Document> documents = new LinkedList<Document>();

		FindIterable<Document> items = itemsCollection.find(query);
		for (Document item : items)
			documents.add(item);

		return documents;
	}
	
	// get buyable items, items which are not hidden
	public List<Document> getBuyableItems() {
		return getItems(new Document("status", "listed"));
	}

	public List<Document> getItemsUploadedByUser(String userID, String userToken) {
		return getItems(new Document("userID", userID));
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
		itemsCollection.insertOne(itemDocument);

		return itemDocument;
	}

	private Document createItemDocument(String username, String itemName, String itemDescription, Double itemPrice,
			String[] tags, String imageURL) {
		Document itemDocument = new Document();
		// assign random integer between 0 and 10,000, we can find a better way
		// to assign item id
		UUID itemID = UUID.randomUUID();
		itemDocument.append("itemID", itemID);
		itemDocument.append("buyerID", null);
		itemDocument.append("sellerID", username);
		itemDocument.append("itemName", itemName);
		itemDocument.append("itemDescription", itemDescription);
		itemDocument.append("itemPrice", itemPrice);
		itemDocument.append("tags", tags);
		itemDocument.append("imageURL", imageURL);
		// true denotes unsold item
		itemDocument.append("status", true);
		itemDocument.append("dateBought", null);
		// is that all?
		return itemDocument;
	}

	public List<Document> searchItemsByText(String searchBy) {
		return getItems(new Document("$text", new Document("$search", searchBy)));
	}
}
