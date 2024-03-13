package com.mycompany.tcpechoserver;
import java.io.*;
import java.net.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TCPEchoServer{
    private static ServerSocket serverSocket;
    private static final int PORT = 12345;
    private static Day CurrentDay = new Day();
    private static Day Monday = new Day("Monday");
    private static Day Tuesday = new Day("Tuesday");
    private static Day Wednesday = new Day("Wednesday");
    private static Day Thursday = new Day ("Thursday");
    private static Day Friday = new Day ("Friday");
    private static Day[] days = {Monday,Tuesday,Wednesday,Thursday,Friday};
    private static TimePeriod TP = new TimePeriod();
    public static void main(String[] args) {

        // Days initialisation
        
        //
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
    private static void assignDay(String message)
    {
    String[]tempArr;
                tempArr = message.split(",");
                List<String> arrayList = new ArrayList<>(Arrays.asList(tempArr));
                arrayList.remove(0);
                for(Day d:days)
                {
                if (arrayList.contains(d.toString())){
                    CurrentDay = d;
                }
                }
    }
    private static void assignTimePeriod(String message)
    {
        String[]tempArr;
            tempArr = message.split(",");
                List<String> arrayList = new ArrayList<>(Arrays.asList(tempArr));
                DateFormat formatter= new SimpleDateFormat("HH:mm");
                Double t1 = Double.parseDouble(arrayList.get(4));
                Double t2 = Double.parseDouble(arrayList.get(5));
                
                long milliseconds1 = (long) (t1 * 60 * 60 * 1000);
                long milliseconds2 = (long) (t2 * 60 * 60 * 1000);
                
                Time startTime = new Time(milliseconds1);
                Time endTime = new Time(milliseconds2);
                
                TP = new TimePeriod(startTime,endTime);
                
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
                if(message.contains("FI"))
                {
                assignDay(message);
                assignTimePeriod(message);
                System.out.println(TP.toString());
                }
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
