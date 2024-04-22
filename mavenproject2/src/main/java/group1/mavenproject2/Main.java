/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import static javafx.application.Application.launch;

/**
 *
 * @author noaca
 */
public class Main {
    public static volatile boolean isRunning = true;
    public static void main(String[] args) {
        Thread ServerConnect = new Thread(()->
        {
        TCPEchoClient.main(args);
        });
         Thread ConnectionCheck = new Thread(()->
        {
        TCPEchoClient.start();
        if(TCPEchoClient.socket!=null)
        {
        ServerConnect.start();
        }else
        {
        System.out.println("Socket not found. Client app running without connection to server.");
        }
        });
        ConnectionCheck.start();
        
        launch(AppView.class, args);
        TCPEchoClient.socket=null;        
   }

    }

