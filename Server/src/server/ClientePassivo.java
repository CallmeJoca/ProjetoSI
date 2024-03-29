package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
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

    private ObjectOutputStream toAlice;
    private ObjectInputStream fromAlice;

    public ClientePassivo() {
    }

    public ClientePassivo(Cliente cliente, String AliceIpAddress) {
        super(cliente.getUsername(), cliente.getServerIP(), cliente.getServerDoor(), cliente.getClientDoor());
        super.setFromServer(cliente.getFromServer());
        super.setToServer(cliente.getToServer());
        super.setServerSocket((cliente.getServerSocket()));

        this.AliceIpAddress = AliceIpAddress;
    }

    public boolean connectToAlice() throws ClassNotFoundException {
        try {
            // Conecção principal
            AliceSS = new Socket(AliceIpAddress, this.getClientDoor());
            toAlice = new ObjectOutputStream(AliceSS.getOutputStream());
            fromAlice = new ObjectInputStream(AliceSS.getInputStream());

            System.out.println((String) fromAlice.readObject());
            toAlice.writeObject("Oii chamo-me " + this.getUsername() + " e sou muita calado/a.");

            return true;
        } catch (IOException e) {
            System.out.println("Something went wrong, try again later™");
            return false;
        }
    }

    public boolean closeAliceConnection() {
        try {
            AliceSS.close();
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean requestDelete() {
        try {

            InetAddress inetAddress = InetAddress.getLocalHost();
            toServer.write(4);
            String info = this.getUsername() + "__" + inetAddress.getHostAddress();
            toServer.writeObject(info);

            // Server check
            boolean serverCheck = fromServer.readBoolean();
            return serverCheck;

        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void run() {
        int AliceChoice = -1;
        try {
            AliceChoice = (int) fromAlice.readObject();
            escolherTrocaSegredo(AliceChoice);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientePassivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientePassivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Bob side of things
    public void escolherTrocaSegredo(int alg) throws Exception {

        switch (alg) {

            case 1://Diffie-Hellman
                DiffieHellman DH = new DiffieHellman();
                BigInteger P = DH.p;
                BigInteger g = getRandomBigInteger(P);
                BigInteger y = getRandomBigInteger(P);
                BigInteger Y = calculateXY(g, P, y);

                // Enviar X e receber Y
                BigInteger X = this.sendYgetX(Y);
                BigInteger KeyCliente = calculateSharedKey(y, P, X);

                // Chaves
                byte[] eKey = generateKey(KeyCliente.toByteArray());
                SecretKey encryptKey = new SecretKeySpec(eKey, 0, eKey.length, "AES");
                byte[] dKey = generateKey(KeyCliente.toByteArray());
                SecretKey decryptKey = new SecretKeySpec(dKey, 0, dKey.length, "AES");

                // Mensagem a enviar (-- encriptada --)
                System.out.println("O que queres sussurrar?");
                String mensagem = Read.readString();
                // Encriptar mensagem
                byte[] criptograma = encryptTextAES(mensagem, encryptKey);
                // Enviar segredo e receber segredo
                byte[] segredo = this.sendSecret_getSecretBYTE(criptograma);
                // Decrypt segredo
                String plaintext = decryptTextAES(segredo, decryptKey);
                System.out.println("Segredo recebido!\n ------> " + plaintext);

                break;

            case 2: // Puzzles de Merkle

                // Receber Puzzles da Alice
                // Resolver um puzzle e obter a chave dele
                String key = sendPuzzleGetKey();

                String keyAB = Base64.getEncoder().encodeToString(key.getBytes());
                byte[] encodedKey = Base64.getDecoder().decode(keyAB);
                SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

                // Queremos trocar criptogramas
                System.out.println("O que queres sussurrar?");
                String mensagem2 = Read.readString();
                // Cifrar mensagem
                byte[] encryptedMessage = encryptTextAES(mensagem2, originalKey);
                // Decifrar criptograma
                byte[] segredo2 = this.sendSecret_getSecretBYTE(encryptedMessage);
                String plaintext2 = decryptTextAES(segredo2, originalKey);
                System.out.println("Segredo recebido!\n ------> " + plaintext2);

                break;

            case 3: // RSA

                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                SecureRandom random = new SecureRandom();
                keyGen.init(256); // for example
                SecretKey secretKey = keyGen.generateKey();

                //Receber a chave pública da Alice e cifrar a sua chave publica com a chave pública da Alice
                this.sendSecretKeyUsePublicKey(secretKey);

                // Queremos trocar criptogramas
                System.out.println("O que queres sussurrar?");
                String mensagem3 = Read.readString();
                // cifrar a mensagem
                byte[] segredo3 = encryptTextAES(mensagem3, secretKey);

                //Enviar o segredo e receber o segredo
                byte[] criptograma2 = this.sendSecret_getSecretBYTE(segredo3);
                String plaintext3 = decryptTextAES(criptograma2, secretKey);
                System.out.println("Segredo recebido!\n ------> " + plaintext3);

                break;
            case 4: //Receber uma chave do servidor DISTRCHAVS
                try {
                    SecretKey serversk = (SecretKey) fromServer.readObject();

                    System.out.println("O que queres sussurrar?");
                    String mensagem4 = Read.readString();
                    byte[] segredo4 = encryptTextAES(mensagem4, serversk);

                    //Enviar o segredo e receber o segredo
                    byte[] criptograma3 = this.sendSecret_getSecretBYTE(segredo4);
                    String plaintext4 = decryptTextAES(criptograma3, serversk);
                    System.out.println("Segredo recebido!\n ------> " + plaintext4);

                } catch (Exception e) {
                    System.out.println(e);
                }

                break;

            case 5://Receber uma chave do servidor AC
                try {
                    SecretKey acKey = (SecretKey) fromServer.readObject();

                    System.out.println("O que queres sussurrar?");
                    String mensagem5 = Read.readString();
                    byte[] segredo4 = encryptTextAES(mensagem5, acKey);

                    //Enviar o segredo e receber o segredo
                    byte[] criptograma4 = this.sendSecret_getSecretBYTE(segredo4);
                    String plaintext5 = decryptTextAES(criptograma4, acKey);
                    System.out.println("Segredo recebido!\n ------> " + plaintext5);

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e);
                }
                break;
        }
    }

    public BigInteger sendYgetX(BigInteger Y) {
        try {
            //receber o X da Alice;
            BigInteger X = (BigInteger) fromAlice.readObject();

            //enviar à alice o Y
            toAlice.writeObject(Y);

            return X;

        } catch (IOException e) {
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
            MerklePuzzle mkl = new MerklePuzzle();

            // Obter todos os puzzles
            ArrayList<String> puzzles = (ArrayList<String>) fromAlice.readObject();

            // O Bob escolhe um puzzle ao acaso e resolve-o.
            Random randomGenerator = new Random();
            int randomPuzzle = randomGenerator.nextInt(totalPuzzles);
            String key_guessing = "";

            boolean solved = false;
            while (!solved) {
                key_guessing = mkl.decryptMerkle(mkl.getRandomKey(key_Length), puzzles.get(randomPuzzle));

                if (key_guessing != null && key_guessing.substring(0, 4).equals("Key=")) {
                    solved = true;
                }
            }

            // Bob obtem chave
            String keyB = key_guessing.substring(4, 20); //chave

            // Bob envia puzzle à Alice
            toAlice.writeObject(randomPuzzle);

            return keyB;

        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ClientePassivo.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    public void sendSecretKeyUsePublicKey(SecretKey simKey) throws ClassNotFoundException, Exception {
        try {
            // Receber a chave pública da Alice
            PublicKey AlicePK = (PublicKey) fromAlice.readObject();

            //cifrar a chave criada pelo bob e envia-la à alice
            byte[] data = simKey.getEncoded();
            byte[] cipheredKey = AutoRSA.rsaEncrypt(data, AlicePK);
            toAlice.write(cipheredKey);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public byte[] sendSecret_getSecretBYTE(byte[] criptograma) {
        try {
            // Receber segredo da Alice
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            for (int s; (s = fromAlice.read(buffer)) != -1;) {
                buf.write(buffer, 0, s);
            }
            byte[] segredo = buf.toByteArray();

            //Enviar criptograma do Bob à Alice
            toAlice.write(criptograma);

            return segredo;

        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
