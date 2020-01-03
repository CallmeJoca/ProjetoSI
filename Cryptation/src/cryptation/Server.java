package cryptation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    ArrayList<String> users_connected;
    
    public Server() {
        this.users_connected = new ArrayList<>();
    }
    
    public Server getServer() {
        return this;
    }
    
        // TAMBÉM PODE SER AGENTE DE CONFIANÇA
    
        // O SERVIDOR TEM DE ESTAR LIGADO PARA HAVEREM CLIENTES
        // QUEM ENTRAR NO MODO SERVIDOR ESCOLHE QUAL O PROTOCOLO A SER USADO
    
    public static void main(String[] args) {
        
        Server server = new Server();
        
        server.users_connected = new ArrayList<>();
    
        if (!deleteAllData()) {
            System.out.println("Servidor com erros. Reinicie.");
        }
        
        System.out.println("--- Entrou no Modo Servidor ---");
        
        System.out.println("Escolha o Protocolo: \n"
                            + " 1- RSA \n"
                            + " 2- Puzzle de Merkle \n"
                            + " 3- Diffie-Hellman \n"
                            + " 4- Sair \n");
        
        int protocolo = Read.readInt();
        
        if (protocolo == 4) {
            System.exit(1);
        }
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
                    server.users_connected.add(userInfo);
                    System.out.println("adicionado user " + userInfo);
                    System.out.println(server.users_connected.size());
                   }

                   catch(Exception ex) {}

            }while(!userInfo.equals("end"));
            ss.close();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static ArrayList<String> listAvailable(Server server) {
        ArrayList<String> available = new ArrayList<>();
        
        for (int i=0; i<server.users_connected.size(); i++) {
            
            System.out.println(server.users_connected.get(i));
            String[] user_info = available.get(i).split(";");
            
            // Verifica se o utilizador lido está livre
            System.out.println(user_info[3]);
            
            if (user_info[3].contains("status=0")) {

                // se estiver, adiciona-o a 'available'.
                // 'available' contém os usernames dos utilizadores disponiveis.
                String available_username = user_info[0];
                available.add(available_username);
            }
                
        }
        
        return available;
    }
    
    public static boolean connectTwoUsers (Server server) {
        ArrayList<String> available = listAvailable(server);
        
        if (available.size() == 0) {
            System.out.println("Não há utilizadores disponíveis de momento.");
            return false;
        }
        else {
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
    
    // Auxiliar para eliminar e atualizar informação sobre utilizadores no ficheiro 'userData.txt'
    public static ArrayList<String> listAllUserData() {
        ArrayList<String> userData = new ArrayList<>();
        
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
                userData.add(st);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userData;
    }
    
    public static boolean deleteAllData () {
        File file = new File("./userData.txt");
        return file.delete();
    }
}

