import java.io.*;
import java.net.*;

import org.apache.commons.lang3.StringEscapeUtils;

import sun.net.www.protocol.http.AuthCacheValue;
import sun.net.www.protocol.http.AuthCacheImpl;

public class Login {

    // username and pw
    private static String username = ""; // your account name
    private static String password = ""; // retrieve password for your account 

    static class MyAuthenticator extends Authenticator {
        int tries = 0;
        public PasswordAuthentication getPasswordAuthentication() {
            System.err.println("Feeding username and password for " + getRequestingScheme());
            if (tries == 0) {
                tries++;
                return (new PasswordAuthentication(username, password.toCharArray()));
            } else {
                //throw an unchecked exception
                throw new IndexOutOfBoundsException();
            }
        }
    }

    public static boolean login(String userID1, String pw1) {
    	// TODO: CHECK IF THIS ACTUALLY RESTS THE AUTHENTICATOR CACHE
    	AuthCacheValue.setAuthCache(new AuthCacheImpl());
    	
    	System.out.println(userID1 + " " + pw1);
		
		// ESCAPE THE JAVASCRIPT STRING
		// TODO: SOME CASES MIGHT NOT ESCAPE CORRECTLY
		String userID = StringEscapeUtils.escapeEcmaScript(userID1);
		String pw = StringEscapeUtils.escapeEcmaScript(pw1);
		
		System.out.println(userID + " " + pw);
		
        //System.out.println("moo");
        try {
        	
            username = userID;
            password = pw;
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
            URL url = connection.getURL();
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
            return false;
        }
        System.out.println("login successful");
        return true;
    }

    public static void main(String[] args) throws Exception {

    }
}