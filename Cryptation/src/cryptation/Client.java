
package cryptation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        
        System.out.println("--- Entrou no Modo Cliente ---");
        String msg = null;
        String msgRecebida = null;
        
        // O SERVIDOR TEM DE ESTAR LIGADO PARA HAVEREM CLIENTES
        // NO SERVIDOR, QUEM ENTRAR NO MODO SERVIDOR ESCOLHE QUAL O PROTOCOLO A SER USADO
        
        // OS CLIENTES CONECTAM-SE AO SERVIDOR ATRAVES DE UMAA SOCKET C O IP DO SERVER
        // VEEM QUAIS SAO OS CLIENTES DISPONIVEIS
        // ESCOLHEM O IP DE UM
        // O OUTRO TEM DE RECEBER UMA NOTIFICACAO A PERGUNTAR SE QUER CONECTAR, SENAO CATCH ERROR NA SOCKET
        // SE ACEITAR, COMUNICAM DIRETAMENTE ATRAVES DE UMA SOCKET COM
        
        do {
            System.out.println("Introduza a mensagem: ");
            msg = Read.readString();
            try {
                Socket s = new Socket("192.168.0.181", 6666);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                DataInputStream dis = new DataInputStream(s.getInputStream());
                
                dout.writeUTF(msg);
                
                msgRecebida = (String) dis.readUTF();
                System.out.println("Recebi = " + msgRecebida);
                dout.flush();
                dout.close();
                s.close();
            } catch(Exception e) {
                System.out.println("Não foi possível estabelecer a ligação.");
            }
        } while (!msg.equals("end"));
        
        
    }
}