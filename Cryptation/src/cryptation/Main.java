package cryptation;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("XIUUU: Troca de Segredos Criptográficos Seguro.");
        System.out.println("\n1 - Modo Cliente\n2 - Modo Servidor\n3 - Sair\n");
        int n = Read.readInt();
        
        if (n==1) {
            Client.main(args);
        }
        
        if (n==2) {
            Client2.main(args);
        }
        
        if (n==3) { 
            System.exit(0);
        }
    }
}
