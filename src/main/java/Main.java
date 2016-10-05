import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.net.UnknownHostException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.mongodb.*;

public class Main {

	public static void main(String[] args) throws MongoException, UnknownHostException {

		// set server port and configure /public to be served statically
		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		//
		// demo page using a view
		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello World!");

			return new ModelAndView(attributes, "index.ftl");
		}, new FreeMarkerEngine());
		//

		//

		//

		// TODO: add the mongo db URI into .env
		if (System.getenv("MLAB_URI") == "")
			return;

		Database database = new Database(new MongoURI(System.getenv("MLAB_URI")));
		DB db = database.db;

		API api = new API(db); // note: the way the methods are organized and
								// accessed is subject to change

		get("/list-items", (req, res) -> {
			Set<String> items = api.getItems();

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("results", items);

			return new ModelAndView(attributes, "db.ftl");
		}, new FreeMarkerEngine());

	}

}
