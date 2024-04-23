
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
    public synchronized void addModule(Module m)
    {
    this.modules.add(m);
    }
    public synchronized void removeModuleByClass(String c)
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
    public synchronized List<TimePeriod> getBusyPeriods() {
    return Collections.unmodifiableList(BusyPeriods);
}
    public synchronized String BusyPeriodsOut() {
    String temp = "";
    if(BusyPeriods.size()>0){
    for (int i=0;i<BusyPeriods.size();i++) {
        if(i<BusyPeriods.size()-1){
        temp += BusyPeriods.get(i) + "\n";
        }else
        {
        temp += BusyPeriods.get(i);
        }
    }
    }
    return temp;
}
    public synchronized String displayDay()
    {
    if(BusyPeriodsOut()!=""){
    return "Day: " + this.name + "\n" + "Schedule:" + "\n" + BusyPeriodsOut() ;
    }
    else{
    return "Day: " + this.name + ", no scheduled classes.";
    }
    }
    public synchronized void addTimeSlot(TimePeriod a, Module m){
    if(checkBookings(a))
    {
    System.out.println("Error, can't book.");
    }
    else
    {
    System.out.println("Added a booking");

    if(BusyPeriods!=null)
        {
        BusyPeriods.add(a);
        }
    ModuleTimePeriodEntry entry =  new ModuleTimePeriodEntry(m, a);
    if(combinedList!=null)
        {
        combinedList.add(entry);
        }    
    }
    }
    public synchronized boolean checkBookings(TimePeriod a)
    {
     if(BusyPeriods !=null){
        for(TimePeriod t:BusyPeriods)
     {
         if(t.clashesWith(a))
             return true;
     }
     }
      return false;
    }
    public synchronized void removeBooking(TimePeriod a)
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
     public synchronized void removeAllClassTimes(String className)
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
     
     public synchronized ArrayList<TimePeriod> getEarlyBookings() {
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
