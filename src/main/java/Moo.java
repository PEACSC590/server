

public class Moo {

	public static void main(String[] args) {
		Login moo = new Login();
		moo.login("asun1", "moo");
		try {
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		moo = new Login();
		moo.login("asun1", "moo");
	}
	
}
