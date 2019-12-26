package Server.src.server;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Conta {

    private static String menus = "Escreve o nome da pessoa para iniciar uma conversa ";

    public static void remover(){
        String a;
        try{
        a = JOptionPane.showInputDialog(null, "Nickname");
        Friends.remove(a);
        }catch(Exception E){ }

    }
    public static void editar(){
        try{
        String a,b;
        a = JOptionPane.showInputDialog(null, "Nickname");
        b = JOptionPane.showInputDialog(null, "Novo Nickname");
        Friends.edit(a,b);
        }catch(Exception E){ }

    }
    public static void adicionar(){
        String a,b;
        try {
            a = JOptionPane.showInputDialog(null, "Nickname");
            b = JOptionPane.showInputDialog(null, "IP");
            Friends.write(a, b);
        }catch(Exception E){ }

    }
    public static void choose(){
        String hostt = null;
        int porrt;
        String portt = null;
        String a;
        Socket socket = new Socket();
        try{
        a = JOptionPane.showInputDialog(null,"Nome");
            try{
                startClient(Friends.read(a),230);
                //non-static method startClient(int) cannot be referenced from a static context
            }catch (Exception e){
                startServer(230);
            }
        }catch(Exception E){
        }
    }
    public static void menu() {

        String a = "";
        try{
        while (!a.equals("sair")) {
            a = JOptionPane.showInputDialog(null,
                    "Deseja 'start' uma conversa  ou 'add', 'edit' ou 'remove' alguém?\n" +
                            "Também poderá 'exit' escrevendo a palavra que está dentro entre aspas ");
            switch (a) {
                case "start":
                    choose();
                    break;
                case "add":
                    adicionar();
                    break;
                case "edit":
                    editar();
                    break;
                case "remove":
                    remover();
                    break;
                case "exit":
                    return;
                default:

            }
            //startClient();
        }
        }catch(Exception E){ }
    }
    public static void main(String[] args)
    {
        menu();
    }


    private static void startClient(String host, int port)
    {


        try
        {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            chat(socket);
        }
        catch (UnknownHostException e)
        {
            System.out.println("Host desconhecido");
            System.out.println("Host: " + host);
            System.out.println("Porta: " + port);
            System.exit(2);
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            //System.exit(1);
        }
    }

    private static void startServer(int porta)
    {
        try
        {
            ServerSocket socket = new ServerSocket(porta);
            socket.setSoTimeout(10*1000);
            System.out.println("Aguardando conexao...");
            Socket s = socket.accept();

            chat(s);
        } catch (Exception e)
        {
            System.out.println("Problemas na comunicação ");
        }
    }

    private static void chat(Socket s) throws IOException
    {
        System.out.println("Conectado com " + s.getRemoteSocketAddress());
        listen(s.getInputStream());
        talk(s.getOutputStream());
    }

    /**
     * @param outputStream
     */
    private static void talk(OutputStream outputStream) throws IOException
    {
        DataOutputStream output = new DataOutputStream(outputStream);
        String line = "";
        while (!line.equals("EXIT"))
        {
            Scanner scan = new Scanner(System.in);
            System.out.print(": ");
            line = scan.nextLine();
            output.writeInt(line.length());
            for (char ch : line.toCharArray())
                output.writeChar(ch);
            output.flush();
        }
    }

    /**
     * @param inputStream
     */
    private static  void listen(final InputStream inputStream)
    {
        new Thread(new Runnable() {
            DataInputStream ds = new DataInputStream(inputStream);
            public void run()
            {
                try
                {
                    while (true)
                    {
                        int size = ds.readInt();
                        int cont = 0;
                        char[] chars = new char[size];
                        while (cont < size)
                        {
                            chars[cont] = ds.readChar();
                            cont = cont + 1;
                        }

                        String str = new String(chars);
                        if (str.equals("EXIT"))
                        {
                            System.out.println("Conversa terminada.");
                            System.exit(0);
                        }

                        System.out.println(str);
                    }
                } catch (IOException e)
                {
                }
            }
        }).start();
    }
}
