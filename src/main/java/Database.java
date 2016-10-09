import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class Database {

	public DB db;

	public Database(MongoURI mongoURI) throws MongoException, UnknownHostException {
		db = mongoURI.connectDB();
		boolean authenticated = db.authenticate(mongoURI.getUsername(), mongoURI.getPassword());
		System.out.printf("Authenticated: %b", authenticated);
	}

}
