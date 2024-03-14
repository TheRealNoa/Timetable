package group1.mavenproject2;

import java.io.*;
import java.net.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TCPEchoClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 12345;
    public static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    public static boolean connection=true;

    public static void start()
    {
    try{socket = new Socket(SERVER_IP, PORT);
            System.out.println("Socket found.");}catch(IOException e){}
    }
    
    public static void main(String[] args) {
        try {
            //socket = new Socket(SERVER_IP, PORT);
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
                socket = null;
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
                if(message.equals("TERMINATE"))
                {
                    System.out.println(message);
                }else
                {
                System.out.println("Received from server: " + message);
                }
            }
        }catch(SocketException s)
        {
        System.out.println("Connection reset");
        socket = null;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendClientData(String StartDate, String EndDate, String DayOfWeek, String StartTime, String EndTime) {
      Alert a = new Alert(AlertType.ERROR);
      String[] client ={StartDate,EndDate,DayOfWeek,StartTime,EndTime};
      String temp="";
      a.setContentText("You are not connected to the server.");
        if(socket!=null){
            //out.println("FullClientData"); myb this should be a command ?
            out.println("FI," + concatenateWithComma(client));
            MonthPickerApp.currentStage.close();
            App.currentStage=App.primaryStage;
            App.currentStage.show();

        }else
        {
        System.out.println("Not connected to server.");
        MonthPickerApp.currentStage.close();
        App.currentStage = App.primaryStage;
        App.currentStage.show();
        CSVManagement.write("FI",StartDate, EndDate, DayOfWeek, StartTime, EndTime); // FI Is for "Full Info"
        a.show();
        }
    }
    // keeping this just in case...
    public static void sendMessage(String message) {
        if(socket!=null && connection){
        out.println(message);
        }else
        {
        Alert a = new Alert(AlertType.ERROR);
         a.setContentText("You are not connected to the server.");
         a.show();
        }
    }
    public static String concatenateWithComma(String[] array) {
        StringBuilder tempBuilder = new StringBuilder();
        
        for (int i = 0; i < array.length; i++) {
            tempBuilder.append(array[i]);
            
            if (i < array.length - 1) {
                tempBuilder.append(",");
            }
        }
        
        return tempBuilder.toString();
    }
}