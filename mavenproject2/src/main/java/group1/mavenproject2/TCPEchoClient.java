package group1.mavenproject2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TCPEchoClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 12345;
    public static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    public static boolean connection=true;
    public static boolean isRunning = true;
    public static void start()
    
    {
    try{socket = new Socket(SERVER_IP, PORT);
            System.out.println("Socket found.");}
    catch(IOException e)
    {
    e.printStackTrace();
    }
    }
    
    public static void main(String[] args) {
        try {
            System.out.println("Connected to server.");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            startListening();
    } catch (IOException e) {
        e.printStackTrace();
    }
        if(socket==null)
        {
        stop();
        }

}
    public static ArrayList<String> daysList;
    private static void startListening() {
        while(isRunning){
        try {
            String message;
            
            while ((message = in.readLine()) != null) {
                if(message.equals("TERMINATE"))
                {
                    System.out.println(message);
                }else if(message.contains("SBPTEAR"))
                {
                    System.out.println("Recieved SBPTEAR");
                    int commaIndex = message.indexOf(", ");
                    if (commaIndex != -1) {
                    message = message.substring(commaIndex + 2);
                } 
                   daysList = new ArrayList<>();
                    String[] daysArr = message.substring(1, message.length() - 1).split(", ");
                     for(String s : daysArr) {
                        daysList.add(s);
                        }
                    Platform.runLater(() -> RemoveClassStage.showDaySchedule(daysList));
                    // This handles the IllegalStateException that I was getting...
                    // It was appearing because apparently we can't execute RemoveClassStage.showDaySchedule(daysList)
                    // on the same thread as the TCPEchoClient thread... has to be a JavaFX thread
                }else if(message.contains("RLC1"))
                {
                    System.out.println("RECIEVED RLC FROM SERVER" + message);
                    String[] tempArr = message.split(",");
                    ArrayList<String> t = new ArrayList<>();
                    for(String s:tempArr)
                    {
                    t.add(s);
                    }
                  Platform.runLater(() -> MonthPickerView.showClasses(t));
                }
                else
                
                {
                System.out.println("Received from server: " + message);
                }
            }
        }
        catch(SocketException s)
        {
        System.out.println("Connection reset");
        socket = null;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        }
        
    }
    public static void sendClientData(String StartDate, String EndDate, String DayOfWeek, String StartTime, String EndTime, String Class, String CourseName) {
      Alert a = new Alert(AlertType.ERROR);
      String[] client ={StartDate,EndDate,DayOfWeek,StartTime,EndTime,Class,CourseName};
      String temp="";
      a.setContentText("You are not connected to the server.");
        if(socket!=null){
            //out.println("FullClientData"); myb this should be a command ?
            out.println("FI," + concatenateWithComma(client));
            MonthPickerView.currentStage.close();
            AppView.primaryStage.show();

        }else
        {
        System.out.println("Not connected to server.");
        MonthPickerView.currentStage.close();
        AppView.primaryStage.show();
        a.show();
        }
    }
    // keeping this just in case...
    public static synchronized void sendMessage(String message) {
        if(socket!=null && connection){
        out.println(message);
        System.out.println("sendMessage ran");
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
    public static void stop() {
        isRunning = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                System.out.println("Disconnected from server.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}