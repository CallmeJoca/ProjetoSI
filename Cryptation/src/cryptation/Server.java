package cryptation;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
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
    
}

