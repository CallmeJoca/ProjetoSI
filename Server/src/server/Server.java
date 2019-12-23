/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
* Cada novo utilizador vai ter a sua propria thread que é criada quando este se liga ao servidor
* Cada thread vai depois ligar-se por um pipe a outra thread para criar uma ligação entre os dois utilizadores
*/


package server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author henri
 */
public class Server {
    
    public static String getIP(ServerSocket ss) throws Exception {
        return("IP: " + InetAddress.getLocalHost() + "\nPort: " + ss.getLocalPort());
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args){
//        String str = null;
//        ServerSocket ss = null;
//        try {
//              ss = new ServerSocket(6666);
//            do{
//                try{
//                    Socket s = ss.accept();
//                    DataInputStream dis = new DataInputStream(s.getInputStream());
//                    str = (String) dis.readUTF();
//                    System.out.println("Client Says = " + str);
//                }
//                catch(EOFException exc) {
//                }
//                catch(IOException e){
//                    System.out.println(e);
//                    break;
//                }
//            }while(!str.equals("end"));
//            ss.close();
//        }
//        catch ( IOException e ) { System.out.println(e); }
//    }
    public static void main(String[] args) {
        int numberClients = 1;
        ArrayList<Thread> clients = new ArrayList<>();
        Thread thread1 = null;
        clients.add(thread1);
        String str = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(6666);
        }
        catch ( IOException e ) { System.out.println(e); }
        try {
            System.out.println(getIP(ss));
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
//        while(true){
//                do{
//                    try{
//                        Socket s = ss.accept();
//                        DataInputStream dis = new DataInputStream(s.getInputStream());
//                        str = (String) dis.readUTF();
//                    }
//                    catch(EOFException exc) {
//                    }
//                    catch(IOException e){
//                        System.out.println(e);
//                        break;
//                    }
//                }while(!str.equals("end"));
//        }
//        try {
//            ss.close();
//        } catch (IOException e) { System.out.println(e); }
    }
}