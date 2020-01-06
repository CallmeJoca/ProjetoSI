package Server.src.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Cliente_Escrever {

    public static void loop(String url, int port) {

        InputHandler inputHandler = new InputHandler();
        String input = "";
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                input = inputHandler.input();
                if (!input.equals("")){
                    Socket s = new Socket(url, port);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF(input);
                    dout.flush();
                    dout.close();
                    s.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (!input.equals("end")) ;
        inputHandler.close();
    }

    public static void loop(String url, int port,int debug) {

        InputHandler inputHandler = new InputHandler();
        String input = "";
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                input = inputHandler.input();
                if (!input.equals("")){
                    //transformar o input melhorado em codigo
                    Socket s = new Socket(url, port);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF(input);
                    dout.flush();
                    dout.close();
                    s.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (!input.equals("end")) ;
        inputHandler.close();
    }

    public static boolean touch(String url, int port, String str) {

        boolean bool = true;
        InputHandler inputHandler = new InputHandler();
        Scanner scanner = new Scanner(System.in);
            try {
                    Socket s = new Socket(url, port);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF(str);
                    dout.flush();
                    dout.close();
                    s.close();
            }
            catch (Exception e) {
                System.out.println(e);
                bool = false;
            }
        inputHandler.close();
            return bool;

    }

    public static void main(String[] args) {

        InputHandler inputHandler = new InputHandler();
        String input = "";
        Scanner scanner = new Scanner(System.in);;
        do {
            try {
                input = inputHandler.input();
                if (!input.equals("")){
                    Socket s = new Socket("localhost", 6666);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF(input);
                    dout.flush();
                    dout.close();
                    s.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (!input.equals("end")) ;
        inputHandler.close();
    }
}