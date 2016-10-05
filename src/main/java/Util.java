import java.util.HashMap;

public class Util {

	public static HashMap<String, String> queryStringToHashMap(String qs) {
		HashMap<String, String> map = new HashMap<String, String>();

		String[] attributes = qs.split("&"), parts;
		for (String attribute : attributes) {
			parts = attribute.split("=");
			map.put(parts[0], parts[1]);
		}

		return map;
	}

}
