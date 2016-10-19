import java.io.*;
import java.net.*;

public class TestLogin {

    public static void main(String[] args) {
        Login login = new Login();
        try {
            boolean good = login.login("asun1", "moo");
        } catch (Exception e) {
            boolean good = false;
        }
        System.out.println(good)
        
        
    }
}