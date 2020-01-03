package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class DiffieHellman {
    
    private int bitlength = 1024;
    private Random r;
    private BigInteger p; 
    
    public DiffieHellman() {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
    }
    
    public static BigInteger calculateXY(BigInteger g, BigInteger P,BigInteger xy) {
        
        BigInteger X_Y = g.modPow(xy,P);
        return X_Y;
        
    }
    
    public static BigInteger calculateSharedKey(BigInteger xy, BigInteger p, BigInteger XY) {
        BigInteger aux_key = XY.modPow(xy,p);
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
    
    public static byte[] generateKey(byte[] sharedKey)
    {    // AES supports 128 bit keys. So, just take first 16 bits of DH generated key.
        byte[] Key = new byte[16];
        for(int i = 0; i < 16; i++) {
            Key[i] = sharedKey[i];
        }
        return Key;
    }
    
    
    /*
    public static void main(String[] args) throws IOException, Exception {
        
        DiffieHellman DH = new DiffieHellman();
        System.out.println(DH.p);
        
        BigInteger P = DH.p;
        BigInteger g = getRandomBigInteger(P);
        
        // Alice
        BigInteger x = getRandomBigInteger(P);
        //Bob
        BigInteger y = getRandomBigInteger(P);
        // Alice
        BigInteger X = calculateXY(g,P,x);
        //Bob
        BigInteger Y = calculateXY(g,P,y);
        
        BigInteger KeyBob = calculateSharedKey(y,P,X);
        System.out.println("Key Bob " + KeyBob);
        BigInteger KeyAlice = calculateSharedKey(x,P,Y);
        System.out.println("Key Alice " + KeyAlice);
        
        byte[] eKey = generateKey(KeyAlice.toByteArray());
        SecretKey encryptKey = new SecretKeySpec(eKey, 0, eKey.length, "AES");
        
        byte[] dKey =  generateKey(KeyAlice.toByteArray());
        SecretKey decryptKey = new SecretKeySpec(dKey, 0, dKey.length, "AES");
        
        DataInputStream in = new DataInputStream(System.in);
        String mensagem;
        System.out.println("Enter the message:");
        mensagem = in.readLine();
        
        
        byte[] criptograma = AES.encryptTextAES(mensagem,decryptKey);
        String texto_limpo = AES.decryptTextAES(criptograma,encryptKey);
        System.out.println("Criptograma: " + criptograma + "\nTexto-limpo: " + texto_limpo);
    }
*/
}

