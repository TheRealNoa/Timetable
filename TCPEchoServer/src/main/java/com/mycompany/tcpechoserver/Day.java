
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
    public ArrayList<TimePeriod> earlyPeriods;

    public Day() {
    }

    public Day(String name) {
        this.name = name;
        BusyPeriods = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public synchronized void addModule(Module m) {
        this.modules.add(m);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public synchronized ArrayList<TimePeriod> getBusyPeriods() {
        return BusyPeriods;
    }

    public synchronized String BusyPeriodsOut() {
        String temp = "";
        if (BusyPeriods.size() > 0) {
            for (int i = 0; i < BusyPeriods.size(); i++) {
                if (i < BusyPeriods.size() - 1) {
                    temp += BusyPeriods.get(i) + ",";
                } else {
                    temp += BusyPeriods.get(i);
                }
            }
        }
        return temp;
    }

    public synchronized String displayDay() {
        if (BusyPeriodsOut() != "") {
            return "Day," + this.name + "," + BusyPeriodsOut();
        } else {
            return "Day," + this.name + ",No scheduled classes,";
        }
    }

    public synchronized String addTimeSlot(TimePeriod a) {
        String tempS;
        if (checkBookings(a)) {
            tempS = ("Error, can't book.");
        } else {
            tempS = ("Added a booking");

            if (BusyPeriods != null) {
                BusyPeriods.add(a);
            }
        }
        return tempS;
    }

    public synchronized boolean checkBookings(TimePeriod a) {
        if (BusyPeriods != null) {
            for (TimePeriod t : BusyPeriods) {
                System.out.println("Checking for class:" + a.ModuleName);
                if (t.clashesWith(a))
                    return true;
            }
        }
        return false;
    }

    public synchronized void removeBooking(TimePeriod a) {
        for (int i = 0; i < BusyPeriods.size(); i++) {
            TimePeriod t = BusyPeriods.get(i);
            if (t.clashesWith(a)) {
                BusyPeriods.remove(t);
                System.out.println("Removed class:" + t);
            }
        }
    }

    public synchronized void removeAllClassTimes(String className) {
        for (TimePeriod t : BusyPeriods) {
            if (t.getClass().toString().contains(className)) {
                System.out.println("Contains");
                BusyPeriods.remove(t);
            }
        }
    }

    public synchronized ArrayList<TimePeriod> getEarlyBookings() {
        ArrayList<TimePeriod> earlyPeriods = new ArrayList<>();
        Time twelvePM = new Time(11 * 3600 * 1000);

        for (int i = 0; i < BusyPeriods.size() - 1; i++) {
            if (BusyPeriods.get(i).getStime().before(twelvePM)) {
                System.out.println("Debug:");
                System.out.println("--------------");
                System.out.println("tp Stime index:" + i + " Start time:" + BusyPeriods.get(i).getStime()
                        + ": twelvePM:" + twelvePM);
                earlyPeriods.add(BusyPeriods.get(i));
            }
        }

        return earlyPeriods;
    }
}
