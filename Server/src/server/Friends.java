package Server.src.server;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class Friends {

    private static String path = "C:\\Users\\tugat\\IdeaProjects\\ProjetoSI\\Server\\friends.properties";

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

    public static String menu() {

        String a = "";
        try{
            while (!a.equals("sair")) {
                a = JOptionPane.showInputDialog(null,
                        "Deseja 'add', 'edit' ou 'remove' alguém?\n" +
                                "Também poderá 'exit' escrevendo a palavra que está dentro entre aspas ");
                switch (a) {
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
                        return "";
                    default:

                }
                //startClient();
            }
        }catch(Exception E){ }
        return a;
    }

    public static String read(String key){
        try (InputStream input = new FileInputStream(path)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return prop.getProperty(key);

        } catch (IOException ex) {
        }
        return "0";

    }


    public static void write(String[] key,String[] value ){

        try (OutputStream output = new FileOutputStream("path/to/config.properties")) {

            Properties prop = new Properties();
            int i = 0;
            // set the properties value
            for(i = 0; i < key.length; i++){
                try {
                    prop.setProperty(key[i], value[i]);
                }catch (Exception e){
                    break;
                }
            }
            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
        }

    }
    public static void write(String key,String value ){

        try (OutputStream output = new FileOutputStream(path)) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty(key, value);

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
        }

    }
    public static void edit(String key,String newkey ){
        String value = read(key);
        remove(key);
        write(newkey,value);
    }
    public static boolean exist(String key ){
        String value = read(key);
        return !value.equals("0");
    }
    public static void remove(String key){

        try (InputStream input = new FileInputStream(path)) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);

            try (OutputStream output = new FileOutputStream(path)) {

                // set the properties value
                prop.remove(key);
                // save properties to project root folder
                prop.store(output, null);

            } catch (IOException io) {
            }

        } catch (IOException ex) {
        }

    }
}
