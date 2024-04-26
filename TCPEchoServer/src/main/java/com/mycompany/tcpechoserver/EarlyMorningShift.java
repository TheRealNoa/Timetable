package com.mycompany.tcpechoserver;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class EarlyMorningShift {
    private static final Time TWELVE_PM = new Time(11 * 3600 * 1000);
    private static final Time ELEVEN = new Time(10 * 3600 * 1000);
    private static final Time NINE_AM = new Time(8 * 3600 * 1000);
    private static final Time HALF_HOUR = new Time(1800000);
    private static final Time HOUR = new Time(3600000);
    public static boolean breakIt = false;
    static List<TimePeriod> earlyPeriods;
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

        private void shiftClasses(Day d) {
            for (TimePeriod tp : d.getBusyPeriods()) {
                if (tp.getStime().after(TWELVE_PM)) {
                    System.out.println("Checking for a time after 12");
                    clashesWithEarlyTime(tp, d);
                }
            }
        }

    private void clashesWithEarlyTime(TimePeriod t, Day d) {
    // Get the list of early periods for the specified day
    breakIt = false;
    earlyPeriods = d.getEarlyBookings();
    System.out.println(earlyPeriods);
    long lenOfPeriodMillis = t.getEtime().getTime() - t.getStime().getTime();
    Time lenOfPeriod = new Time(lenOfPeriodMillis);
    Time ogS = t.getStime(); // Corrected method call
    Time ogE = t.getEtime(); // Corrected method call
    
    // Adjust start time to 9 AM
    
    t.Stime =NINE_AM;
    // Adjust end time accordingly
    t.Etime = (new Time(NINE_AM.getTime() + lenOfPeriod.getTime()));

    // Check if the adjusted start time is before 12 PM
    if (t.getStime().after(TWELVE_PM)) {
        System.out.println("Start time is after 12 PM. Cannot add before 12 PM.");
        return;
    }

    boolean going = true;
        while (going) {
            // Break loop if start time is after 11 AM
            if (t.getStime().after(ELEVEN)) {
                System.out.println("Start time is after 11 AM. Cannot proceed further.");
                System.out.println("OG S:" + ogS + "OG E: " + ogE);
                t.Stime = ogS;
                t.Etime = ogE;
                breakIt = true;
                going = false;
            } else if (clashesWith(t, earlyPeriods)) {
                // Adjust start time by an hour
                t.Stime = (new Time(t.getStime().getTime() + HOUR.getTime()));
                // Adjust end time accordingly
                t.Etime = (new Time(t.getStime().getTime() + lenOfPeriod.getTime()));
            } else {
                System.out.println("Added " + t);
                //earlyPeriods.add(t);
                going = false;
            
        }
    }
}

    
      private boolean clashesWith(TimePeriod t,List<TimePeriod> earlyPeriods)
    {
        boolean tempb=false;
        for(TimePeriod tp:earlyPeriods)
        {
        if(t.clashesWith(tp))
        {
        tempb=true;
        break;
        }
        }
    return tempb;
    }
}

}
