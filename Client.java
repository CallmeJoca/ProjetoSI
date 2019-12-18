package client;

import java.io.DataOutputStream;
import java.net.Socket;


public class Client {

    public static void main(String[] args) {
        
        try {
            // criação da socket para a usar na comunicação com o servidor
            //                    | Server IP   | Server Port
            Socket s = new Socket("192.168.43.27", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("Hello Server");
            dout.flush();
            dout.close();
            s.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}