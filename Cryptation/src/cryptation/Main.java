package cryptation;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("XIUUU: Troca de Segredos Criptográficos Seguro.");
        System.out.println("\n1 - Modo Cliente\n2 - Modo Servidor\n3 - Sair\n");
        int n = Read.readInt();
        
        if (n==1) {
            Client.main(args);
        }
        
        // MUDAR PARA SERVER
        if (n==2) {
            Client2.main(args);
        }
        
        if (n==3) { 
            System.exit(0);
        }
    }
}
