package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Read {

    public static int readInt() {

        while (true) {
            try {
                return Integer.parseInt(readString().trim());
            } catch (NumberFormatException e) {
                System.out.println("Not an Integer.");
            }
        }
    }

    public static String readString() {
        String str = null;

        while (true) {
            try {
                BufferedReader bIn = new BufferedReader(new InputStreamReader(System.in));
                str = bIn.readLine();
                return str;
            } catch (IOException e) {
                System.out.println("Not a String.");
            }
        }
    }
}
