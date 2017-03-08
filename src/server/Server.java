/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import values.Constants;

/**
 *
 * @author Vinay
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    static Vector members;
    ServerSocket sco;
    
    public Server() {
        try {
            members=new Vector();
            sco=new ServerSocket(Constants.SOCKET);
            while(true){
                Socket so=sco.accept();
                Instance ins=new Instance(so);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigWindow().setVisible(true);
            }
        });
        new Server();
        
    }
    
}
