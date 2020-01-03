package cryptation;


public class Users {
    String ip;
    String password;
    String username;
    int status = 0; // 0 - livre / 1 - ocupado (em ligação)
    
    public Users(String ip) {
        this.ip = ip;
        
        System.out.println("Introduza o seu username: ");
        this.username = Read.readString();
        
        System.out.println("Introduza a sua password: ");
        String password_plain = Read.readString();
        try {
            this.password = Main.calculateSHA256(password_plain);
        } catch (Exception ex) {
            System.out.println("Erro na leitura da password.");
        }
    }
    
    public String getIpFromUsername (String username) {
        if (username.equals(this.username)) {
            return this.ip;
        }
        return null;
    }

    @Override
    public String toString() {
        return "username=" + username + ";ip=" + ip + ";password=" + password + ";status=" + status + "\n";
    }
}
