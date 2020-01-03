package cryptation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
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

            }while(!userInfo.equals("end"));
            ss.close();
        }
        catch ( Exception e ) { System.out.println(e); }
    }
    
    
    public static ArrayList<String> listAvailable() {
        ArrayList<String> available = new ArrayList<>();
        
        File fin = new File("./userData.txt");
        
        BufferedReader br = null; 
        try {
            br = new BufferedReader(new FileReader(fin));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        String st; 
        try {
            while ((st = br.readLine()) != null) { 
                String[] user_info = st.split(";");
                
                // Verifica se o utilizador lido está livre
                if (user_info[3].equals("0")) {

                    // se estiver, adiciona-o a 'available'.
                    // 'available' contém os usernames dos utilizadores disponiveis.
                    String available_username = user_info[0];
                    available.add(available_username);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return available;
    }
    
    public static boolean connectTwoUsers () {
        ArrayList<String> available = listAvailable();
        
        System.out.println("\nUtilizadores disponíveis agora:");
        for (int i=0; i<available.size(); i++) {
            
        }
        
        System.out.println("Indique o username do utilizador a conectar: ");
        String client2 = Read.readString();
        if (available.contains(client2)) {
            
            String IPtoConnect = getIPFromUsername (client2);
            if (IPtoConnect != null) {
                return true;
            }
        }
        return false;
    }
    
    public static String getIPFromUsername (String username) {
        
        File fin = new File("./userData.txt");
        
        BufferedReader br = null; 
        try {
            br = new BufferedReader(new FileReader(fin));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        String st; 
        try {
            while ((st = br.readLine()) != null) { 
                String[] user_info = st.split(";");
                
                if (user_info[0].equals(username)) {
                    return user_info[1];
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }
}

