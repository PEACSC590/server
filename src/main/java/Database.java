import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;

public class Database {

	public MongoDatabase db;

	public Database(MongoClientURI mongoURI) throws MongoException, UnknownHostException {
		MongoClient mongoClient = new MongoClient(mongoURI);
		db = mongoClient.getDatabase("heroku_cf7lkl0c");

		// close the connection when the app receives a SIGTERM
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				mongoClient.close();
			}
		});

	}

}
