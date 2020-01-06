package server;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

    BigInteger p;
    BigInteger q;
    BigInteger N;
    BigInteger phi;
    BigInteger e;
    BigInteger d;
    private int bitlength = 1024;
    private Random r;

    public RSA() {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);

        N = p.multiply(q);

        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitlength / 2, r);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
    }

    // Encrypt message
    public byte[] encryptRSA(byte[] message, BigInteger N, BigInteger e) {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message
    public byte[] decryptRSA(byte[] message, BigInteger N, BigInteger d) {
        return (new BigInteger(message)).modPow(d, N).toByteArray();

    }
}
