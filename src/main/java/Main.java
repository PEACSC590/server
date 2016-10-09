import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.net.UnknownHostException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;

import com.mongodb.*;

public class Main {

	public static void main(String[] args) throws MongoException, UnknownHostException {

		// set server port and configure /public to be served statically
		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");
		
		Database database = new Database(new MongoURI(System.getenv("MONGODB_URI")));
		DB db = database.db;

		API api = new API(db); // note: the way the methods are organized and
								// accessed is subject to change

		//
		// demo page using a view
		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello World!");
			return new ModelAndView(attributes, "index.ftl");
		}, new FreeMarkerEngine());
		//

		// Using API.java (as all endpoints should)
		post("/post-demo", (request, response) -> {
			Map<String, String> data = api.getBody(request);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data);
			return new ModelAndView(attributes, "display-data.ftl");
		}, new FreeMarkerEngine());

		// example using the db
		get("/list-items", (req, res) -> {
			List<String> items = api.getItems();

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("results", items);
			return new ModelAndView(attributes, "db.ftl");
		}, new FreeMarkerEngine());

	}

}
