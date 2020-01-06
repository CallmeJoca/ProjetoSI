package server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {
    
    public static void main(String[] args) {
        
        
        System.out.println("XIUUU: Troca de Segredos Criptográficos Seguro.");
        System.out.println("\n1 - Modo Cliente\n2 - Modo Servidor\n3 - Sair\n");
        int n = server.Read.readInt();
        
        if (n==1) {
            server.Cliente.main(args);
            //Não responsabilizo pela classe Cliente, todas as edições foram feitas no XiuuuMain
            //Caso deseje uma duplicação do XiuuuMain avisa-me
            //Por: Tiago
        }

        // MUDAR PARA SERVER
        if (n==2) {
            Server.main(args);
        }
        
        if (n==3) {
            System.exit(0);
        }
    }
    
    public static String calculateSHA256(String password) throws Exception {
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = hash.digest(password.getBytes(StandardCharsets.UTF_8));
        
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}