package server;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author a39851
 */ 
public class RSA
{
    BigInteger p;
    BigInteger q;
    BigInteger N;
    BigInteger phi;
    BigInteger e;
    BigInteger d;
    private int bitlength = 1024;
    private Random r;
 
    public RSA()
    {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        
        N = p.multiply(q);
        
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        e = BigInteger.probablePrime(bitlength / 2, r);
        
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        
        d = e.modInverse(phi);
    }
     
    // Encrypt message
    public byte[] encryptRSA(byte[] message, BigInteger N, BigInteger e)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }
 
    // Decrypt message
    public byte[] decryptRSA(byte[] message, BigInteger N, BigInteger d)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
    
    /*
    public static void main(String[] args) throws IOException
    {
        RSA rsa = new RSA();
        DataInputStream in = new DataInputStream(System.in);
        String teststring;
        System.out.println("Enter the plain text:");
        teststring = in.readLine();
        System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: " + bytesToString(teststring.getBytes()));
        
        
        // encrypt
        byte[] encrypted = rsa.encryptRSA(teststring.getBytes());
        
        // decrypt
        byte[] decrypted = rsa.decryptRSA(encrypted);
        
        System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));
    }
 
    private static String bytesToString(byte[] encrypted)
    {
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
    }
*/

}

//https://www.sanfoundry.com/java-program-implement-rsa-algorithm/