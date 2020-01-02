package cryptation;

public class Users {
    String ip;
    String password;
    String username;
    
    public Users(String ip) {
        this.ip = ip;
        
        System.out.println("Introduza o seu username: ");
        this.username = Read.readString();
        
        System.out.println("Introduza a sua password: ");
        this.password = Read.readString();
    }
    
    public String getIpFromUsername (String username) {
        if (username.equals(this.username)) {
            return this.ip;
        }
        return null;
    }

    @Override
    public String toString() {
        return "username=" + username + "\nip=" + ip + "\npassword=" + password;
    }
}
