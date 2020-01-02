package cryptation;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class DiffieHellman {
    
    private int bitlength = 1024;
    private Random r;
    
    public DiffieHellman() {
        BigInteger p;
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
    }
    
    public BigInteger calculateXY (BigInteger g, BigInteger p,BigInteger gen) {
        
        BigInteger xy = g.modPow(gen,p);
        return xy;
        
    }
    
    public BigInteger calculateSharedKey(BigInteger XY, BigInteger gen, BigInteger p) {
        BigInteger aux_key = XY.modPow(gen,p);
        return aux_key;
    }
    
    public static BigInteger getRandomBigInteger(BigInteger p) {
        Random rand = new Random();
        BigInteger upperLimit = new BigInteger(p.toString());
        BigInteger result;
        do {
            result = new BigInteger(upperLimit.bitLength(), rand); 
        }while(result.compareTo(upperLimit) >= 0);   
        
        return result;
    }
    
    public Key generateKey(byte[] sharedKey)
    {
        // AES supports 128 bit keys. So, just take first 16 bits of DH generated key.
        byte[] byteKey = new byte[16];
        for(int i = 0; i < 16; i++) {
            byteKey[i] = sharedKey[i];
        }

        //convert given key to AES format
        try {
            Key key = new SecretKeySpec(byteKey, "AES");

            return key;
        } catch(Exception e) { System.err.println(e);}

        return null;
    }
    
     
    public byte[] encryptDH (Key key, String message) {
        byte[] encrypted = null;
        try {
            
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(message.getBytes());
            //System.err.println(new String(encrypted));
        } catch (Exception e) {System.err.println(e);};
        
        return encrypted;
    }
    
    public String decryptDH (Key key, byte[] encrypted)  {
      
        String decrypted = "";
        try {
            Cipher cipher = Cipher.getInstance("AES");
        
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = new String(cipher.doFinal(encrypted));
            System.err.println(decrypted);
        } catch (Exception e) {System.err.println(e);};
        
        return decrypted;
    }
}

