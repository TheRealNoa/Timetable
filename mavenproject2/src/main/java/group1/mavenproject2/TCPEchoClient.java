/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.mavenproject2;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import static javafx.application.Application.launch;

/**
 *
 * @author razi
 */
public class TCPEchoClient{
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, PORT);
            System.out.println("Connected to server.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message to send (type 'exit' to close connection): ");
                String message = scanner.nextLine();
                writer.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                String response = reader.readLine();
                System.out.println("Server response: " + response);
            }

            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

