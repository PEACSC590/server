
import com.github.fakemongo.Fongo;
import com.mongodb.DB;

public class TestDB {

	DB db;

	public TestDB() {
		Fongo fongo = new Fongo("Mongo test server");
		db = fongo.getDB("testDB");

		// TODO: populate with test data
	}

}
