package com.mycompany.tcpechoserver;

import java.sql.Time;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class EarlyMorningShift {
    private static final Time TWELVE_PM = new Time(11 * 3600 * 1000);
    private static final Time NINE_AM = new Time(8 * 3600 * 1000);
    private static final Time HALF_HOUR = new Time(1800000);

    public static void main(String[] args) {
       System.out.println("Executing main from EarlyMorningShift");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ShiftClassesRecursion task = new ShiftClassesRecursion(TCPEchoServer.days, 0, TCPEchoServer.days.length);
        forkJoinPool.invoke(task);
    }

    static class ShiftClassesRecursion extends RecursiveAction {
        private Day[] days;
        private int start;
        private int end;

        public ShiftClassesRecursion(Day[] days, int start, int end) {
            this.days = days;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= 1) {
            shiftClasses(days[start]);
        } else {
            int mid = start + (end - start) / 2;
            ShiftClassesRecursion leftTask = new ShiftClassesRecursion(days, start, mid);
            ShiftClassesRecursion rightTask = new ShiftClassesRecursion(days, mid, end);
            
            leftTask.fork();
            rightTask.fork();
            
            leftTask.join();
            rightTask.join();
        }
            
        }

        private void shiftClasses(Day d) {//processes one day
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
        }
    } while (clashed && t.getStime().before(TWELVE_PM));
    System.out.println("Day bookings:");
    System.out.println("Day: " + d.name + ":" + d.getBusyPeriods());
        }
    }
}
