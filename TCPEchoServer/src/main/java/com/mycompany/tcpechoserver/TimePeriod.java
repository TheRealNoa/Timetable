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
    
     public boolean clashesWith(TimePeriod a)
      {
       if(this.Etime.before(a.getStime())|| this.Stime.after(a.getEtime()) || this.Etime.equals(a.getEtime()))
       {
       return false;
       }else
       {
       return true;
       }
      }
}
