import java.io.*;
import java.net.*;

public class Login {

    static final String kuser = "asun"; // your account name
    static final String kpass = "moomoo"; // retrieve password for your account 

    static class MyAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            // I haven't checked getRequestingScheme() here, since for NTLM
            // and Negotiate, the usrname and password are all the same.
            System.err.println("Feeding username and password for " + getRequestingScheme());
            return (new PasswordAuthentication(kuser, kpass.toCharArray()));
        }
    }

    public static void main(String[] args) throws Exception {
        String temp_url = "https://www.outlook.com/owa/exeter.edu";
        URL obj = new URL(temp_url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        System.out.println(connection.getResponseCode());

        // Redirected e-mail URL
        URL url = connection.getURL();

        Authenticator.setDefault(new MyAuthenticator());
        System.out.println("moo");
        InputStream ins = url.openConnection().getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String str;
        while((str = reader.readLine()) != null)
            System.out.println(str);
    }
}