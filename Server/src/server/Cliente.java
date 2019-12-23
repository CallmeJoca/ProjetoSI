package Server.src.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Cliente {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        do {
            try {
                input = scanner.next();
                Socket s = new Socket("192.168.1.84", 6666);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                dout.writeUTF(input);
                dout.flush();
                dout.close();
                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (!input.equals("end")) ;
    }
}

//192.168.43.27 doutra pessoa
//192.168.1.75 computer
//192.168.1.84 laptop