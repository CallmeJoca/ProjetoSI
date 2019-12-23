package Server.src.server;

import java.io.DataOutputStream;
import java.net.Socket;


public class Cliente {

    public static void main(String[] args) {
        
        try {
            Socket s = new Socket("192.168.1.75", 6666);
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

//192.168.43.27 doutra pessoa
//192.168.1.75 computer
//192.168.1.84 laptop