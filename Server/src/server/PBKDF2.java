package server;

import java.security.SecureRandom;
import java.util.Random;
import javax.crypto.SecretKey;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2 {
    
    private static final Random RANDOM = new SecureRandom();
    
    public PBKDF2() {
        
    }
    
    // Geração de 'salt'
    public byte[] getNextSalt() {
        byte[] salt = new byte[64];
        RANDOM.nextBytes(salt);
        return salt;
    }
    
    // Calcula e devolve PBKDF2 da password em parâmetro.
    public String getPBKDF2(String password) throws Exception {
        
        char[] chars = password.toCharArray();
        byte[] salt = this.getNextSalt();
        int iterations = 10000;
        
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 256);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = skf.generateSecret(spec).getEncoded();
        
        return key.toString();
    }
    
    public String cifrarComPBKDF2(String mensagem, SecretKey chave) {
        // TODO: PBKDF2 com um atributo chave que é inicializado no construtor
        // ou passar como parâmetro para aqui cifrar
        return "Couves";
    }
}
