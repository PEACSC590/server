import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import java.net.UnknownHostException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.Request;
import spark.ResponseTransformer;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class Main {

	protected static API api;

	public static void main(String[] args) throws MongoException, UnknownHostException {

		// set server port and configure /public to be served statically
		port(Integer.valueOf(System.getenv("PORT")));
		// don't copy the files into target if in dev so that the program
		// doesn't have to be rebuilt for static file changes
		if (System.getenv("STATIC_DEV") == "true") {
			String projectDir = System.getProperty("user.dir");
			String staticDir = "/src/main/resources/public";
			externalStaticFileLocation(projectDir + staticDir);
		} else {
			staticFileLocation("/public");
		}

		// only use the official mongo db uri for production--when it is on the
		// machine (only MONGO_DEV_URI should be in .env for development)
		String mongoURI = System.getenv("MONGODB_URI") != null ? System.getenv("MONGODB_URI")
				: System.getenv("MONGODB_DEV_URI");
		Database database = new Database(new MongoClientURI(mongoURI));
		MongoDatabase db = database.db;

		api = new API(db); // note: the way the methods are organized and
		// accessed is subject to change

		FreeMarkerEngine templateEngine = new FreeMarkerEngine();
		ResponseTransformer jsonEngine = JsonUtil.json();

		// REFRESH ITEMS EVERY 5 MINUTES
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

		// LEAVE TESTING CODE HERE
//		TestAPI test = new TestAPI(api);
//		test.test();

		get("/", (request, response) -> {
			System.out.println("GET LOGIN");
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("pageName", "login");
			return new ModelAndView(attributes, "login.ftl");
		}, templateEngine);
		
		// DONE AND TESTED
		get("/login", (request, response) -> {
			System.out.println("GET LOGIN");
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("pageName", "login");
			return new ModelAndView(attributes, "login.ftl");
		}, templateEngine);

		// DONE AND TESTED
		post("/login", (req, res) -> {
			System.out.println("POST LOGIN");
			Map<String, String> data = api.getBody(req);
			String userID = data.get("userID");
			String password = data.get("password");

			return api.users.login(userID, password);
		}, jsonEngine);

		// Should be used by AJAX -> serves json
		post("/logout", (req, res) -> {
			Map<String, String> data = api.getBody(req);
			String userID = data.get("userID");
			String userToken = data.get("userToken");

			return api.users.logout(userID, userToken);
		}, jsonEngine);

		get("/browse", (req, res) -> {
			Map<String, String> data = getUserData(req);
			
			if (data.containsKey("redirect"))
				return new ModelAndView(data, "loadWithLocalData.ftl");
			
			List<Document> items = api.items.getBuyableItems(data.get("userID"));

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("items", items);
			attributes.put("pageName", "browse");
			return new ModelAndView(attributes, "browse.ftl");
		}, templateEngine);

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

		// GET LIST OF ITEMS BOUGHT AND SOLD
		get("/dashboard", (req, res) -> {
			Map<String, String> data = getUserData(req);
			if (data.containsKey("redirect"))
				return new ModelAndView(data, "loadWithLocalData.ftl");

			String userID = data.get("userID");
			String userToken = data.get("userToken");
			
			if (userID == "null" || userToken == "null" || !api.userTokens.testUserTokenForUser(userID, userToken)) {
				res.redirect("/login");
				return errorView("NOT AUTHENTICATED");
			}

			List<Document> itemsBought = api.items.getItemsBoughtByUser(userID);
			List<Document> itemsListed = api.items.getItemsListedByUser(userID);
			List<Document> itemsSold = api.items.getItemsSoldByUser(userID);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("itemsBought", itemsBought);
			attributes.put("itemsListed", itemsListed);
			attributes.put("itemsSold", itemsSold);
			attributes.put("pageName", "dashboard");
			return new ModelAndView(attributes, "dashboard.ftl");
		}, templateEngine);

		// GET INFO ABOUT PEABAY COMPANY
		get("/about", (req, res) -> staticTemplate("about.ftl", "about"), templateEngine);

		get("/pendingitems", (req, res) -> {
			Map<String, String> data = getUserData(req);
			if (data.containsKey("redirect"))
				return new ModelAndView(data, "loadWithLocalData.ftl");

			List<Document> pendingSales = api.items.getPendingSales(data.get("userID"));
			List<Document> pendingPurchases = api.items.getPendingPurchases(data.get("userID"));

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("pendingSales", pendingSales);
			attributes.put("pendingPurchases", pendingPurchases);
			attributes.put("pageName", "pendingitems");
			return new ModelAndView(attributes, "pendingitems.ftl");
		}, templateEngine);

		get("/upload", (req, res) -> staticTemplate("upload.ftl", "upload"), templateEngine);

		post("/upload", (req, res) -> {
			Map<String, String> body = api.getBody(req);
			
			if (!body.containsKey("userID") || !body.containsKey("userToken") || !body.containsKey("itemID"))
				return jsonError("Invalid input");
		
			// System.out.println(data);
			Document item = (Document) JSON.parse(body.get("item"));

			return api.items.upload(body.get("userID"), body.get("userToken"), item);
		}, jsonEngine);

		// Should be used by AJAX -> serves json
		post("/buy", (req, res) -> {
			Map<String, String> body = api.getBody(req);
			if (!body.containsKey("userID") || !body.containsKey("userToken") || !body.containsKey("itemID"))
				return jsonError("Invalid input");
			
			Map<String, String> output;
			try {
				output = api.sales.buy(body.get("userID"), body.get("userToken"), body.get("itemID"));
			} catch (Exception e) {
				return jsonError(e.getMessage());
			}

			return output;
		}, jsonEngine);

		post("/sell", (req, res) -> {
			Map<String, String> body = api.getBody(req);
			if (!body.containsKey("userID") || !body.containsKey("userToken") || !body.containsKey("itemID"))
				return jsonError("Invalid input");

			Map<String, String> output;
			try {
				output = api.sales.sell(body.get("userID"), body.get("userToken"), body.get("itemID"));
			} catch (Exception e) {
				return jsonError(e.getMessage());
			}
			return output;
		}, jsonEngine);

		post("/cancelPendingSale", (req, res) -> {

			Map<String, String> body = api.getBody(req);

			if (!body.containsKey("userToken") || !body.containsKey("userID") || !body.containsKey("itemID"))
				return jsonError("Invalid input");

			Map<String, String> output;
			try {
				output = api.sales.cancelPendingSale(body.get("userID"), body.get("userToken"), body.get("itemID"));
				res.redirect("/pendingitems");
			} catch (Exception e) {
				return jsonError(e.getMessage());
			}

			return output;

		}, jsonEngine);

		post("/refuseSale", (req, res) -> {
			Map<String, String> body = api.getBody(req);

			if (!body.containsKey("userToken") || !body.containsKey("userID") || !body.containsKey("itemID"))
				return jsonError("Invalid input");
			
			Map<String, String> output;
			try {
				output = api.sales.refuseSale(body.get("userID"), body.get("userToken"), body.get("itemID"));
				res.redirect("/pendingitems");
			} catch (Exception e) {
				return jsonError(e.getMessage());
			}

			return output;

		}, jsonEngine);

		post("/unlist", (req, res) -> {
			Map<String, String> body = api.getBody(req);
			if (!body.containsKey("userID") || !body.containsKey("userToken") || !body.containsKey("itemID"))
				return jsonError("Invalid input");
			
			Map<String, String> output;
			try {
				output = api.items.unlist(body.get("userID"), body.get("userToken"), body.get("itemID"));
				res.redirect("/dashboard");
			} catch (Exception e) {
				return jsonError(e.getMessage());
			}
			return output;

		}, jsonEngine);

		//exception(Exception.class, (exc, req, res) -> {
			//res.body(exc.getMessage());
		//});
	}

	private static ModelAndView staticTemplate(String path, String pageName) {
			
		Map<String, Object> attributes = new HashMap<>();
		if (!pageName.isEmpty())
			attributes.put("pageName", pageName);
		return new ModelAndView(attributes, path);
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

	private static Map<String, String> getUserData(Request req) {
		Map<String, String> data = api.getBody(req);
		String userID = data.get("userID");
		String userToken = data.get("userToken");
		if (userID == null || userToken == null || userID.isEmpty() || userToken.isEmpty()) {
			Map<String, String> attributes = new HashMap<>();
			attributes.put("redirect", req.pathInfo());
			attributes.put("get", "[\"userID\", \"userToken\"]");
			return attributes;
		} else {
			return data;
		}
	}

}
