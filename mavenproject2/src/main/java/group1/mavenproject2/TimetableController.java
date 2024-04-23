/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import java.util.ArrayList;

/**
 *
 * @author noaca
 */
public class TimetableController {
    public static ArrayList<String> Inputs;
    
    TimetableController(ArrayList<String> s)
    {
    this.Inputs = s;
    }

    public void inputsToUI()
    {
    System.out.println(Inputs);
    }
}
