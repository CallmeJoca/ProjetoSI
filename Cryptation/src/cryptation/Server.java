package cryptation;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    // TAMBÉM PODE SER AGENTE DE CONFIANÇA
    
    public static void main(String[] args) {
        
        System.out.println("--- Entrou no Modo Servidor ---");
        
        System.out.println("Escolha o Protocolo: \n"
                            + " 1- RSA \n"
                            + " 2- Puzzle de Merkle \n"
                            + " 3- Diffie-Hellman \n"
                            + " 4- Sair \n");
        
        String str = null;
        ServerSocket ss = null;
        try {
              ss = new ServerSocket(6666);
            do{
                try{
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    str = (String) dis.readUTF();
                    System.out.println("Client Says = " + str);
                }
                catch(EOFException exc) {
                    continue;
                }
                catch(Exception e){
                    System.out.println(e);
                    break;
                }
            

            }while(!str.equals("end"));
            ss.close();
        }
        catch ( Exception e ) { System.out.println(e); }
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

