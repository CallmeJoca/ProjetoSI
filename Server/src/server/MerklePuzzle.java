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

/**
 *
 * @author a39851
 */
public class MerklePuzzle {

    Cipher cipher;

    public SecureRandom random = new SecureRandom();

    public MerklePuzzle() {
        try {
            cipher = Cipher.getInstance("DES");
        } catch (Exception e) {
            System.err.println(e);
        }
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
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public String encryptMerkle(SecretKey key, String mensagem) throws java.security.InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] utf8 = mensagem.getBytes("UTF8");
            byte[] ciphertext = cipher.doFinal(utf8);
            ciphertext = BASE64EncoderStream.encode(ciphertext);
            return new String(ciphertext);
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public String decryptMerkle(SecretKey key, String mensagem) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            // decode with base64 to get bytes
            byte[] dec = BASE64DecoderStream.decode(mensagem.getBytes());
            byte[] utf8 = cipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (Exception e) {
        }

        return null;
    }
}
