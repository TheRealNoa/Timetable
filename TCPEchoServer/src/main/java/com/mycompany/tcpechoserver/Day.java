
package com.mycompany.tcpechoserver;

import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author noaca
 */
public class Day {
    String name;
    ArrayList<TimePeriod> BusyPeriods = new ArrayList<TimePeriod>();
    private Time TempSTime;
    private Time TempETime;
    public Day(String name)
    {
    this.name=name;
    }
    public void addBookedTimeSlot(TimePeriod a)
    {
        //check here if start and end time clash
        for(TimePeriod t:BusyPeriods)
        {
           if(t.clashesWith(a))
           {
               System.out.println("Error, Times clash.");
           }else
            {
            this.BusyPeriods.add(a);
            }
        }
    }
    
}
