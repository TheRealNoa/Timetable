package com.mycompany.tcpechoserver;
import java.io.*;
import java.net.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TCPEchoServer{
    private static ServerSocket serverSocket;
    private static final int PORT = 12345;
    private static Day CurrentDay = new Day();
    private static  final Day Monday = new Day("Monday");
    private static final Day Tuesday = new Day("Tuesday");
    private static final Day Wednesday = new Day("Wednesday");
    private static final Day Thursday = new Day ("Thursday");
    private static final Day Friday = new Day ("Friday");
    public static final Day[] days = {Monday,Tuesday,Wednesday,Thursday,Friday};
    private static TimePeriod TP = new TimePeriod();
    private static ArrayList<String> classes = new ArrayList<>();
    private static String message;
    private static BufferedReader reader;
    private static PrintWriter writer;
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running and waiting for connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client: " + clientSocket.getInetAddress().getHostAddress());
                //handleClient(clientSocket);
                Thread tempT = new Thread(new ClientHandler(clientSocket));
                tempT.start();
            }
        }catch(SocketException b)
        {
            System.out.println("Client terminated the server app. Server shutdown.");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
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
    private static void displayTimetable()
    {
        for(Day d:days)
        {
            System.out.println(d.displayDay());
        }
    }
    private static void assignTimePeriod(String message)
    {
        String[]tempArr= message.split(",");
                List<String> arrayList = new ArrayList<>(Arrays.asList(tempArr));
                Double t1 = Double.valueOf(arrayList.get(4));
                Double t2 = Double.valueOf(arrayList.get(5));
                
                long milliseconds1 = (long) ((t1 * 60 * 60 * 1000)-(60*60*1000));
                long milliseconds2 = (long) ((t2 * 60 * 60 * 1000)-(60*60*1000));
                
                Time startTime = new Time(milliseconds1);
                Time endTime = new Time(milliseconds2);
                
                TP = new TimePeriod(startTime,endTime,arrayList.get(arrayList.size()-1));
                
                CurrentDay.addTimeSlot(TP,assignModule(message));
                
    }
    static String className;
    private static void assignClassName(String message)
    {
    String[]tempArr= message.split(",");
    List<String> arrayList = new ArrayList<>(Arrays.asList(tempArr));
    className = arrayList.getLast();
    }
    
    private static Module assignModule(String m)
    {
        String[]tempArr= m.split(",");
    List<String> arrayList = new ArrayList<>(Arrays.asList(tempArr));
    String modName = arrayList.get(arrayList.size()-2);
    Module mod = new Module(modName,className);
    return mod;
    }
     private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);
                // Echo back to client
                //writer.println("Server received: " + message);
                // going to change this to include memory access control
                if(message.contains("FI"))
                {
                dealWithFI();
                }
                else if(message.contains("TD"))
                {
                dealWithTD();
                }
                else if(message.equals("STOP"))
                {
                dealWithStop();
                }
                else if(message.contains("ShowDaySchedule"))
                {
                 dealWithSDS();
                }
                else if(message.contains("RemClassMsG,"))
                {
                 dealWithRC();
                }
                else if(message.contains("NewClass"))
                {
                 dealWithNC();
                }
                else if(message.equals("RCL"))
                {
                 dealWithRCL();
                }else if(message.contains("DeleteClasses"))
                {
                 dealWithDC();
                }else if(message.equals("EarlyTimes"))
                {
                dealWithET();
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     public static synchronized void dealWithET()
     {
     EarlyMorningShift earlyMorningShift = new EarlyMorningShift();
     String[] args = {};
     earlyMorningShift.main(args);
     }
     public static synchronized void dealWithFI()
     {
     assignDay(message);
     assignTimePeriod(message);
     assignClassName(message);
     assignModule(message);
     System.out.println(TP.toString());
     }
     public static synchronized void dealWithTD()
     {
      System.out.println("Timetable:");
      displayTimetable();
     }
     public static synchronized void dealWithStop() {
    try {
        writer.println("TERMINATE");

        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        if (reader != null) {
            reader.close();
        }
        if (writer != null) {
            writer.close();
        }

        System.out.println("Server stopped successfully.");
    } catch (IOException e) {
        System.err.println("Error occurred during server shutdown:");
        e.printStackTrace();
    }
}
     public static synchronized void dealWithSDS()
     {
      String[] tempArr = message.split(",");
      String dayString = tempArr[1];
      System.out.println("Day recieved:" + tempArr[1]);
      for(Day d : days)
      {
       if(d.name.equalsIgnoreCase(dayString))
        {
        writer.println("SBPTEAR, " + d.getBusyPeriods());
        }
      }       
     }
     public static synchronized void dealWithRC()
     {
      String[] tempArr = message.split(",");
      if(!tempArr[1].equals(""))
      {
        for(Day d:days)
        {
            if(d.name.equalsIgnoreCase(tempArr[2]))
            {
                System.out.println("Trying to remove class.");
                String[]tempArr2 = tempArr[1].split("-");
                Double t1 = convertTimeToDecimal(tempArr2[0]);
                Double t2 = convertTimeToDecimal(tempArr2[1]);
                long milliseconds1 = (long) ((t1 * 60 * 60 * 1000)-(60*60*1000));
                long milliseconds2 = (long) ((t2 * 60 * 60 * 1000)-(60*60*1000));
                
                Time startTime = new Time(milliseconds1);
                Time endTime = new Time(milliseconds2);
                TimePeriod tempT = new TimePeriod(startTime,endTime);
                System.out.println(tempT);
                d.removeBooking(tempT);
                System.out.println(d.getBusyPeriods());
            }
        }
      }
    }
     public static synchronized void dealWithNC()
     {
     String[] tempArr = message.split(",");
     classes.add(tempArr[1]);
     System.out.println(classes);
     }
     public static synchronized void dealWithRCL()
     {
     System.out.println("Sending back list of classes");
     writer.println("RLC1 ," + classes);
     }
     public static synchronized void dealWithDC()
     {
        String[] tempArr = message.split(",");
        String className = tempArr[1];
        removeAllClassesFromOneClass(className);
     }
     public static void removeAllClassesFromOneClass(String m)
    {
    for(Day d:days)
    {
    d.removeAllClassTimes(m);
    }
    //System.out.println("Removed all of this classes modules");
    }
     public static double convertTimeToDecimal(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        double decimalHours = hours + (minutes / 60.0);

        return decimalHours;
    }
}
