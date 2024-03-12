/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tcpechoserver;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author noaca
 * 
 */public class Class
  {
      private String name;
      private Date Sdate;
      private Date Edate;
      private String day ="";
      private Day today;
      private Time Stime;
      private Time Etime;
      
      public Class(String name)
      {
      this.name = name;
      }
      
      public void selectDay(Day d)
      {
      this.today=d;
      }
      
      
      
      public void addSdate(Date Sdate)
      {
        this.Sdate=Sdate;
      }
      
      public String getDay()
      {
      return this.day;
      }
      
      public void addEdate(Date Edate)
      {
        this.Edate=Edate;
      }
      public Time getStime()
      {
          return this.Stime;
      }
      public Time getEtime()
      {
          return this.Etime;
      }
     
      public Class(String name, Date Sdate, Date Edate, int dayID, Time Stime, Time Etime)
      {
          this.name = name;
          this.Sdate = Sdate;
          this.Edate = Edate;
          //this.day = dayArr[dayID].toString();
          this.Stime = Stime;
          this.Etime = Etime;
      }
      
      
      
      @Override
      public String toString()
      {
          return this.name + ", " + this.Sdate + "-" + this.Edate ;
      }
      
  }
  
