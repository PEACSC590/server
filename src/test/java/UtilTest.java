import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

public class UtilTest {

	@Test
	public void queryStringToHashMap() {

		String qs;
		HashMap<String, String> map = new HashMap<>();

		// test valid input
		qs = "val1=1&foo=32";
		// map.clear();
		map.put("val1", "1");
		map.put("foo", "32");
		assertEquals(Util.queryStringToHashMap(qs), map);

		// test blank input
		qs = "";
		map.clear();
		assertEquals(Util.queryStringToHashMap(qs), map);

		// test input containing empty values and empty keys
		qs = "val1=&foo=32&=empty";
		map.clear();
		map.put("val1", "");
		map.put("foo", "32");
		assertEquals(Util.queryStringToHashMap(qs), map);

	}

}
