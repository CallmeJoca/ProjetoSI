package Server.src.server;

import java.util.Scanner;

public class InputHandler {
    public Scanner scanner;

    public InputHandler() {
        scanner = new Scanner(System.in);;
    }

    public String input(){
        String input = "";
        long sTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - sTime < 50) {
            try {
                if (System.in.available() > 0) {
                    input = scanner.nextLine();
                }
            }
            catch(Exception e){
            }
        }
        return input;
    }

    public void close(){
        scanner.close();
    }
}
