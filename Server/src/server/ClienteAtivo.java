package server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
    
    public ClienteAtivo() {}
    
    public ClienteAtivo(Cliente cliente, String BobIpAddress) {
        super(cliente.getUsername(), cliente.getServerIP(), cliente.getServerDoor(), cliente.getClientDoor());
        super.setServerSocket((cliente.getServerSocket()));
        this.BobIpAddress = BobIpAddress;
    }
    
    public boolean connectToBob() {
        try {
            BobSS = new Socket(BobIpAddress, this.getClientDoor());
            ObjectOutputStream toBob = new ObjectOutputStream(BobSS.getOutputStream());
            ObjectInputStream fromBob = new ObjectInputStream(BobSS.getInputStream());
            //enviar ao bob o meu ip
            InetAddress inetAddress = InetAddress.getLocalHost();
            toBob.writeUTF(inetAddress.getHostAddress());
            
            //Enviar mensagem à alice, receber mensagem da alice
            toBob.writeUTF("Olá eu sou o/a " + this.getUsername() + " eu vou guiar a conversa, mas temos de sussurrar ok?");
            System.out.println(fromBob.readUTF());
            
            toBob.close();
            fromBob.close();
            
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
    
    public byte[] sendPublicKeyGetSecretKey(PublicKey pk, PrivateKey sk) throws NoSuchAlgorithmException, Exception {
        // Alice envia ao bob a sua chave publica, bob gera uma chave simétrica, e cifra-a com a chave p
        try {
            ObjectOutputStream toBob = new ObjectOutputStream(BobSS.getOutputStream());
            ObjectInputStream fromBob = new ObjectInputStream(BobSS.getInputStream());
            
            // Enviar a chave pública ao Bob
            toBob.writeObject(pk);
            
            // Receber a chave cifrada do Bob
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            for(int s; (s=fromBob.read(buffer)) != -1; ) {
                buf.write(buffer, 0, s);
            }
            byte[] segredo = buf.toByteArray();
            
            fromBob.close();
            toBob.close();
            
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
            ObjectOutputStream toServer =new ObjectOutputStream(super.getServerSocket().getOutputStream());
            ObjectInputStream fromServer =new ObjectInputStream(super.getServerSocket().getInputStream());
            // Enviar opcao para o servidor nos dar chaves
            toServer.write(3);
            // Enviar o socket do Bob
            toServer.writeObject(BobSS);
            // Receber chave do servidor
            SecretKey sk = (SecretKey)fromServer.readObject();
            
            toServer.close();
            fromServer.close();
            return sk;            
        }catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }
    
    
    public byte[] sendSecret_getSecretBYTE(byte[] criptograma) {
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
            
            fromBob.close();
            toBob.close();
            return segredo;
            
        }catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }
}