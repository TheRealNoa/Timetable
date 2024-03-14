/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tcpechoserver;

import java.sql.Time;

/**
 *
 * @author noaca
 */
public class TimePeriod {
    public Time Stime;
    public Time Etime;
    public TimePeriod(){};
    public TimePeriod(Time Stime, Time Etime)
    {
        this.Stime=Stime;
        this.Etime=Etime;
    }
    public Time getStime()
    {
        return this.Stime;
    }
    public Time getEtime()
    {
        return this.Etime;
    }
    @Override
    public String toString()
    {
        return Stime + ":" + Etime;
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
