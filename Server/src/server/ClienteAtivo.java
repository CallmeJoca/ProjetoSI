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
public class ClienteAtivo extends Cliente {
    
    //Vão ser precisos mais alguns atributos
    private String AliceIpAddress;
    private Socket AliceSS;
    
    
    
    public ClienteAtivo(Cliente cliente, String AliceIpAddress) {
        super();
        this.AliceIpAddress = AliceIpAddress;
    }
    
    public boolean connectToAlice() {
        try {
            AliceSS = new Socket(AliceIpAddress, this.getClientDoor());
            ObjectOutputStream dataOut = new ObjectOutputStream(AliceSS.getOutputStream());
            dataOut.writeUTF("Olá eu sou o " + this.getUsername() + " eu vou guiar a conversa, por isso sê paciente pelo meu sinal para conversar está bem?");
            dataOut.flush();
            return true;
        }catch(IOException e) {
            System.out.println("Something went wrong, try again later™");
            return false;
        }
    }
    
    public boolean closeAliceConnection() {
        try {
            AliceSS.close();
            return true;
        }catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }
    
    // Retorna um segredo criptográfico, ou seja o resultado de cada uma das operações
    // Basta apenas chamar este método na main para receber uma chave gerada por cada método
    // Alice side of things
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
