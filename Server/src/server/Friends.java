package Server.src.server;

import java.io.*;
import java.util.Properties;

public class Friends {

    private static String path = "C:\\Users\\tugat\\IdeaProjects\\ProjetoSI\\Server\\friends.properties";

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
