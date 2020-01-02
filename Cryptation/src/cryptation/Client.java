
package cryptation;

import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        
        System.out.println("--- Entrou no Modo Cliente ---");
        String msg = null;
        
        do {
            System.out.println("Introduza a mensagem: ");
            msg = Read.readString();
            try {
                Socket s = new Socket("192.168.0.181", 6666);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                dout.writeUTF(msg);
                dout.flush();
                dout.close();
                s.close();
            } catch(Exception e) {
                System.out.println("Não foi possível estabelecer a ligação.");
            }
        } while (!msg.equals("end"));
        
        
    }
}