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

		get("/login", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();

			return new ModelAndView(attributes, "login.ftl");
		}, templateEngine);

		// login
		post("/login", (request, response) -> {
			Map<String, String> data = api.getBody(request);
			// get username and password from login.ftl
			String username = data.get("username");
			String password = data.get("password");

			// using Login.java, check if username/password is valid
			String user = api.login(username, password);

			Map<String, Object> attributes = new HashMap<>();
			// attributes.put("correct", good);

			// if good, put username
			if (user.length() > 0) {
				response.redirect("/myproducts");
				// TODO: halt
				// attributes.put("user", username);
			} else {
				attributes.put("error", "Your username or password is incorrect.");
			}

			return new ModelAndView(attributes, "login.ftl"); // FIXME
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
				return errorView(e.toString());
			}

			Document item = api.getItemByID(itemID);
			if (item == null)
				return errorView("Item with id: " + itemID + " could not be found");

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("item", item);
			return new ModelAndView(attributes, "ProductFocus.ftl");
		}, templateEngine);

		// get the page to upload an item
		get("/upload", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();

			return new ModelAndView(attributes, "upload.ftl");
		}, templateEngine);
		
		
		// POST method for upload
		post("/upload", (req, res) -> {
			Map<String, String> data = api.getBody(req);
			
			// need to make sure all necessary data is present
			if (data.size() >= 5) {
				// do the upload
				String username = data.get("username");
				String itemName = data.get("itemName");
				String itemDescription = data.get("itemDescription");
				double itemPrice = Double.parseDouble(data.get("itemPrice"));
				
				// TODO: need to test this
				String[] tags = (String[]) JSON.parse(data.get("tags"));
				
				String imageURL = data.get("imageURL");
				api.insertItem(username, itemName, itemDescription, itemPrice, tags, imageURL);
				
				// redirect after success
				res.redirect("/myproducts");
				Map<String, Object> attributes = new HashMap<>();
				return new ModelAndView(attributes, "MyProducts.ftl");
			} else {
				// wat?
				res.redirect("/upload");
			}

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data);
			return new ModelAndView(attributes, "display-data.ftl");
		}, templateEngine);

		post("/buy", (req, res) -> {

			Map<String, String> body = api.getBody(req);

			if (!body.containsKey("userID") || !body.containsKey("itemID"))
				return errorView("Invalid input");
			
			Map<String, String> output;
			try {
				output = api.buy(body.get("userID"), body.get("itemID"));
			} catch (Exception e) {
				return errorView(e.getMessage());
			}

			
			// PLACEHOLDER: display the raw output
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", output);
			return new ModelAndView(attributes, "display-data.ftl");
		}, templateEngine);

	}

	private static ModelAndView errorView(String errmsg) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("message", errmsg);
		return new ModelAndView(attributes, "error.ftl");
	}

}
