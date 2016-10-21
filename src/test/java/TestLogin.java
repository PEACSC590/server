
public class TestLogin {

    public static void main(String[] args) {
        boolean good = false;
        try {
            good = Login.login("asun1", "Quantal^1");
        } catch (Exception e) {

        }
        System.out.println(good);
    }
}
