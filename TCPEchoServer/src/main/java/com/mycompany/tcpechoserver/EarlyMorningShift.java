/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tcpechoserver;
import java.sql.Time;
/**
 *
 * @author noaca
 */
public class EarlyMorningShift {
public static final Day[] days = TCPEchoServer.days;

static final Time twelve = new Time(12 * 3600 * 1000);
public void shiftClasses(Day d)
{
for(TimePeriod tp:d.BusyPeriods)
{
   if(tp.Stime.after(twelve))
   {
   clashesWithEarlyTime(tp,d);
   }
}
}
public static void clashesWithEarlyTime(TimePeriod t, Day d) {
    Time nine = new Time(9 * 3600 * 1000);
    Time half = new Time(1800000);

    t.setSTime(nine);

    long lenOfPeriodMillis = t.getEtime().getTime() - t.getStime().getTime();
    Time lenOfPeriod = new Time(lenOfPeriodMillis);

    t.setETime(new Time(nine.getTime() + lenOfPeriodMillis));

    boolean clashed;
    do {
        clashed = false;
        for (TimePeriod tp : d.getEarlyBookings()) {
            if (t.clashesWith(tp)) {
                t.setSTime(new Time(t.getStime().getTime() + half.getTime()));
                t.setETime(new Time(t.getStime().getTime() + lenOfPeriodMillis));
                clashed = true;
                break;
            }
        }
    } while (clashed && t.getStime().before(twelve));
}

}
