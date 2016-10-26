import java.util.HashMap;

public class Util {

	public static HashMap<String, String> queryStringToHashMap(String qs) {
		HashMap<String, String> map = new HashMap<>();

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

}
