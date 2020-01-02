package cryptation;

import static cryptation.AES.decryptTextAES;
import static cryptation.AES.encryptTextAES;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class MerklePuzzle {
    
    Cipher cipher;

    public SecureRandom random = new SecureRandom();

    public MerklePuzzle() {
        try {
            cipher = Cipher.getInstance("DES");
        } catch (Exception e) { System.err.println(e);}
    }

    public String randomString(int length) {
        // Gera uma random string com o tamanho dado
        String k = new BigInteger(400, random).toString(32);
        k = k.substring(0, length);
        return k;
    }

    public SecretKey randomKey(int length) throws InvalidKeySpecException {
        
        // Adiciona zeros á string como padding devido ao tamnho ser muito pequeno
        byte[] k = (this.randomString(length) + "00000000").getBytes();
        try {
            DESKeySpec sks = new DESKeySpec(k);
            SecretKeyFactory sf = SecretKeyFactory.getInstance("DES");
            return sf.generateSecret(sks);
        } catch (Exception e) { System.err.println(e);}
        return null;
    }

    public byte[] encryptMerkle(SecretKey key, String data) throws java.security.InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] utf8 = data.getBytes("UTF8");
            byte[] ciphertext = cipher.doFinal(utf8);
            return ciphertext;
        } catch (Exception e) { System.err.println(e);}
        return null;
    }

    public String decryptMerkle(SecretKey key, byte[] ciphertext) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] utf8 = cipher.doFinal(ciphertext);
            return new String(utf8, "UTF8");
        } catch (Exception e) { System.err.println(e);}
        
        return null;
    }
    
    
    public static void main(String[] args) throws IOException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, Exception
    {   
        MerklePuzzle mkl = new MerklePuzzle();
        int totalPuzzles = 10001;
        int keyLen = 4;

         //Gera puzzles
        ArrayList<byte[]> puzzles = new ArrayList<byte[]>();
        ArrayList<String> keys = new ArrayList<String>();
        
        for (int i = 0; i < totalPuzzles; ++i) {
            String aux = mkl.randomString(16);
            keys.add(i, aux);
            byte[] ciphertext = mkl.encryptMerkle(mkl.randomKey(keyLen), "Key=" + aux + " & Puzzle=" + i);
            puzzles.add(ciphertext);
        }
        
        //Baralha os puzzles
        //Collections.shuffle(puzzles);
            
        DataInputStream in = new DataInputStream(System.in);
            
        String teststring;
        System.out.println("Enter the number of the puzzle ( random ):");
        teststring = in.readLine();
        
        
        
        
        String chave = "";
        boolean solved = false;
        while (!solved) {
            chave = mkl.decryptMerkle(mkl.randomKey(keyLen), (byte[]) puzzles.get(Integer.parseInt(teststring)));
                         
            if (chave != null && chave.substring(0, 4).equals("Key=")) {
                solved = true;
            }
         }
            
        String key = chave.substring(4, 20); //chave
        
        byte[] encodedKeyy = key.getBytes();
        SecretKey originalKeyy = new SecretKeySpec(encodedKeyy, 0, encodedKeyy.length, "AES");
        
        
        
        String mensagem;
        System.out.println("Enter the message:");
        mensagem = in.readLine();

            
        String keyChosen = keys.get(Integer.parseInt(teststring));
        //keyChosen é a chave simetrica  
        byte[] encodedKey = keyChosen.getBytes();
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        byte[] encryptedMessage = null;
        encryptedMessage = encryptTextAES(mensagem, originalKey);
                

        System.out.println("Segredo enviado!\n ------> " + encryptedMessage);
       
        
            
        String decryptedMessage = decryptTextAES(encryptedMessage, originalKey);
        System.out.println("Segredo recebido!\n ------> " + decryptedMessage );

            
    }
}

/*

                                            Merkle mkl = new Merkle();
                                            int totalPuzzles = 10000;
                                            int keyLen = 4;

                                            //Gera puzzles
                                            ArrayList<byte[]> puzzles = new ArrayList<byte[]>();
                                            ArrayList<String> keys = new ArrayList<String>();
                                            for (int i = 0; i < totalPuzzles; ++i) {
                                                String aux = mkl.random_string(16);
                                                keys.add(i, aux);
                                                byte[] ciphertext = mkl.encrypt(mkl.random_key(keyLen), "Key=" + aux + " & Puzzle=" + i);
                                                puzzles.add(ciphertext);
                                            }
                                            //Baralha os puzzles
                                            Collections.shuffle(puzzles);
                                            String chosen = cliente9.enviaPuzzles(puzzles, nome, cifra_escolhida);

                                            String keyChosen = keys.get(Integer.parseInt(chosen));
                                            //keyChosen é a chave simetrica

                                            byte[] encodedKey = keyChosen.getBytes();

                                            SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

                                            byte[] encryptedMessage = null;
                                            try {
                                                if (cifra_escolhida == 1) {
                                                    encryptedMessage = encryptText(mensagem, originalKey);
                                                } else if (cifra_escolhida == 2) {
                                                    String keyz = keyChosen + keyChosen;
                                                    byte[] doidoi = keyz.getBytes(Charset.forName("UTF-8"));

                                                    SecretKey originaKey = new SecretKeySpec(doidoi, 0, 8, "DES");

                                                    encryptedMessage = encriptaDes(mensagem, originaKey);
                                                }
                                            } catch (Exception ex) {
                                                Logger.getLogger(XIUUU.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                            cliente9.enviaCriptograma(encryptedMessage);
                                            System.out.println("Segredo enviado!\n");


*/