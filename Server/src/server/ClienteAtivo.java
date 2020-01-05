/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

/**
 *
 * @author a40284
 */
public class ClienteAtivo extends Cliente {
    
    //Vão ser precisos mais alguns atributos
    private String BobIpAddress;
    private Socket BobSS;
    
    
    
    public ClienteAtivo() {}
    
    public ClienteAtivo(Cliente cliente, String BobIpAddress) {
        super(cliente.getUsername(), cliente.getServerIP(), cliente.getServerDoor(), cliente.getClientDoor());
        this.BobIpAddress = BobIpAddress;
    }
    
    public boolean connectToBob() {
        try {
            BobSS = new Socket(BobIpAddress, this.getClientDoor());
            ObjectOutputStream dataOut = new ObjectOutputStream(BobSS.getOutputStream());
            dataOut.writeUTF("Olá eu sou o " + this.getUsername() + " eu vou guiar a conversa, mas temos de sussurrar ok?");
            dataOut.flush();
            
            dataOut.close();
            
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
 
    public BigInteger sendXgetY(BigInteger X) {
        try {
            ObjectOutputStream toBob = new ObjectOutputStream(BobSS.getOutputStream());
            ObjectInputStream fromBob = new ObjectInputStream(BobSS.getInputStream());
            //enviar ao bob o X
            toBob.writeObject(X);
            //receber o Y do Bob;
            BigInteger Y = (BigInteger)fromBob.readObject();
            
            toBob.close();
            fromBob.close();
            
            return Y;
            
        }catch(IOException e) {
            System.out.println(e);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteAtivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String sendPuzzleGetPuzzle(ArrayList<String> puzzles) {
        try {
            ObjectOutputStream toBob = new ObjectOutputStream(BobSS.getOutputStream());
            ObjectInputStream fromBob = new ObjectInputStream(BobSS.getInputStream());
            
            //enviar todos os puzzles ao Bob
            toBob.writeObject(puzzles);
            
            //receber um puzzle do Bob
            String puzzle = fromBob.readUTF();
            
            toBob.close();
            fromBob.close();
            return puzzle;
                    
        }catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }
    
    
    public byte[] sendSecret_getSecret(byte[] criptograma) {
        try {
            ObjectOutputStream toBob = new ObjectOutputStream(BobSS.getOutputStream());
            ObjectInputStream fromBob = new ObjectInputStream(BobSS.getInputStream());
            //Enviar criptograma da alice ao bob
            toBob.write(criptograma);
            

            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            for(int s; (s=fromBob.read(buffer)) != -1; ) {
                buf.write(buffer, 0, s);
            }
            byte[] segredo = buf.toByteArray();
            
            return segredo;
            
        }catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }
    
    
}
