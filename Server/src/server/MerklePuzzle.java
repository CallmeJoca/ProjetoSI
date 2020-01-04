package server;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class MerklePuzzle {
    
    Cipher cipher;

    public SecureRandom random = new SecureRandom();

    public MerklePuzzle() {
        try {
            cipher = Cipher.getInstance("DES");
        } catch (Exception e) { System.err.println(e);}
    }

    public String getRandomString(int length) {
        // Gera uma random string com o tamanho dado
        String k = new BigInteger(400, random).toString(32);
        k = k.substring(0, length);
        return k;
    }

    public SecretKey getRandomKey(int length) throws InvalidKeySpecException {
        
        // Adiciona zeros á string como padding devido ao tamnho ser muito pequeno
        byte[] k = (this.getRandomString(length) + "00000000").getBytes();
        try {
            DESKeySpec sks = new DESKeySpec(k);
            SecretKeyFactory sf = SecretKeyFactory.getInstance("DES");
            return sf.generateSecret(sks);
        } catch (Exception e) { System.err.println(e);}
        return null;
    }

    public String encryptMerkle(SecretKey key, String mensagem) throws java.security.InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] utf8 = mensagem.getBytes("UTF8");
            byte[] ciphertext = cipher.doFinal(utf8);
            ciphertext = BASE64EncoderStream.encode(ciphertext);
            return new String(ciphertext);
        } catch (Exception e) { System.err.println(e);}
        return null;
    }

    public String decryptMerkle(SecretKey key, String mensagem) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            // decode with base64 to get bytes
            byte[] dec = BASE64DecoderStream.decode(mensagem.getBytes());
            byte[] utf8 = cipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (Exception e) { }
        
        return null;
    }
    
    /*
    public static void main(String[] args) throws IOException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, Exception
    {   
        MerklePuzzle mkl = new MerklePuzzle();
        int totalPuzzles = 2000;
        int key_Length = 4;

        ArrayList<String> puzzles = new ArrayList<>();
        ArrayList<String> puzzlesA = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<String>();
        
        
        //Gera puzzles ----- ALICE
        for (int i = 0; i < totalPuzzles; ++i) {
            String puzzleKeys = mkl.getRandomString(16);
            keys.add(i, puzzleKeys);
            puzzlesA.add("Key=" + puzzleKeys + " & Puzzle=" + i);
            String ciphertext = mkl.encryptMerkle(mkl.getRandomKey(key_Length), "Key=" + puzzleKeys + " & Puzzle=" + i);
            System.out.println("Puzzle " + i + " chave = " + puzzleKeys);
            puzzles.add(ciphertext);
        }
        
        // BOB ------------------------------
        DataInputStream in = new DataInputStream(System.in);
        String teststring;
        System.out.println("Enter the number of the puzzle ( random ):");
        teststring = in.readLine();
        
        String key_guessing = "";
        boolean solved = false;
        while (!solved) {
            
            key_guessing = mkl.decryptMerkle(mkl.getRandomKey(key_Length), puzzles.get(Integer.parseInt(teststring)).toString());
                         
            if (key_guessing != null && key_guessing.substring(0, 4).equals("Key=")) {
                solved = true;
                System.out.println(key_guessing);
                System.out.println("jata");
            }
        }
        
        // BOB obtem chave
        String keyB = key_guessing.substring(4, 20); //chave
        System.out.println("Key Bob: " + keyB);
        System.out.println("Puzzle: " + key_guessing.substring(30));
        System.out.println("Puzzle: " + teststring);
        
        // BOB envia puzzle --- teststring
        
        
        // ALice obtem puzzle
        String puzzle_chosen = puzzlesA.get(Integer.parseInt(teststring));
        String keyA = puzzle_chosen.substring(4, 20); //chave
        System.out.println("Key Alice: " + keyA);
        
         
        String keyAb = Base64.getEncoder().encodeToString(keyA.getBytes());
        byte[] encodedKey = Base64.getDecoder().decode(keyAb);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        
        String mensagem;
        System.out.println("Enter the message:");
        mensagem = in.readLine();


        byte[] encryptedMessage = encryptTextAES(mensagem, originalKey);
        System.out.println("Segredo enviado!\n ------> " + encryptedMessage);
            
        String decryptedMessage = decryptTextAES(encryptedMessage, originalKey);
        System.out.println("Segredo recebido!\n ------> " + decryptedMessage );
      
    }
*/
}