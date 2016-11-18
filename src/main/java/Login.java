import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class Login {

	private static final String imapHost = "outlook.office365.com";

	public static boolean login(String userID, String password) {
		boolean success = false;
		
		try {
			userID = URLDecoder.decode(userID, "UTF-8");
			password = URLDecoder.decode(password, "UTF-8");
		} catch (UnsupportedEncodingException e1) {}

		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect(imapHost, userID.toLowerCase() + "@exeter.edu", password);
			success = true;
		} catch (MessagingException e) {}

		return success;
	}

}