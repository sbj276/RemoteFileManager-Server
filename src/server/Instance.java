/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dbOperations.View;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import values.Constants;

/**
 *
 * @author Vinay
 */
public class Instance extends Thread{
    
    Socket s;
    DataInputStream in;
    DataOutputStream out;
    String logName;
    
    public Instance(Socket s) {
        try {
            this.s = s;
            in=new DataInputStream(s.getInputStream());
            out=new DataOutputStream(s.getOutputStream());
            
            logName=in.readUTF();
            String temp[]=logName.split("&");
            if(
                    !temp[1].equals(Constants.PASSWORD)
                                  ||
                    (Server.members.contains(temp[0]))
               ){
                System.out.println("error username or password reject ");
                out.writeUTF("1:Wrong Password");
                this.stop();
            }
            out.writeUTF("0:Password Accpted");
            logName=temp[0];
            
            synchronized (Server.members) {
                Server.members.add(logName);
                Server.members.notify();
            }
            
            System.out.println(logName+"  connected");
            start();
        } catch (IOException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        super.run();
        String request=null;
        System.out.println("service for "+logName+" is started");
        
        while(true){
            try {
                try {
                    request=in.readUTF();
                } catch (IOException ex) {
                    System.out.println(logName+" disconnected");
                    synchronized(Server.members){
                        Server.members.remove(logName);
                        Server.members.notify();
                    }
                    this.stop();
                }
                System.out.println(logName+" asking for "+request);
                switch(request){
                    case "view":
                        View view=new View(logName);
                        String s=view.viewAll();
                        out.writeUTF(s);
                        break;
                }
                request=null;
            } catch (IOException ex) {
                Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
