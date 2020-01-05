package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class Cliente {
    
    // Atributos básicos
    private String username;
    private String serverIP;
    private int serverDoor;
    private int clientDoor; // pode ser mudado para estático?
    private Socket serverSocket;

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
    public boolean establishServerConnection() {
        
        try {
            serverSocket = new Socket(serverIP, serverDoor);
            DataOutputStream dataOut = new DataOutputStream(serverSocket.getOutputStream());
            dataOut.writeUTF(username + "conectou-se ao servidor.");
            dataOut.flush();
            
            dataOut.close();
            
            return true;
            
        }catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean sendData() {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(serverSocket.getInputStream());
            InetAddress inetAddress = InetAddress.getLocalHost();
            String info = username + "__" + inetAddress.getHostAddress();
            toServer.write(4);
            toServer.writeUTF(info);
            //
            
            toServer.close();
            fromServer.close();
            return true;

        }catch(IOException e) {
            System.out.println(e);
            return false;
        }   
    }
   
    @Override
    public Object clone() {
        Cliente cliente = new Cliente();
        cliente.username = this.username;
        cliente.serverDoor = this.serverDoor;
        cliente.clientDoor = this.clientDoor;
        cliente.serverDoor = this.serverDoor;
        cliente.serverSocket = this.serverSocket;
        
        return cliente;
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
            ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream());
            return in.readUTF();
        }catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    public String requestUsers() {
        
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(serverSocket.getInputStream());
            //enviar 1 para listar users (Servidor entrar na opção de listar users)
            toServer.write(1);
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
            
        }catch(Exception e) {
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