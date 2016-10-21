import java.io.*;
import java.net.*;

public class Login {

    // username and pw
    static String username = ""; // your account name
    static String password = ""; // retrieve password for your account 

    static class MyAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            System.err.println("Feeding username and password for " + getRequestingScheme());
            return (new PasswordAuthentication(username, password.toCharArray()));
        }
    }

    public static boolean login(String user, String pass) throws Exception {
        username = user;
        password = pass;
        // URL of exeter website
        String temp_url = "https://www.outlook.com/owa/exeter.edu";
        URL obj = new URL(temp_url);

        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");

        // make sure this returns 401 so that website has moved
        System.out.println(connection.getResponseCode());

        // Redirected e-mail URL
        // moo
        HttpURLConnection.setFollowRedirects(false);
        URL url = connection.getURL();

        Authenticator.setDefault(new MyAuthenticator());
        //System.out.println("moo");
        try {
            System.setProperty("http.maxRedirects", "3");
            connection = (HttpURLConnection) url.openConnection();
            System.out.println("opened connection");
            InputStream ins = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String str;
            while((str = reader.readLine()) != null) {
                System.out.println(str);
            }
            
        } catch (Exception e) {
            System.out.println("exception");
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("http.maxRedirects", "1");
    }
}