package com.mycompany.tcpechoserver;
import java.io.*;
import java.net.*;

public class TCPEchoServer{
    private static ServerSocket serverSocket;
    private static final int PORT = 12345;

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running and waiting for connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client: " + clientSocket.getInetAddress().getHostAddress());
                handleClient(clientSocket);
            }
        }catch(SocketException b)
        {
            System.out.println("Client terminated app. Server shutdown.");
           // try{serverSocket.close();}catch(IOException e){e.printStackTrace();}
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        /*finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);
                // Echo back to client
                writer.println("Server received: " + message);
            }
        }catch(SocketException s)
        {
        System.out.println("Connection reset.");}
        /*try{serverSocket.close();}catch(IOException e)
        {
        System.out.println("Server closed due to client shutting down.");
        }
        }
        */catch (IOException e) {
            e.printStackTrace();
        }/* finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    */}
}
