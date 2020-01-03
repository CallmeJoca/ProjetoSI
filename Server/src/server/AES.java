package server;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;


public class AES {
    
    public static byte[] encryptTextAES(String textolimpo, SecretKey secretK) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretK);
        byte[] CipherText = cipher.doFinal(textolimpo.getBytes());
        return CipherText;
    }

    public static String decryptTextAES(byte[] byteCipherText, SecretKey secretK) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretK);
        byte[] PlainText = cipher.doFinal(byteCipherText);
        return new String(PlainText);
    }    
    
}
