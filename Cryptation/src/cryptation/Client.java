
package cryptation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {

    public static void main(String[] args) {
        
        System.out.println("--- Entrou no Modo Cliente ---");
        String msg = null;
        String msgRecebida = null;
        String ip = null;
        
        System.out.println(Client2.getIP());
        
        // O SERVIDOR TEM DE ESTAR LIGADO PARA HAVEREM CLIENTES
        // NO SERVIDOR, QUEM ENTRAR NO MODO SERVIDOR ESCOLHE QUAL O PROTOCOLO A SER USADO
        
        // OS CLIENTES CONECTAM-SE AO SERVIDOR ATRAVES DE UMAA SOCKET C O IP DO SERVER
        // VEEM QUAIS SAO OS CLIENTES DISPONIVEIS
        // ESCOLHEM O IP DE UM
        // O OUTRO TEM DE RECEBER UMA NOTIFICACAO A PERGUNTAR SE QUER CONECTAR, SENAO CATCH ERROR NA SOCKET
        // SE ACEITAR, COMUNICAM DIRETAMENTE ATRAVES DE UMA SOCKET COM
        
        do {
            Users u = new Users(ip);
            System.out.println(u);
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
        
    }
    
    public static String getIP () {
        String ip = null;
        
        try(final DatagramSocket socket = new DatagramSocket()){
              socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
                System.out.println(ip);
            } catch (SocketException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return ip;
    }
}