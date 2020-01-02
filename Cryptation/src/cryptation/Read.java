package cryptation;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Read {

    public static int readInt() {
        while (true) {

            try {
                return Integer.parseInt(readString().trim());

            } catch (Exception e) {
                System.out.println("Not an Integer.");
            }
        }
    }
    
    public static String readString() {
        String str = null;

        try {
            BufferedReader bIn = new BufferedReader (new InputStreamReader(System.in));
            str = bIn.readLine();

        } catch (Exception e) {
            System.out.println("Not a String.");
        }
        return str;
    }
}