package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Cliente {

    static void main(String[] args) {
        //TODO: Code main in client for when called from Main.java
        throw new UnsupportedOperationException("Not supported yet."); 
    }

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
    
    public boolean closeServerConnection() {
        try {
            serverSocket.close();
            return true;
        }catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }
    
    // Eventualmente tentar trocar de atributos?
}