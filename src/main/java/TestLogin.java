
public class TestLogin {

	public static void main(String[] args) {

		boolean good;
		try {
			good = Login.login("asun1", "moo");
		} catch (Exception e) {
			good = false;
		}
		System.out.println(good);

	}
}