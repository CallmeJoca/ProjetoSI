/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.crypto.SecretKey;

/**
 *
 * @author a40284
 */
public class ClientePassivo extends Cliente implements Runnable {
    
    //Vão ser precisos mais alguns atributos
    private String BobIpAddress;
    private Socket BobSS;
    
    
    public ClientePassivo(Cliente cliente, String BobIpAddress) {
        super();
        this.BobIpAddress = BobIpAddress;
    }
    
    public boolean connectToBob() {
        try {
            BobSS = new Socket(BobIpAddress, this.getClientDoor());
            ObjectOutputStream dataOut = new ObjectOutputStream(BobSS.getOutputStream());
            dataOut.writeUTF("Olá eu sou o " + this.getUsername() + " eu vou guiar a conversa, por isso sê paciente pelo meu sinal para conversar está bem?");
            dataOut.flush();
            return true;
        }catch(IOException e) {
            System.out.println("Something went wrong, try again later™");
            return false;
        }
    }
    
    public boolean closeBobConnection() {
        try {
            BobSS.close();
            return true;
        }catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void run() {
        // 
        //escolherTrocaSegredo()
    }
    
    // Bob side of things
    public byte[] escolherTrocaSegredo(int alg) {
    
        // TODO: Fazer os 5 algoritmos
        
        switch(alg) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
        
        return null;
    }
}
