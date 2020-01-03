package cryptation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client2 {
    
    public static void main(String[] args) {
        String str = null;
        ServerSocket ss = null;
        
        
        // TEM DE RECEBER UMA NOTIFICACAO A PERGUNTAR SE QUER CONECTAR A QUEM QUER ESTABELECER LIGACAO COM ELE
        
        try {
              ss = new ServerSocket(6666);
            do{
                try{
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    str = (String) dis.readUTF();
                    System.out.println("Client Says = " + str);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF("OLA");
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
