/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.src.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author henri
 */
public class Cliente_Ouvir {

    /**
     * @param port the command line arguments
     */
    public static void start(int port,int wait_ms) {
        String str = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            do{
                try{
                    ServerSocket socket = new ServerSocket(port);
                    socket.setSoTimeout(wait_ms);
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    str = (String) dis.readUTF();
                    //Do something
                    System.out.println("Client Says = " + str);
                }
                catch(EOFException exc) {
                    continue;
                }
                catch(Exception e){
                    System.out.println(e);
                    break;
                }


            }while(!str.equals("end"));
            ss.close();
        }
        catch ( Exception e ) { System.out.println(e); }
    }

    public static void start(int port) {
        String str = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            do{
                try{
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    str = (String) dis.readUTF();
                    //Do something
                    System.out.println("Client Says = " + str);
                }
                catch(EOFException exc) {
                    continue;
                }
                catch(Exception e){
                    System.out.println(e);
                    break;
                }


            }while(!str.equals("end"));
            ss.close();
        }
        catch ( Exception e ) { System.out.println(e); }
    }
    public static void readAll(int port) {
        String str = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            do{
                try{
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    str = (String) dis.readUTF();
                    //Do something
                    System.out.println("Client Says = " + str);
                }
                catch(EOFException exc) {
                    continue;
                }
                catch(Exception e){
                    System.out.println(e);
                    break;
                }


            }while(!str.equals(""));
            ss.close();
        }
        catch ( Exception e ) { System.out.println(e); }
    }

    public static void main(String[] args) {
        String str = null;
        ServerSocket ss = null;
        try {
              ss = new ServerSocket(6666);
            do{
                try{
                    Socket s = ss.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    str = (String) dis.readUTF();
                    System.out.println("Client Says = " + str);
                }
                catch(EOFException exc) {
                    continue;
                }
                catch(Exception e){
                    System.out.println(e);
                    break;
                }
            

            }while(!str.equals("end"));
            ss.close();
        }
        catch ( Exception e ) { System.out.println(e); }
    }
    
}
