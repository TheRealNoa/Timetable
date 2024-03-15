
package com.mycompany.tcpechoserver;

import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author noaca
 */
public class Day {
    String name;
    ArrayList<TimePeriod> BusyPeriods;
    private Time TempSTime;
    private Time TempETime;
    public Day(){}
    public Day(String name)
    {
    this.name=name;
    BusyPeriods = new ArrayList<>();
    }
    @Override
    public String toString()
    {
        return this.name;
    }
    public ArrayList<TimePeriod> getBusyPeriods()
    {
    return BusyPeriods;
    }
    public String BusyPeriodsOut() {
    String temp = "";
    for (TimePeriod t : BusyPeriods) {
        temp += t + "\n";
    }
    return temp;
}
    public String displayDay()
    {
    return "Day: " + this.name + "\n" + "Busy periods:" + "\n" + BusyPeriodsOut() + "\n";
    }
    public void addTimeSlot(TimePeriod a){
    if(checkBookings(a))
    {
    System.out.println("Error, can't book.");
    }
    else
    {
    System.out.println("Added a booking");
    BusyPeriods.add(a);
    }
    }
    public boolean checkBookings(TimePeriod a)
    {
     for(TimePeriod t:BusyPeriods)
     {
         if(t.clashesWith(a))
             return true;
     }
      return false;
    }
    
}
