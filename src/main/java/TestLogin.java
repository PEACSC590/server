import java.io.*;
import java.net.*;

public class TestLogin {

    public static void main(String[] args) {
        Login login = new Login();
        boolean good = true;
        try {
            good = login.login("asun1", "moo");
        } catch (Exception e) {
            
        }
        System.out.println(good);
        
        
    }
}