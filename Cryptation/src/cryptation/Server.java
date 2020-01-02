package cryptation;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
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
    
        // O SERVIDOR TEM DE ESTAR LIGADO PARA HAVEREM CLIENTES
        // NO SERVIDOR, QUEM ENTRAR NO MODO SERVIDOR ESCOLHE QUAL O PROTOCOLO A SER USADO
    
    public static void main(String[] args) {
        
        System.out.println("--- Entrou no Modo Servidor ---");
        
        System.out.println("Escolha o Protocolo: \n"
                            + " 1- RSA \n"
                            + " 2- Puzzle de Merkle \n"
                            + " 3- Diffie-Hellman \n"
                            + " 4- Sair \n");
        
        int protocolo = Read.readInt();
        // REGISTA O PROTOCOLO ESCOLHIDO
        // GUARDA INFORMAÇÃO SOBRE OS USERS CONECTADOS
        
        
        
        String userInfo = null;
        ServerSocket ss = null;
        try {
              ss = new ServerSocket(6666);
            do{
                try{
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    userInfo = (String) dis.readUTF();
                    
                    FileOutputStream fos = new FileOutputStream("userData.txt");
                    fos.write(userInfo.getBytes());
                    fos.close(); 

                   }
                   catch(Exception ex) {}
                    
            System.out.println("Client Says = " + userInfo);

            }while(!userInfo.equals("end"));
            ss.close();
        }
        catch ( Exception e ) { System.out.println(e); }
    }
}

