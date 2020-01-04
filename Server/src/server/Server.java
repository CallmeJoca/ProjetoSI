package server;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{
    
    ServerSocket sSocket;
    ArrayList[] clients;
    Socket clientInput;

    /**
     *
     * @param clientInput
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static int printMenu(Socket clientInput) throws IOException, ClassNotFoundException{
        ObjectInputStream inputText = new ObjectInputStream(clientInput.getInputStream());
        
        System.out.println("Menu:");
        System.out.println("1. Escolher porta a abrir");
        System.out.println("2. Fazer print dos clientes");
        System.out.println("3. Desligar");
        
        return ((int) inputText.readObject());
    }

    @Override
    public void run() {
        
        int option;
        try {
            Socket clientInput = sSocket.accept();
            while(true) {
                option = printMenu(clientInput);
                switch (option) {
                    case 1:
                        
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        
                        break;
                    default:
                        break;
                }
            }
        }catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
