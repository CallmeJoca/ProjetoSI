package server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;

/**
 *
 * @author a40284
 */
public class AutoRSA {

    public final PrivateKey privK;
    public final PublicKey pubK;

    public AutoRSA() throws NoSuchAlgorithmException {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        privK = pair.getPrivate();
        pubK = pair.getPublic();
    }

    public static KeyPair generateKeyPair() throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }

    public static byte[] rsaEncrypt(byte[] secretkey, PublicKey publicKey) throws Exception {

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] segredo = encryptCipher.doFinal(secretkey);

        return segredo;
    }

    public static byte[] rsaDecrypt(byte[] chaveCifrada, PrivateKey privateKey) throws Exception {

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] chavedecifrada = decriptCipher.doFinal(chaveCifrada);

        return chavedecifrada;
    }

}
