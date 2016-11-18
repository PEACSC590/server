import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

public class Util {

	public static HashMap<String, String> queryStringToHashMap(String qs) {
		HashMap<String, String> map = new HashMap<>();

		if (qs == null || qs.isEmpty())
			return map;

		String[] attributes = qs.split("&"), parts;
		String val;
		if (!attributes[0].equals("")) {
			for (String attribute : attributes) {
				parts = attribute.split("=");

				if (parts.length == 0 || parts[0].equals(""))
					continue;

				if (parts.length == 1)
					val = "";
				else
					val = parts[1];

				map.put(parts[0], val);
			}
		}

		return map;
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static HashMap<String, Object> convertDocumentToMap(Document doc) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String[] keys = doc.keySet().toArray(new String[0]);
		Object val;
		for (int i = 0; i < keys.length; i++) {
			val = doc.get(keys[i]);
			if (keys[i].getClass().toString() == "Document")
				val = convertDocumentToMap((Document) val);
			map.put(keys[i], val);
		}

		return map;
	}

	public static List<HashMap<String, Object>> convertListOfDocsIntoListOfMaps(List<Document> docs) {
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String, Object>>();

		for (int i = 0; i < docs.size(); i++) {
			list.add(convertDocumentToMap(docs.get(i)));
			System.out.println(docs.get(i));
		}

		return list;
	}

	public static ArrayList<String> itemNameList(List<HashMap<String, Object>> docs) {
		ArrayList<String> itemNames = new ArrayList<String>();
		for (int i = 0; i < docs.size(); i++) {
			itemNames.add((String) docs.get(i).get("name"));
			System.out.println(itemNames.get(i));
		}
		return itemNames;
	}

	public static ArrayList<Double> itemPriceList(List<HashMap<String, Object>> docs) {
		ArrayList<Double> itemPrices = new ArrayList<Double>();
		for (int i = 0; i < docs.size(); i++) {
			itemPrices.add((Double) docs.get(i).get("price"));
		}
		return itemPrices;
	}

	public static ArrayList<Long> itemDateBought(List<HashMap<String, Object>> docs) {
		ArrayList<Long> itemDateBought = new ArrayList<Long>();
		for (int i = 0; i < docs.size(); i++) {
			itemDateBought.add((Long) docs.get(i).get("dateBought"));
		}
		return itemDateBought;
	}

	public static ArrayList<String> itemImageURL(List<HashMap<String, Object>> docs) {
		ArrayList<String> itemImages = new ArrayList<String>();
		for (int i = 0; i < docs.size(); i++) {
			itemImages.add((String) docs.get(i).get(""));
		}
		return itemImages;
	}

}
