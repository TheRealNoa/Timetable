/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tcpechoserver;
import java.sql.Time;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class EarlyMorningShift {
    private static final Time TWELVE_PM = new Time(12 * 3600 * 1000);
    private static final Time NINE_AM = new Time(8 * 3600 * 1000);
    private static final Time HALF_HOUR = new Time(1800000);

    public static void main(String[] args) {
        System.out.println("Executing main from EarlyMorningShift");
        ForkJoinPool forkJoinPool = new ForkJoinPool(TCPEchoServer.days.length);
        for (Day day : TCPEchoServer.days) {
            forkJoinPool.submit(new ShiftClassesRecursion(day));
        }
        forkJoinPool.shutdown();
    }

    static class ShiftClassesRecursion extends RecursiveAction {
        private Day day;

        public ShiftClassesRecursion(Day day) {
            this.day = day;
        }

        @Override
        protected void compute() {
            System.out.println("Computing");
            shiftClasses(day);
        }

        private void shiftClasses(Day d) {
            for (TimePeriod tp : d.getBusyPeriods()) {
                if (tp.getStime().after(TWELVE_PM)) {
                    System.out.println("Checking for a time after 12");
                    clashesWithEarlyTime(tp, d);
                }
            }
        }

        private void clashesWithEarlyTime(TimePeriod t, Day d) {
    long lenOfPeriodMillis = t.getEtime().getTime() - t.getStime().getTime();
    Time lenOfPeriod = new Time(lenOfPeriodMillis);
    t.setETime(new Time(NINE_AM.getTime() + lenOfPeriodMillis));
    t.setSTime(NINE_AM);
    System.out.println("Early bookings:" + d.getEarlyBookings().toString());
    boolean clashed;
    do {
        clashed = false;
        for (TimePeriod tp : d.getEarlyBookings()) {
            if (t.clashesWith(tp)) {
                t.setSTime(new Time(t.getStime().getTime() + HALF_HOUR.getTime()));
                t.setETime(new Time(t.getStime().getTime() + lenOfPeriodMillis));
                clashed = true;
                System.out.println("Clashed");
                break;
            }
            System.out.println("Didn't clash");
        }
    } while (clashed && t.getStime().before(TWELVE_PM));
        }
    }
}
