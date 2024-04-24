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
        Platform.runLater(() -> displayTimtable()); // gosh this gave me a headache
        
        //NOTE TO SELF:
        //Apparently if you try to call a method like displayTimetable() which executes something
        //on the javaFX thread, then you have to call that method from a javaFX thread as well...
    }
    }
    public void displayTimtable()
    {
    if (AppView.currentStage != null) {
            AppView.currentStage.close();
    }    
    Platform.runLater(() -> {
        TimetableView timetableView = new TimetableView();
        Stage stage = new Stage();
        timetableView.start(stage);
    });
}
}
