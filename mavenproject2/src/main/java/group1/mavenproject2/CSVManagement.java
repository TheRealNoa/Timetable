/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/**
 *
 * @author Noa
 */
public class CSVManagement {
    private static FileWriter writer;
    private static StringBuilder stringBuilder = new StringBuilder();
    public static void main(String[] args) {
  
  
  stringBuilder.append("Start date").append(",").append("End date").append(",").append("Day of week").append(",")
               .append("Start time").append(",").append("End time").append("\n");
  
  try ( FileWriter fileWriter = new FileWriter("C:\\temp\\csvMan.csv") ) {
   
   fileWriter.write(stringBuilder.toString());
   
  } catch(Exception ex) {
   ex.printStackTrace();
  }
 }
    public static void write(String command,String StartDate, String EndDate, String DayOfWeek, String StartTime, String EndTime)
    {
    String separator = ",";
    String[] data = {StartDate, EndDate,DayOfWeek,StartTime,EndTime} ;
    for (int i = 0; i < data.length; i++) {
            String s = data[i];
            stringBuilder.append(s);
            
            if (i < data.length - 1) {
                stringBuilder.append(", ");
            }else
            {
            stringBuilder.append("\n");
            }
        }
    try ( FileWriter fileWriter = new FileWriter("C:\\temp\\csvMan.csv") ) {
   fileWriter.write(stringBuilder.toString());
   
  } catch(Exception ex) {
   ex.printStackTrace();
  }
    }
    
}
