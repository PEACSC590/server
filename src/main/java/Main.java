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
import spark.ResponseTransformer;

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
		ResponseTransformer jsonEngine = JsonUtil.json();
		
		//System.out.println("Server is good!");
		
		// constantly refresh items every 5 min
		new Thread(() -> {
		    while (true) {
		    	try {
					Thread.sleep(1000 * 60 * 5);
					System.out.println("Refreshing pending items");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	api.refreshItems();
		    }
		}).start();
		
		// BEGIN TESTING CODE
		// PLEASE LEAVE THE TESTING CODE HERE BECAUSE IT IS THE ONLY WAY I CAN GET IT TO CONNECT TO THE DB
		//TestAPI test = new TestAPI(api);
		//test.test();
		// END TESTING CODE
		
		// Browser page
		get("/login", (request, response) -> {
			System.out.println("GET LOGIN");
			Map<String, Object> attributes = new HashMap<>();
			// no attributes needed?

			return new ModelAndView(attributes, "login.ftl");
		}, templateEngine);

		// login
		// Should be used by a form -> serves a page
		post("/login", (req, res) -> {
			System.out.println("POST LOGIN");
			Map<String, String> data = api.getBody(req);
			String userID = data.get("userID");
			String password = data.get("password");
			
			Map<String, String> loginStatus = api.users.login(userID, password);

			Map<String, Object> attributes = new HashMap<>();
			// if good, put username
			if (loginStatus.get("success") == "true") {
				res.redirect("/dashboard");
				// TODO: halt... how do redirects work in spark?
			} else {
				attributes.put("error", "Your username or password is incorrect.");
			}

			return new ModelAndView(attributes, "login.ftl"); // FIXME
		}, templateEngine);

		// logout
		// Should be used by AJAX -> serves json
		post("/logout", (req, res) -> {
			Map<String, String> data = api.getBody(req);
			String userID = data.get("userID");
			String userToken = data.get("userToken");

			return api.users.logout(userID, userToken);
		}, jsonEngine);

		// list items in the db that match the query provided as a querystring
		// param
		// Browser page
		get("/list-items", (req, res) -> {
			Map<String, Object> attributes = new HashMap<>();

			String jsonStringQuery = req.queryParams("query");
			if (jsonStringQuery == null || jsonStringQuery.length() == 0 || jsonStringQuery.charAt(0) != '{')
				jsonStringQuery = "{}";
			try {
				Bson query = (Bson) JSON.parse(jsonStringQuery);
				List<Document> items = api.items.getItems(query);
				//not sure we need this 
				List<String> itemStrings = new LinkedList<String>();

				for (Document item : items)
					itemStrings.add(item.toJson());

				attributes.put("results", itemStrings);
				attributes.put("items", items);
			} catch (Exception e) {
				attributes.put("error", e.toString());
			}
			
			// TODO: replace with a view-items template
			return new ModelAndView(attributes, "browse.ftl");
		}, templateEngine);
		
		
		// Browser page
		get("/list-item", (req, res) -> {
			String itemID;
			try {
				itemID = (String) req.queryParams("itemID");
			} catch (NumberFormatException e) {
				return errorView(e.toString());
			}

			Document item = api.items.getItemByID(itemID);
			if (item == null)
				return errorView("Item with id: " + itemID + " could not be found");

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("item", item);
			return new ModelAndView(attributes, "ProductFocus.ftl");
		}, templateEngine);
		
		// get list of items bought and list of items sold
		get("/dashboard", (req, res) -> {
			Map<String, String> data = api.getBody(req);
			String userID = data.get("userID");
			String userToken = data.get("userToken");
			List<Document> itemsBought = api.items.getItemsBoughtByUser(userID);
			List<Document> itemsUploaded = api.items.getItemsUploadedByUser(userID, userToken);
			List<Document> itemsSold = api.items.getItemsSold(userID);
			
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("itemsBought", itemsBought);
			attributes.put("itemsUploaded", itemsUploaded);
			attributes.put("itemsSold", itemsSold);
			return new ModelAndView(attributes, "dashboard.ftl");
		}, templateEngine);
		
		// get contact info for peaBay company
		get("/contact", (req, res) -> staticTemplate("contact.ftl"), templateEngine);
		// get about info for peaBay company
		get("/about", (req, res) -> staticTemplate("about.ftl"), templateEngine);
		// get settings for user
		get("/settings", (req, res) -> staticTemplate("settings.ftl"), templateEngine);
		// get profile page for user
		get("/profile", (req, res) -> staticTemplate("profile.ftl"), templateEngine);

		get("/upload", (req, res) -> {
			Map<String, Object> attributes = new HashMap<>();
			
			return new ModelAndView(attributes, "upload.ftl");
		}, templateEngine);

		post("/upload", (req, res) -> {
			Map<String, String> data = api.getBody(req);
			System.out.println(data);
			Document item = (Document) JSON.parse(data.get("item"));
			String userID = data.get("userID");
			String userToken = data.get("userToken");
			
			Map<String, String> result = api.items.upload(item, userID, userToken);
			System.out.println(userID);
			if (result.get("status").equals("listed")) {
				res.redirect("/dashboard");
				return new ModelAndView(new HashMap<>(), "redirecting.ftl");
				// return new ModelAndView(new HashMap<>(), "MyProducts.ftl");
			} else {
				// if upload has illegal inputs, redirect
				// res.redirect("/upload"); // can't use this for now; adds the
				// params to the url as qs params
				Map<String, Object> attributes = new HashMap<>();
				attributes.put("error", result.get("error"));
				return new ModelAndView(attributes, "upload.ftl");
				// TODO: have it redirect with an error
			}
		}, templateEngine);
		
		// Should be used by AJAX -> serves json
		post("/buy", (req, res) -> {

			Map<String, String> body = api.getBody(req);

			if (!body.containsKey("userToken") || !body.containsKey("userID") || !body.containsKey("itemID"))
				return jsonError("Invalid input");

			Map<String, String> output;
			try {
				output = api.sales.buy(body.get("userID"), body.get("itemID"), body.get("userToken"));
			} catch (Exception e) {
				return jsonError(e.getMessage());
			}

			return output;
		}, jsonEngine);
		

	}
	
	private static ModelAndView staticTemplate(String path) {
		return new ModelAndView(new HashMap<>(), "contact.ftl");
	}

	private static ModelAndView errorView(String errmsg) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("message", errmsg);
		return new ModelAndView(attributes, "error.ftl");
	}
	
	private static Map<String, String> jsonError(String errmsg) {
		Map<String, String> output = new HashMap<>();
		output.put("error", errmsg);
		return output;
	}

}
