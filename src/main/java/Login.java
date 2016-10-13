/* Login.java
 */

import java.net.*;
import java.io.*;

public class Login
{
	public static void main(String[] args) throws Exception {
		// Inital e-mail URL
		String temp_url = "https://www.outlook.com/owa/exeter.edu";
		URL obj = new URL(temp_url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("GET");
		System.out.println(connection.getResponseCode());

		// Redirected e-mail URL
		String url = connection.getURL();

		req
		/** BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());**/

		

		System.out.println("Hello World!");
	}
}