package group1.mavenproject2;

import java.io.*;
import java.net.*;

public class TCPEchoClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 12345;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_IP, PORT);
            System.out.println("Connected to server.");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            disconnect();
        }
    }
    private static void disconnect()
    {
        try{
        out.println("client disconnected");
        if (socket != null && !socket.isClosed()) 
        {
                socket.close();
                in.close();
                out.close();
                System.out.println("Disconnected from server.");
        }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void startListening() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from server: " + message);
            }
        }catch(SocketException s)
        {
        System.out.println("Connection reset");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }finally
        {
            disconnect();
        }
    }

    public static void sendMessage(String message) {
        out.println(message);
    }
}