
package com.mycompany.tcpechoserver;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author noaca
 */
public class Day {
    String name;
    ArrayList<TimePeriod> BusyPeriods;
    private Time TempSTime;
    private Time TempETime;
    private ArrayList<Module> modules;
    private ArrayList<ModuleTimePeriodEntry> combinedList;
    public ArrayList<TimePeriod> earlyPeriods;
    public Day(){}
    public Day(String name)
    {
    this.name=name;
    BusyPeriods = new ArrayList<>();
    modules = new ArrayList<>();
    combinedList = new ArrayList<>();
    }
    public void addModule(Module m)
    {
    this.modules.add(m);
    }
    public void removeModuleByClass(String c)
    {
    for(int i=1;i<modules.size();i++)
    {
    Module m= modules.get(i);
    if(m.hasClass(c)){
        modules.remove(m);
    }
    }
    }
    @Override
    public String toString()
    {
        return this.name;
    }
    public List<TimePeriod> getBusyPeriods() {
    return Collections.unmodifiableList(BusyPeriods);
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
    public void addTimeSlot(TimePeriod a, Module m){
    if(checkBookings(a))
    {
    System.out.println("Error, can't book.");
    }
    else
    {
    System.out.println("Added a booking");

    BusyPeriods.add(a);
    ModuleTimePeriodEntry entry =  new ModuleTimePeriodEntry(m, a);
    combinedList.add(entry);    
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
    public void removeBooking(TimePeriod a)
    {
    for(int i=0;i<BusyPeriods.size();i++)
    {
        TimePeriod t = BusyPeriods.get(i);
    if(t.clashesWith(a))
    {
    BusyPeriods.remove(t);
    System.out.println("Removed class:" + t);
    }
    }
    }
     public void removeAllClassTimes(String className)
    {
    for(TimePeriod t:BusyPeriods)
    {
    if(t.getClass().toString().contains(className))
    {
     System.out.println("Contains");
    BusyPeriods.remove(t);
    }
    }
    }
    
     
     /// part 2 methods
     
     public ArrayList<TimePeriod> getEarlyBookings() {
    ArrayList<TimePeriod> earlyPeriods = new ArrayList<>();
    Time twelvePM = new Time(11 * 3600 * 1000);

    for (int i=0; i<BusyPeriods.size()-1; i++) {
        if (BusyPeriods.get(i).getStime().before(twelvePM)) {
            System.out.println("Debug:");
            System.out.println("--------------");
            System.out.println("tp Stime index:" + i +" Start time:"+ BusyPeriods.get(i).getStime() + ": twelvePM:" + twelvePM);
            earlyPeriods.add(BusyPeriods.get(i));
        }
    }

    return earlyPeriods;
}
}
