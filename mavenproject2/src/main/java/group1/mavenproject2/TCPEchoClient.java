/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.mavenproject2;
import java.io.*;
import java.net.*;

/**
 *
 * @author razi
 */
public class TCPEchoClient {
 private static InetAddress host;
    private static final int PORT = 25564;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    
    public TCPEchoClient() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            e.printStackTrace();
            System.exit(1);
        }
        connectToTCP();
    }

    
   public void connectToTCP() {			//Step 1.
    try 
    {
	socket = new Socket(host,PORT);		//Step 1.
	BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//Step 2.
	PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);	 //Step 2.

    } 
    catch(IOException e)
    {
	e.printStackTrace();
    } 
   }
   private void sendString(String s)
   {
       if(socket!=null&&reader!=null)
       {
           writer.println(s);
       }else
       {
       System.err.println("Not connected to server?");
       }
   }
 } 
