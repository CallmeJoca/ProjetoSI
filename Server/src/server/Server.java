package server;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Server implements Runnable {

    // Strings utilzadas para chave de sessão AC
    public final String forBob = "bobnopaísdasmaravilhas";
    public final String forAlice = "alicenopaisdasmaravilhas";

    // String utilzada para distribuição de chaves
    public final String forcliente = "servidornopaisdasmaravilhas";

    private ServerSocket sSocket;
    private int portaAberta;
    private Socket clientSocket;
    private final ArrayList<String> users = new ArrayList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();

    private ObjectInputStream fromCliente;
    private ObjectOutputStream toCliente;

    public Server() {
    }

    public Server(int port) throws IOException {
        portaAberta = port;
    }

    public void startRunning() {

        System.out.println("Servidor está a correr silenciosamente... à espera de input");

        try {
            sSocket = new ServerSocket(portaAberta);
            clientSocket = sSocket.accept();

            fromCliente = new ObjectInputStream(clientSocket.getInputStream());
            toCliente = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
            return;
        }

        Thread t = new Thread(this);
        threads.add(t);
        t.start();

    }

    public int printMenu(Socket clientInput) throws IOException, ClassNotFoundException {
        ObjectInputStream inputText = new ObjectInputStream(clientInput.getInputStream());
        return ((int) inputText.readObject());
    }

    @Override
    public void run() {

        int option = -1;
        String clientArrival = "";
        String data = "";

        //Primeiro contacto, cliente base estabelece ligação com servidor
        try {

            System.out.println(clientArrival = (String) fromCliente.readObject());
            toCliente.writeObject("Welcome, " + clientArrival);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {

            // Primeiro contacto que as subclasses cliente têm com o servidor
            try {
                option = (int) fromCliente.readObject();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                return;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            switch (option) {

                case 1:
                    System.out.println("A enviar users para o cliente");

                    try {
                        toCliente.writeObject(users);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }

                    // Adquirir os users e mandar ao cliente que lhe pediu
                    break;
                case 2:
                    System.out.println("Registar um Cliente na sala de espera");

                    try {
                        //Receber os dados do ClientePassivo que quer ser passivo
                        data = (String) fromCliente.readObject();
                        System.out.println(data);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }

                    //Server check
                    try {
                        if (users.add(data)) {
                            toCliente.writeObject(true);
                        } else {
                            toCliente.writeObject(false);
                        }
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    }

                    break;

                case 3:
                    // Isto é o agente de confiaça
                    // O servidor gera chaves aleatórios para enviar aos 2 clientes
                    System.out.println("Distribuir chaves pelos clientes");
                    try {
                        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                        SecureRandom random = new SecureRandom();
                        keyGen.init(256); // for example
                        SecretKey secretKey = keyGen.generateKey();

                        // Recebe o socket do Bob pela Alice
                        Socket BobSocket = (Socket) fromCliente.readObject();
                        ObjectOutputStream toBob = new ObjectOutputStream(BobSocket.getOutputStream());

                        // vai para a Alice e o Bob
                        toCliente.writeObject(secretKey);
                        toBob.writeObject(secretKey);

                        toBob.close();

                    } catch (NoSuchAlgorithmException e) {
                        System.out.println(e);
                    } catch (IOException | ClassNotFoundException e) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
                        return;
                    }

                    // ClienteAtivo tem de enviar os 2 ips e fazer um socket temporário para enviar ao Bob
                    // Fazer chaves
                    break;

                case 4:
                    // apagar dados do cliente do ArrayList users
                    System.out.println("A retirar um cliente da sala de espera");

                    try {
                        data = (String) fromCliente.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }

                    //Server check
                    try {
                        if (users.remove(data)) {
                            toCliente.writeObject(true);
                        } else {
                            toCliente.writeObject(false);
                        }
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    }

                    break;

                case 5: // como o agente de confiança e a entidade que iria distribuir chaves de de cifra são o server

                case 6:
                    // Cliente a pedir para disconectar
                    try {
                        fromCliente.close();
                        toCliente.close();
                    } catch (IOException e) {
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
