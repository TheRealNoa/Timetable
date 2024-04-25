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
    
    private static  final Day Mon = new Day("Monday");
    private static final Day Tue = new Day("Tuesday");
    private static final Day Wed = new Day("Wednesday");
    private static final Day Thu = new Day ("Thursday");
    private static final Day Fri = new Day ("Friday");
    public static final Day[] OGdays = {Mon,Tue,Wed,Thu,Fri};
    
    private static TimePeriod TP = new TimePeriod();
    private static ArrayList<String> classes = new ArrayList<>();
    private static String message;
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
    private static void displayTimetable(PrintWriter pw)
    {
        System.out.println("Sending a display of the timetable to client");
        for(Day d:days)
        {
            pw.println(d.displayDay());
        }
    }
    
    private static void checkForChanges(PrintWriter pw)
    { 
        new Thread(() -> {
        while (true) {
            try {
                //System.out.println("wtf");
                Thread.sleep(3000);
                for(int i=0;i<5;i++)
                {
                        if(days[i].BusyPeriods.size()>OGdays[i].BusyPeriods.size())
                        {
                        for(TimePeriod t:days[i].BusyPeriods)
                        {
                                TimePeriod ntp = new TimePeriod(t.Stime,t.Etime);//this is the time period we've added
                                if(!OGdays[i].checkBookings(ntp))
                                {
                                OGdays[i].BusyPeriods.add(ntp);
                                pw.println("UpdateAdd," + days[i].name + "," + ntp);
                                }
                        }
                        System.out.println("Og busy periods:"+OGdays[i].BusyPeriodsOut());
                        }else if(days[i].BusyPeriods.size()<OGdays[i].BusyPeriods.size())
                        {
                        System.out.println("We have removed something");
                        }
                    //System.out.println("For " + days[i].name + ", we updated: " +days[i].BusyPeriodsOut());
                    //OGdays[i].BusyPeriods=days[i].BusyPeriods;
                    }
                //BusyTOriginal=lNew;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
    }
    private static void sendUpdate(PrintWriter pw, String data)
    {
        pw.println("Update,");
    }
    private static void assignTimePeriod(String message, PrintWriter pw)
    {
        String[]tempArr= message.split(",");
                List<String> arrayList = new ArrayList<>(Arrays.asList(tempArr));
                Double t1 = Double.valueOf(arrayList.get(4));
                Double t2 = Double.valueOf(arrayList.get(5));
                
                long milliseconds1 = (long) ((t1 * 60 * 60 * 1000)-(60*60*1000));
                long milliseconds2 = (long) ((t2 * 60 * 60 * 1000)-(60*60*1000));
                
                Time startTime = new Time(milliseconds1);
                Time endTime = new Time(milliseconds2);
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
                System.out.println(arrayList.get(arrayList.size()-2));
                System.out.println(arrayList.get(arrayList.size()-1));
                System.out.println(arrayList.get(arrayList.size()));
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
                TP = new TimePeriod(startTime,endTime,arrayList.get(arrayList.size()-1));
                pw.println("aaaaaaaaaaa sise");
                CurrentDay.addTimeSlot(TP,assignModule(message));
                String result = CurrentDay.addTimeSlot(TP,assignModule(message));
                pw.println(result);
                
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
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            try {

            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);
                checkForChanges(writer);
                // Echo back to client
                //writer.println("Server received: " + message);
                // going to change this to include memory access control
                if(message.contains("FI"))
                {
                dealWithFI(writer);
                }
                else if(message.contains("TD"))
                {
                dealWithTD(writer);
                }
                else if(message.equals("STOP"))
                {
                dealWithStop(writer,reader);
                }
                else if(message.contains("ShowDaySchedule"))
                {
                 dealWithSDS(writer);
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
                 dealWithRCL(writer);
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
     public static synchronized void dealWithFI(PrintWriter pw)
     {
     assignDay(message);
     assignTimePeriod(message,pw);
     assignClassName(message);
     assignModule(message);
     System.out.println(TP.toString());
     }
     public static synchronized void dealWithTD(PrintWriter pw)
     {
      System.out.println("Timetable sent.");
      pw.println("TimetableDisplay");
      displayTimetable(pw);
     }
     public static synchronized void dealWithStop(PrintWriter pw, BufferedReader br) {
    try {
        pw.println("TERMINATE");

        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        if (br != null) {
            br.close();
        }
        if (pw != null) {
            pw.close();
        }

        System.out.println("Server stopped successfully.");
    } catch (IOException e) {
        System.err.println("Error occurred during server shutdown:");
        e.printStackTrace();
    }
}   
     public static String dayString;
     public static synchronized void dealWithSDS(PrintWriter pw)
     {
      String[] tempArr = message.split(",");
      dayString = tempArr[1];
      System.out.println("Day recieved:" + tempArr[1]);
      for(Day d : days)
      {
       if(d.name.equalsIgnoreCase(dayString))
        {
        pw.println("SBPTEAR, " + d.getBusyPeriods());
        }else
       {
       pw.println("Wtf day");
       }
      }       
     }
     public static synchronized void dealWithRC() {
    String[] tempArr = message.split(",");
            for (int i = 0; i < days.length; i++) {
                if (days[i].name.equalsIgnoreCase(dayString)) {
                    System.out.println("Trying to remove class.");
                    String[] tempArr2 = tempArr[1].split("-");
                    if (tempArr2.length == 2) { // Check if tempArr2 has 2 elements
                        Double t1 = convertTimeToDecimal(tempArr2[0]);
                        Double t2 = convertTimeToDecimal(tempArr2[1]);
                        long milliseconds1 = (long) ((t1 * 60 * 60 * 1000) - (60 * 60 * 1000));
                        long milliseconds2 = (long) ((t2 * 60 * 60 * 1000) - (60 * 60 * 1000));

                        Time startTime = new Time(milliseconds1);
                        Time endTime = new Time(milliseconds2);
                        TimePeriod tempT = new TimePeriod(startTime, endTime);
                        System.out.println(tempT);
                        days[i].removeBooking(tempT);
                        System.out.println("Day bookings:" + days[i].getBusyPeriods());
                        System.out.println("OGday bookings:" +OGdays[i].getBusyPeriods());
                    } else {
                        System.out.println("Invalid format for time range");
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
     public static synchronized void dealWithRCL(PrintWriter pw)
     {
     System.out.println("Sending back list of classes");
     pw.println("RLC1 ," + classes);
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
