package server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    
    public int printMenu(){
        int option = -1;
        
        System.out.println("Menu:");
        System.out.println("1. Escolher porta a abrir");
        System.out.println("2. Fazer print dos clientes");
        System.out.println("3. Desligar");
        
        
        return option;
    }
    
    public static void main(String[] args) {
        
        
//        String str = null;
//        ServerSocket ss = null;
//        try {
//            ss = new ServerSocket(6666);
//            do{
//                try{
//                    Socket s = ss.accept();
//                    DataInputStream dis = new DataInputStream(s.getInputStream());
//                    str = (String) dis.readUTF();
//                    System.out.println("Client Says = " + str);
//                }
//                catch(EOFException exc) {
//                    continue;
//                }
//                catch(Exception e){
//                    System.out.println(e);
//                    break;
//                }
//                
//                
//            }while(!str.equals("end"));
//            ss.close();
//        }
//        catch ( Exception e ) { System.out.println(e); }
    }
    
}
