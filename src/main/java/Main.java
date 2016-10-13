import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.BSON;
import org.bson.conversions.Bson;

import java.net.UnknownHostException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class Main {

	public static void main(String[] args) throws MongoException, UnknownHostException {

		// set server port and configure /public to be served statically
		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		// only use the official mongo db uri for production--when it is on the
		// machine (only MONGO_DEV_URI should be in .env for development)
		String mongoURI = System.getenv("MONGODB_URI") != null ? System.getenv("MONGODB_URI")
				: System.getenv("MONGODB_DEV_URI");
		Database database = new Database(new MongoClientURI(mongoURI));
		MongoDatabase db = database.db;

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

		// login
		post("/login", (request, response) -> {
			Map<String, String> data = api.getBody(request);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data);
			return new ModelAndView(attributes, "login.ftl");
		}, new FreeMarkerEngine());

		// list items in the db that match the query provided as a querystring
		// param
		get("/list-items", (req, res) -> {
			Map<String, Object> attributes = new HashMap<>();

			String jsonStringQuery = req.queryParams("query");
			if (jsonStringQuery == null || jsonStringQuery.length() == 0 || jsonStringQuery.charAt(0) != '{')
				jsonStringQuery = "{}";
			try {
				Bson query = (Bson) JSON.parse(jsonStringQuery);
				List<String> items = api.getItems(query);

				attributes.put("results", items);
			} catch (Exception e) {
				attributes.put("error", e.toString());
			}

			return new ModelAndView(attributes, "db.ftl");
		}, new FreeMarkerEngine());

		post("/upload", (req, res) -> {
			Map<String, String> data = api.getBody(req);

			if (data.size() == 5) {
				// actually do the upload
				res.redirect("/myproducts");
			} else {
				// give an error msg
				res.redirect("/upload");
			}

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data);
			return new ModelAndView(attributes, "display-data.ftl");
		}, new FreeMarkerEngine());

	}

}
