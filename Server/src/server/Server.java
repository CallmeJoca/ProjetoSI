package server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Server implements Runnable{
    
    ServerSocket sSocket;
    Thread t;
    ArrayList[] clients;
    
    public Server(int port) throws IOException{
        
        sSocket = new ServerSocket(port);
        t = new Thread(this);
        t.start();
    }
    
    
    public int printMenu(Socket clientInput) throws IOException, ClassNotFoundException{
        ObjectInputStream inputText = new ObjectInputStream(clientInput.getInputStream());
        
        System.out.println("Menu:");
        System.out.println("1. Escolher porta a abrir");
        System.out.println("2. Fazer print dos clientes");
        System.out.println("3. Desligar");
        
        return ((int) inputText.readObject());
    }
    
    /**
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void run(String[] args) throws IOException, ClassNotFoundException {
          
        while(true) {
            int option = 0;
            Socket clientInput = sSocket.accept();
            option = printMenu(clientInput);
            
            if (option == 1){
                
            }
            else if (option == 2) {
                
            }
            else if (option == 3) {
                
            }
            else{
                
            }
        }
          
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

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
