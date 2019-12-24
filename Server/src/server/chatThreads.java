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
        
        final PipedOutputStream outputToClient2 = new PipedOutputStream();
        try {
            final PipedInputStream  inputFromClient1  = new PipedInputStream(outputToClient2);
        } catch (IOException ex) {
        }
        final PipedOutputStream outputToClient1 = new PipedOutputStream();
        try {
            final PipedInputStream  inputFromClient2  = new PipedInputStream(outputToClient1);
        } catch (IOException ex) {
        }
        // client 1 connecting to client 2
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                * TODO: pipe DataInputStream coming from Client1 into InputFromClient1
                *       pipe outputToClient1 into DataOutputStream( hoping it exists) out going to Client1
                */
            }
        });
        // client 2 being connected to client 1
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                * TODO: pipe DataInputStream coming from Client2 into InputFromClient2
                *       pipe outputToClient2 into DataOutputStream( hoping it exists) out going to Client2
                */
            }
        });
    }
}
