import java.io.*;
import java.net.*;

public class Login {

    private static String username = "";
    private static String password = "";
    private static URL url = null;
    
    static class MyAuthenticator extends Authenticator {
    	int tries;
        public PasswordAuthentication getPasswordAuthentication() {
            System.err.println("Feeding username and password for " + getRequestingScheme());
            if (tries == 0) {
            	tries++;
                return (new PasswordAuthentication(username, password.toCharArray()));
            } else {
                // THROW AN UNCHECKED EXCEPTION TO TRIGGER THE CATCH BLOCK BELOW
                throw new IndexOutOfBoundsException();
            }
        }
    }
    
    
    public static boolean login(String userID1, String pw1) {

		// System.out.println(userID1 + " " + pw1);

		// ESCAPE THE JAVASCRIPT STRING
		// TODO: SOME CASES MIGHT NOT ESCAPE CORRECTLY
		String userID = "";
		String pw = "";
		try {
			userID = URLDecoder.decode(userID1, "UTF-8");
			pw = URLDecoder.decode(pw1, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(userID + " " + pw);
		
        //System.out.println("moo");
        try {
        	
            username = userID;
            password = pw;
            
            if (url == null) {
            	// URL of exeter website
                String temp_url = "https://www.outlook.com/owa/exeter.edu";
                URL obj = new URL(temp_url);

                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");

                // make sure this returns 401 so that website has moved
                System.out.println(connection.getResponseCode());

                // Redirected e-mail ULR
                // moo
                HttpURLConnection.setFollowRedirects(false);
                url = connection.getURL();
            }
            HttpURLConnection connection;
            Authenticator.setDefault(new MyAuthenticator());
            //CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            //System.setProperty("-Dhttp.maxRedirects", "2");
            //System.setProperty("http.maxRedirects", "2");
            //HttpURLConnection.setFollowRedirects(false);
            connection = (HttpURLConnection) url.openConnection();
            InputStream ins = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String str;
            while((str = reader.readLine()) != null) {
                System.out.println(str);
            }
            
        } catch (Exception e) {
            System.out.println("login failed");
            Authenticator.setDefault(null);
            return false;
        }
        System.out.println("login successful");
        return true;
    }

    public static void main(String[] args) throws Exception {

    }
}