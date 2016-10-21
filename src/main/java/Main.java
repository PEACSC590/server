import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
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

		FreeMarkerEngine templateEngine = new FreeMarkerEngine();

		//
		// demo page using a view
		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello World!");
			return new ModelAndView(attributes, "index.ftl");
		}, templateEngine);
		//

		// Using API.java (as all endpoints should)
		post("/post-demo", (request, response) -> {
			Map<String, String> data = api.getBody(request);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data);
			return new ModelAndView(attributes, "display-data.ftl");
		}, templateEngine);

		// login
		post("/login", (request, response) -> {
			Map<String, String> data = api.getBody(request);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data);
			return new ModelAndView(attributes, "login.ftl");
		}, templateEngine);

		// list items in the db that match the query provided as a querystring
		// param
		get("/list-items", (req, res) -> {
			Map<String, Object> attributes = new HashMap<>();

			String jsonStringQuery = req.queryParams("query");
			if (jsonStringQuery == null || jsonStringQuery.length() == 0 || jsonStringQuery.charAt(0) != '{')
				jsonStringQuery = "{}";
			try {
				Bson query = (Bson) JSON.parse(jsonStringQuery);
				List<Document> items = api.getItems(query);
				List<String> itemStrings = new LinkedList<String>();

				for (Document item : items)
					itemStrings.add(item.toJson());

				attributes.put("results", itemStrings);
			} catch (Exception e) {
				attributes.put("error", e.toString());
			}

			return new ModelAndView(attributes, "db.ftl");
		}, templateEngine);

		get("/list-item", (req, res) -> {
			int itemID;
			try {
				itemID = Integer.parseInt(req.queryParams("itemID"));
			} catch (NumberFormatException e) {
				Map<String, Object> attributes = new HashMap<>();
				attributes.put("message", e.toString());
				return new ModelAndView(attributes, "error.ftl");
			}

			Document item = api.getItemByID(itemID);
			if (item == null) {
				Map<String, Object> attributes = new HashMap<>();
				attributes.put("message", "Item with id: " + itemID + " could not be found");
				return new ModelAndView(attributes, "error.ftl");
			}

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("item", item);
			return new ModelAndView(attributes, "ProductFocus.ftl");
		}, templateEngine);

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
		}, templateEngine);

	}

}
