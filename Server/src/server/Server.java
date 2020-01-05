package server;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{
    
    private ServerSocket sSocket;
    private int portaAberta;
    private Socket clientSocket;
    private Thread t;
    private ArrayList<String> users = new ArrayList<>();
    
    private ObjectInputStream fromCliente;
    private ObjectOutputStream toCliente;
    
    public Server() {}
    
    public Server(int port) throws IOException{
        portaAberta = port;
    }
    
    public void startRunning() {
        
        while(true) {
            System.out.println("Servidor está a correr silenciosamente... à espera de input");
            try {
                sSocket = new ServerSocket(portaAberta);
                clientSocket = sSocket.accept();
                
                fromCliente = new ObjectInputStream(clientSocket.getInputStream());
                toCliente = new ObjectOutputStream(clientSocket.getOutputStream());
                
            } catch (IOException e) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
            }
            
            t = new Thread(this);
            t.start();
            
        }
    }
    
    
    public int printMenu(Socket clientInput) throws IOException, ClassNotFoundException{
        ObjectInputStream inputText = new ObjectInputStream(clientInput.getInputStream());
        return ((int) inputText.readObject());
    }

    @Override
    public void run() {
        
        int option = -1;
        String clientArrival;
        String data = "";
        
        //Primeiro contacto, cliente base estabelece ligação com servidor
        try {
            clientArrival = fromCliente.readUTF();
            toCliente.writeUTF("Welcome, " + clientArrival);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(true) {
            
            // Primeiro contacto que as subclasses cliente têm com o servidor
            try { option = fromCliente.readInt(); } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            switch (option) {
                
                case 1:
                    System.out.println("A enviar users para o cliente");
                    
                    try {
                        toCliente.writeObject(users);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    // Adquirir os users e mandar ao cliente que lhe pediu
                    break;
                case 2:
                    System.out.println("Registar um Cliente na sala de espera");

                    try {
                        //Receber os dados do ClientePassivo que quer ser passivo
                        data = (String)fromCliente.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Server check
                    try {
                    if(users.add(data)) {
                        toCliente.writeBoolean(true);
                    } else {
                        toCliente.writeBoolean(false);
                        }
                    }catch(IOException e) {
                        System.out.println(e);
                    }
                    
                    break;
                    
                case 3:
                    System.out.println("Distribuir chaves pelos clientes");
                    // ClienteAtivo tem de enviar os 2 ips e fazer um socket temporário para enviar ao Bob
                    // Fazer chaves
                    break;
                    
                case 4:
                    // apagar dados do cliente do ArrayList users
                    System.out.println("A retirar um cliente da sala de espera");
                    
                    try {
                        data = (String)fromCliente.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Server check
                    try {
                    if(users.remove(data)) {
                        toCliente.writeBoolean(true);
                    } else {
                        toCliente.writeBoolean(false);
                        }
                    }catch(IOException e) {
                        System.out.println(e);
                    }
                    
                case 5:
                    // Cliente a pedir para disconectar
                    try {
                        fromCliente.close();
                        toCliente.close();
                    }catch(IOException e) {
                        System.out.println(e);
                    }
                    
                    // sair da thread
                    return;
                    
                default:
                    break;
            }
        }
    }
}
