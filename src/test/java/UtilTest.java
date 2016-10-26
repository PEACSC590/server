import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

public class UtilTest {
	
	@Test
	public void testQueryStringToHashMap() {
		
		String qs = "val1=1&foo=32";
		HashMap<String, String> map = new HashMap<>();
		map.put("val1", "1");
		map.put("foo", "32");
		assertEquals(Util.queryStringToHashMap(qs), map);
		
	}

}
