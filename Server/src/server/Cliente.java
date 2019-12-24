package Server.src.server;

import java.io.DataOutputStream;
import java.net.Socket;


public class Cliente {

    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        String input = "";
        do {
            try {
                input = inputHandler.input();
                if (!input.equals("")){
                    Socket s = new Socket("localhost", 6666);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF(input);
                    dout.flush();
                    dout.close();
                    s.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (!input.equals("end")) ;
        inputHandler.close();
    }
}