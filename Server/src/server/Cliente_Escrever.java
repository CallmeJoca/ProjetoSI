package Server.src.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Cliente_Escrever {

    public static void main(String[] args) {

        InputHandler inputHandler = new InputHandler();
        String input = "";
        Scanner scanner = new Scanner(System.in);;
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

//192.168.43.27 doutra pessoa
//192.168.1.75 computer
//192.168.1.84 laptop