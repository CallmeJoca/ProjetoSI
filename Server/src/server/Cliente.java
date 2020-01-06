package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {
    
// Strings utilzadas para chave de sessão AC
    public final String forBob = "bobnopaísdasmaravilhas";
    public final String forAlice = "alicenopaisdasmaravilhas";
    
    // String utilzada para distribuição de chaves
    public final String forcliente = "servidornopaisdasmaravilhas";    

// Atributos básicos
    private String username;
    private String serverIP;
    private int serverDoor;
    private int clientDoor; // pode ser mudado para estático?
    private Socket serverSocket;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    //  Construtores
    public Cliente() {}
    
    public Cliente(String username, String serverIP, int serverDoor, int clientDoor) {
        this.username = username;
        this.serverIP = serverIP;
        this.serverDoor = serverDoor;
        this.clientDoor = clientDoor;
    }
    
    
    //  Getters e Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getServerIP() {
        return serverIP;
    }
    
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
    
    public int getServerDoor() {
        return serverDoor;
    }
    
    public void setServerDoor(int serverDoor) {
        this.serverDoor = serverDoor;
    }
    
    public int getClientDoor() {
        return clientDoor;
    }
    
    public void setClientDoor(int clientDoor) {
        this.clientDoor = clientDoor;
    }
    
    public Socket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    //  Método que permite esta instancia de cliente comunicar com o servidor
    public boolean establishServerConnection() throws ClassNotFoundException {
        
        boolean loop = true;
        
        try {
            serverSocket = new Socket(serverIP, serverDoor);
            toServer = new ObjectOutputStream(serverSocket.getOutputStream());
            fromServer = new ObjectInputStream(serverSocket.getInputStream());
           
            
            
            System.out.println("fuck");
            
            toServer.writeObject(username + " conectou-se ao servidor.");
            
            System.out.println("this");
            
            System.out.println((String)fromServer.readObject());
            
            
            System.out.println("shit");
            
            
//            toServer.close();
//            fromServer.close();
              
            return true;
            
        }catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean sendData() {
        try {
            
            InetAddress inetAddress = InetAddress.getLocalHost();
            String info = username + "__" + inetAddress.getHostAddress();
            toServer.writeObject(2);
            toServer.writeObject(info);
            
            //
            //toServer.close();
            //fromServer.close();
            return (Boolean)fromServer.readObject();

        }catch(IOException e) {
            System.out.println(e);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return false;
    }
    
    public boolean closeServerConnection() {
        try {
            serverSocket.close();
            return true;
        }catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public String receiveClientIP() {
        try {
            return (String)fromServer.readObject();
        }catch(IOException ex) {
            System.out.println(ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String requestUsers() {
        
        try {
            //enviar 1 para listar users (Servidor entrar na opção de listar users)
            toServer.writeObject(1);
            ArrayList<String> users = (ArrayList<String>)fromServer.readObject();
            printUsers(users);
            
            System.out.println("Para quem queres sussurrar?");
            String nome = Read.readString();
            
            // Mecanismo para verificar se um Nome ou IP estão na lista fornecida
            boolean verifiy = false;
            do {
                if(list_names(users).contains(nome) || list_ips(users).contains(nome)) {
                    verifiy = true;
                }
                else {
                    System.out.println("Nome ou IP não reconhecido na lista, tenta outra vez");
                    nome = Read.readString();
                }
            }while(!verifiy);
            
            if(!list_ips(users).contains(nome))
                nome = IpGivenName(users, nome);
            
            return nome;
            
        }catch(IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return null;
        // Eventualmente tentar trocar de atributos?
    }
    
    public void printUsers(ArrayList<String> list) {
        for(String s: list) {
            String[] clientData = s.split("__");
            System.out.println("Nome : " + clientData[0] + "\tIP : " + clientData[1] + "\n");
        }
    }
    
    
    // Pesquisar nos nomes
    public static ArrayList<String> list_names(ArrayList<String> list) {
        ArrayList<String> lista = new ArrayList<>();
        for(String nome: list) {
            String[] tmp = nome.split("__");
            lista.add(tmp[0]);
        }
        
        return lista;
    }
    
    // Pesquisar nos ips
    public static ArrayList<String> list_ips(ArrayList<String> list) {
        ArrayList<String> lista = new ArrayList<>();
        for(String nome: list) {
            String[] tmp = nome.split("__");
            lista.add(tmp[1]);
        }
        
        return lista;
    }
    
    public static String IpGivenName(ArrayList<String> list, String nome) {
        ArrayList<String> lista = new ArrayList<>();
        for(String tmp: list) {
            String[] tmp2 = nome.split("__");
            if(tmp2[0].equals(nome))
                return tmp2[1];
        }
        return null; //já sabemos que está na lista portanto tanto faz
    }    
}