package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;


public class ClienteAtivo extends Cliente {
    
    //Vão ser precisos mais alguns atributos
    private String BobIpAddress;
    private Socket BobSS;
    
    private ObjectOutputStream toBob;
    private ObjectInputStream fromBob;
    
    public ClienteAtivo() {}
    
    public ClienteAtivo(Cliente cliente, String BobIpAddress) {
        
        super(cliente.getUsername(), cliente.getServerIP(), cliente.getServerDoor(), cliente.getClientDoor());
        super.setFromServer(cliente.getFromServer());
        super.setToServer(cliente.getToServer());
        super.setServerSocket((cliente.getServerSocket()));
        this.BobIpAddress = BobIpAddress;
        
    }
    
    public boolean connectToBob() {
        try {
            BobSS = new Socket(BobIpAddress, this.getClientDoor());
            toBob = new ObjectOutputStream(BobSS.getOutputStream());
            fromBob = new ObjectInputStream(BobSS.getInputStream());
            //enviar ao bob o meu ip
            InetAddress inetAddress = InetAddress.getLocalHost();
            toBob.writeObject(inetAddress.getHostAddress());
            
            //Enviar mensagem à alice, receber mensagem da alice
            toBob.writeObject("Olá eu sou o/a " + this.getUsername() + " eu vou guiar a conversa, mas temos de sussurrar ok?");
            System.out.println((String)fromBob.readObject());
             
            return true;
        }catch(IOException e) {
            System.out.println("Something went wrong, try again later™");
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteAtivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
            //enviar ao bob o X
            toBob.writeObject(X);
            //receber o Y do Bob;
            BigInteger Y = (BigInteger)fromBob.readObject();
             
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
            
            //enviar todos os puzzles ao Bob
            toBob.writeObject(puzzles);
            
            //receber um puzzle do Bob
            String puzzle = (String) fromBob.readObject();
            
            return puzzle;
            
        }catch(IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteAtivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public byte[] sendPublicKeyGetSecretKey(PublicKey pk, PrivateKey sk) throws NoSuchAlgorithmException, Exception {
        // Alice envia ao bob a sua chave publica, bob gera uma chave simétrica, e cifra-a com a chave p
        try {
            
            // Enviar a chave pública ao Bob
            toBob.writeObject(pk);
            
            // Receber a chave cifrada do Bob
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            for(int s; (s=fromBob.read(buffer)) != -1; ) {
                buf.write(buffer, 0, s);
            }
            byte[] segredo = buf.toByteArray();
            
            byte[] chaveBob = AutoRSA.rsaDecrypt(segredo, sk);
            
            return chaveBob;
        }
        catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public SecretKey askServerForKeys() throws ClassNotFoundException {
        try {
            // Enviar opcao para o servidor nos dar chaves
            toServer.write(3);
            // Enviar o socket do Bob
            toServer.writeObject(BobSS);
            // Receber chave do servidor
            SecretKey sk = (SecretKey)fromServer.readObject();
            
            return sk;            
        }catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }
    
    
    public byte[] sendSecret_getSecretBYTE(byte[] criptograma) {
        try {
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