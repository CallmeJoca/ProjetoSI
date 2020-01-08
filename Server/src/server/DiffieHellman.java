package server;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author a39851
 */
public class DiffieHellman {

    private int bitlength = 1024;
    private Random r;
    BigInteger p;

    public DiffieHellman() {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
    }

    public static BigInteger calculateXY(BigInteger g, BigInteger P, BigInteger xy) {

        BigInteger X_Y = g.modPow(xy, P);
        return X_Y;

    }

    public static BigInteger calculateSharedKey(BigInteger xy, BigInteger p, BigInteger XY) {
        BigInteger aux_key = XY.modPow(xy, p);
        return aux_key;
    }

    public static BigInteger getRandomBigInteger(BigInteger p) {
        Random rand = new Random();
        BigInteger upperLimit = new BigInteger(p.toString());
        BigInteger result;
        do {
            result = new BigInteger(upperLimit.bitLength(), rand);
        } while (result.compareTo(upperLimit) >= 0);

        return result;
    }

    public static byte[] generateKey(byte[] sharedKey) {    // AES supports 128 bit keys. So, just take first 16 bits of DH generated key.
        byte[] Key = new byte[16];
        for (int i = 0; i < 16; i++) {
            Key[i] = sharedKey[i];
        }
        return Key;
    }
}
