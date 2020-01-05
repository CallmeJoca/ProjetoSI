package server;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.util.Random;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PBKDF2 {
    
    private static final Random RANDOM = new SecureRandom();
    
    public PBKDF2() { }
    
    // Geração de 'salt'
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }
    
    // Calcula e devolve PBKDF2 da password em parâmetro.
    public static String getPBKDF2(String password) throws Exception {
        
        char[] chars = password.toCharArray();
        byte[] salt = PBKDF2.getNextSalt();
        int iterations = 10000;
        
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 256); // AES-256
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = skf.generateSecret(spec).getEncoded();
        
        return new String(key);
    }
    
    // O segundo parâmetro (PBKDF2_from_user_pw) deve ser obtido através do retorno 
    // da função getPBKDF2 com parâmetro 'PBKDF2_from_user_pw' = password 
    // escolhida pelo utilizador.
    public static byte[] cifrarComPBKDF2(String mensagem, String PBKDF2_from_user_pw) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
        Cipher cipher = Cipher.getInstance("AES");

        KeySpec spec = new PBEKeySpec(PBKDF2_from_user_pw.toCharArray(), getNextSalt(), 65536, 256); // AES-256
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = f.generateSecret(spec).getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encValue = cipher.doFinal(mensagem.getBytes());
        
        return encValue;
    }
}