package com.mycompany.tcpechoserver;

import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 *
 * @author noaca
 */
public class TimePeriod {
    public Time Stime;
    public Time Etime;
    public String ModuleName;
    public String formattedTime;

    public TimePeriod() {
    };

    public TimePeriod(Time Stime, Time Etime, String ModuleName) {
        this.Stime = Stime;
        this.Etime = Etime;
        this.ModuleName = ModuleName;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formattedTime = formatter.format(Stime) + "-" + formatter.format(Etime);
    }

    public Time getStime() {
        return this.Stime;
    }

    public Time getEtime() {
        return this.Etime;
    }

    public void setSTime(Time tim) {
        this.Stime.setTime(tim.getTime());
    }

    public void setETime(Time time) {
        this.Etime.setTime(time.getTime());
    }

    public String getModuleName() {
        return this.ModuleName;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("HH:mm").format(Stime) + "-" + new SimpleDateFormat("HH:mm").format(Etime)
                + ", Module: " + ModuleName;
    }

    public boolean clashesWith(TimePeriod a) {
        if (a.getStime().equals(this.Etime)) {
            return false;
        } else if (this.Etime.before(a.getStime()) || this.Stime.after(a.getEtime())) {
            return false;
        } else if (this.Stime.equals(a.getEtime())) {
            return false;
        } else {
            return true;
        }
    }

}
