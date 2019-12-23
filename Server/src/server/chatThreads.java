/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class chatThreads extends Thread {
    
    @Override
    public void run() {
        
        final PipedOutputStream output = new PipedOutputStream();
        try {
            final PipedInputStream  input  = new PipedInputStream(output);
        } catch (IOException ex) {
            Logger.getLogger(chatThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
