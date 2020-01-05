package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import static server.AES.encryptTextAES;
import static server.AES.decryptTextAES;
import static server.DiffieHellman.calculateSharedKey;
import static server.DiffieHellman.calculateXY;
import static server.DiffieHellman.generateKey;
import static server.DiffieHellman.getRandomBigInteger;


public class ClientePassivo extends Cliente implements Runnable {
    
    //Vão ser precisos mais alguns atributos
    private String AliceIpAddress;
    private Socket AliceSS;
    
    
    public ClientePassivo() {}
    
    public ClientePassivo(Cliente cliente, String AliceIpAddress) {
        super(cliente.getUsername(), cliente.getServerIP(), cliente.getServerDoor(), cliente.getClientDoor());
        this.AliceIpAddress = AliceIpAddress;
    }
    
    public boolean connectToAlice() {
        try {
            AliceSS = new Socket(AliceIpAddress, this.getClientDoor());
            ObjectOutputStream toAlice = new ObjectOutputStream(AliceSS.getOutputStream());
            ObjectInputStream fromAlice = new ObjectInputStream(AliceSS.getInputStream());
            System.out.println(fromAlice.readUTF());
            toAlice.writeUTF("Oii chamo-me " + this.getUsername() + " e sou muita calada.");
            
            toAlice.close();
            fromAlice.close();
            
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
    
    public boolean requestDelete() {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(this.getServerSocket().getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(this.getServerSocket().getInputStream());
            InetAddress inetAddress = InetAddress.getLocalHost();
            toServer.write(4);
            String info = this.getUsername() + "__" + inetAddress.getHostAddress();
            toServer.writeUTF(info);
            
            // Server check
            System.out.println(fromServer.readUTF());
            
            toServer.close();
            fromServer.close();            
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
    public byte[] escolherTrocaSegredo(int alg) throws Exception {
        
        switch(alg) {
            
            case 1://Diffie-Hellman
                DiffieHellman DH = new DiffieHellman();
                BigInteger P = DH.p;
                BigInteger g = getRandomBigInteger(P);
                BigInteger y = getRandomBigInteger(P);
                BigInteger Y = calculateXY(g,P,y);
                
                // Enviar X e receber Y
                BigInteger X = this.sendYgetX(Y);
                BigInteger KeyCliente = calculateSharedKey(y,P,X);

                
                // Chaves 
                byte[] eKey = generateKey(KeyCliente.toByteArray());
                SecretKey encryptKey = new SecretKeySpec(eKey, 0, eKey.length, "AES");
                byte[] dKey =  generateKey(KeyCliente.toByteArray());
                SecretKey decryptKey = new SecretKeySpec(dKey, 0, dKey.length, "AES");
                
                // Mensagem a enviar (-- encriptada --)
                System.out.println("O que queres sussurrar?");
                String mensagem = Read.readString();
                byte[] segredo = encryptTextAES(mensagem, encryptKey);
                
                // Mandar o segredo
                // Receber um segredo
                byte[] criptograma_recebido = this.sendSecret_getSecret(segredo);
                
                // Decrypt segredo
                String plaintext = decryptTextAES(criptograma_recebido, decryptKey);
                System.out.println(plaintext);
                
                break;
            case 2: // Puzzles de Merkle
                
                // Receber Puzzles da Alice
                
                // Receber chave
                // String key = sendPuzzleGetKey();
                
                // Chave 
                // String keyAB = Base64.getEncoder().encodeToString(key.getBytes());
                // byte[] encodedKey = Base64.getDecoder().decode(keyAB);
                // SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
                
                // Receber segredo
                // ---------------------
                
                // Decrypt segredo
                // String decryptedMessage = decryptTextAES(encryptedMessage, originalKey);
                // System.out.println("Segredo recebido!\n ------> " + decryptedMessage );
                
                // Enviar segredo ???
                // --------------
                
                break;
            case 3: // RSA
                
                // Receber N, e, phi
                // BigInteger N = 
                // BigInteger e = 
                // BigInteger phi =
                
                // Calcular d
                // BigInteger d = e.modInverse(phi);
                
                // Receber segredo
                // byte[] segredo = ????
                
                // Decrypt segredo
                // byte[] decrypted = rsa.decryptRSA(segredo, N, d);
                
                // Enviar segredo ???
                // --------------------
                
                break;
            case 4:
                
                break;
            case 5:
                
                break;
        }
        
        return null;
    }
    
    public BigInteger sendYgetX(BigInteger Y) {
        try {
            ObjectOutputStream toAlice = new ObjectOutputStream(AliceSS.getOutputStream());
            ObjectInputStream fromAlice = new ObjectInputStream(AliceSS.getInputStream());
            
            //receber o X da Alice;
            BigInteger X = (BigInteger)fromAlice.readObject();
            
            //enviar à alice o Y
            toAlice.writeObject(Y);

            toAlice.close();
            fromAlice.close();
            return X;
            
        }catch(IOException e) {
            System.out.println(e);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteAtivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String sendPuzzleGetKey() throws InvalidKeySpecException {
        int totalPuzzles = 2000;
        int key_Length = 4;
        try {
            ObjectOutputStream toAlice = new ObjectOutputStream(AliceSS.getOutputStream());
            ObjectInputStream fromAlice = new ObjectInputStream(AliceSS.getInputStream());
            MerklePuzzle mkl = new MerklePuzzle();
            
            // Obter todos os puzzles
            ArrayList<String> puzzles = (ArrayList<String>)fromAlice.readObject();           
           
            // O Bob escolhe um puzzle ao acaso e resolve-o.
            Random randomGenerator = new Random();
            int randomPuzzle = randomGenerator.nextInt(totalPuzzles);
            String key_guessing = "";
            boolean solved = false;
            while (!solved) {
                key_guessing = mkl.decryptMerkle(mkl.getRandomKey(key_Length), puzzles.get(randomPuzzle).toString());

                if (key_guessing != null && key_guessing.substring(0, 4).equals("Key=")) {
                    solved = true;
                }
            }
            
            // Bob obtem chave
            String keyB = key_guessing.substring(4, 20); //chave
            
            // Bob envia puzzle à Alice
            toAlice.writeObject(randomPuzzle);
            
            return keyB;
            
        }catch(IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ClientePassivo.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return null;
    }
     
    
    public byte[] sendSecret_getSecret(byte[] criptograma) {
        try {
            ObjectOutputStream toAlice = new ObjectOutputStream(AliceSS.getOutputStream());
            ObjectInputStream fromAlice = new ObjectInputStream(AliceSS.getInputStream());
            
            // Receber segredo da Alice
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            for(int s; (s=fromAlice.read(buffer)) != -1; ) {
                buf.write(buffer, 0, s);
            }
            byte[] segredo = buf.toByteArray();
            
            //Enviar criptograma do Bob à Alice
            toAlice.write(criptograma);

            toAlice.close();
            fromAlice.close();
            return segredo;
            
        }catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }

}
