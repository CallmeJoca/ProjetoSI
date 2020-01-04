package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    //  Método que permite esta instancia de cliente comunicar com o servidor
    public boolean establishServerConnection() {
        try {
            serverSocket = new Socket(serverIP, serverDoor);
            DataOutputStream dataOut = new DataOutputStream(serverSocket.getOutputStream());
            dataOut.writeUTF(username + "conectou-se ao servidor.");
            dataOut.flush();
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
            //enviar 1 para listar users
            toServer.write(1);
            ArrayList[] users = (ArrayList[])fromServer.readObject();
            
            
        }catch(Exception e) {
            System.out.println(e);
        }
        return null;
        // Eventualmente tentar trocar de atributos?
    }
    
    public void printhelp(ArrayList[] list) {
        
        
    }
}