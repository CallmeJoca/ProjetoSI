package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Cliente {
    
    public static void main(String[] args) {
        
        try {
            Socket s = new Socket("192.168.43.27", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("Hello Server");
            dout.flush();
            dout.close();
            s.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}