/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    public String Class;
    public String formattedTime;
    public TimePeriod(){};
    public TimePeriod(Time Stime, Time Etime)
    {
        this.Stime=Stime;
        this.Etime=Etime;
        this.Class=Class;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formattedTime = formatter.format(Stime) + "-" + formatter.format(Etime);
    }
    public TimePeriod(Time Stime, Time Etime, String Class)
    {
        this.Stime=Stime;
        this.Etime=Etime;
        this.Class=Class;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formattedTime = formatter.format(Stime) + "-" + formatter.format(Etime);
    }
    public Time getStime()
    {
        return this.Stime;
    }
    public Time getEtime()
    {
        return this.Etime;
    }
    public void setSTime(Time t)
    {
    this.Stime=t;
    }
    public void setETime(Time t)
    {
    this.Etime=t;
    }
    public String getClassName()
    {
    return this.Class;
    }
    @Override
    public String toString()
    {
        return formattedTime + ", Class: " + this.Class;
    }
     public boolean clashesWith(TimePeriod a)
      {
          if(a.getStime().equals(this.Etime))
          {
          return false;
          }else if(this.Etime.before(a.getStime())|| this.Stime.after(a.getEtime()))
       {
       return false;
       }else if(this.Stime.equals(a.getEtime()))
       {
       return false;
       }else
       {
           System.out.println("Clash");
           System.out.println("------------------------");
           System.out.println("Clashing times:");
           System.out.println(this.Stime + "-" + this.Etime);
           System.out.println(a.getStime() + "-" + a.getEtime());
       return true;
       }
      }
}
