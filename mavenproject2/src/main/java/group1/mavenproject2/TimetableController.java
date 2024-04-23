/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import static group1.mavenproject2.TCPEchoClient.daysList;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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

    public void inputsToArrays()
    {
    System.out.println(Inputs);
    if(Inputs.get(0).startsWith("Fri"))
    {
    displayTimtable();
    }
    }
    public void displayTimtable()
    {
    if (AppView.currentStage != null) {
            AppView.currentStage.close();
    }    
    TimetableView tv = new TimetableView();
    Platform.runLater(() -> tv.start(new Stage()));
    }
}
