
package cryptation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {

    public static void main(String[] args) {
        
        // OS CLIENTES CONECTAM-SE AO SERVIDOR ATRAVES DE UMA SOCKET C O IP DO SERVER
        String serverIP = "192.168.0.181";
        String msg = null;
        String msgRecebida = null;
        String ip = null;
        
        
        System.out.println("--- Entrou no Modo Cliente ---");
        
        // REGISTO DO USER
        Users u = new Users(getIP());
        
        int ligar = 1;
        while (ligar >= 1 || ligar <= 2) {
            
            System.out.println("1 - Ligar a um utilizador\n2 - Esperar por ligação");
            ligar = Read.readInt();

            if (ligar == 1) {
               
                // ENVIO DOS DADOS DO USER PARA O SERVIDOR
                try {
                        Socket s = new Socket(serverIP, 6666);
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        u.status = 1; // é visto como Ocupado.
                        dout.writeUTF(u.toString());

                        dout.flush();
                        dout.close();
                        s.close();
                } catch (Exception e) {
                        System.out.println("Não foi possível estabelecer a ligação.");
                }
                
                // VEEM QUAIS SAO OS CLIENTES DISPONIVEIS
                ArrayList<String> available_users = Server.listAvailable();

                // ESCOLHEM O IP DE UM
                System.out.println(Server.connectTwoUsers());
                
            }
            
            if (ligar == 2) {
                // ENVIO DOS DADOS DO USER PARA O SERVIDOR
                try {
                        Socket s = new Socket(serverIP, 6666);
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        // u.status = 0; // é visto como livre. o status já é definido como 0 inicialmente em 'Users'.
                        dout.writeUTF(u.toString());

                        dout.flush();
                        dout.close();
                        s.close();
                } catch (Exception e) {
                        System.out.println("Não foi possível estabelecer a ligação.");
                }
                
                // passa a ser da classe Client2 (aquele que espera pela ligação).
                Client2.main(args);
            }
        }
        /*
        do {
            msg = Read.readString();
            try {
                Socket s = new Socket("192.168.0.181", 6666);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                DataInputStream dis = new DataInputStream(s.getInputStream());
                
                dout.writeUTF(msg);
                
                msgRecebida = (String) dis.readUTF();
                System.out.println("Recebi = " + msgRecebida);
                dout.flush();
                dout.close();
                s.close();
            } catch(Exception e) {
                System.out.println("Não foi possível estabelecer a ligação.");
            }
        } while (!msg.equals("end"));
        
*/
    }
    
    public static String getIP () {
        String ip = null;
        
        try(final DatagramSocket socket = new DatagramSocket()){
              socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            } catch (SocketException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return ip;
    }
}