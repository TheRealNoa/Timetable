/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author noaca
 */
public class TimetableController {
    public static ArrayList<String> Inputs;
    private TimetableModel model;
    int timesChecked;
    public boolean running=true;
    public TimetableController(TimetableModel model) {
        this.model = model;
    }
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
    //Experimental
    public void addLabel(Label label, int col, int row) {
        model.addLabel(label,col,row);
    }

    public void removeLabel(Label label) {
        model.removeLabel(label);
    }

   public void checkLabelList() {
    new Thread(() -> {
        List<Label> currentLabels;
        while (running) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Label l = new Label("l");
            timesChecked++;
            List<Label> newLabels = model.getLabels();
            synchronized (model) {
                currentLabels = new ArrayList<>(model.getLabels()); 
            }
            if (!newLabels.equals(currentLabels)) {
                System.out.println("It changed");
                synchronized (model) {
                    currentLabels = newLabels;
                }
            }else
            {
            System.out.println("No change");
            }
        }
    }).start();
}
    public void stopThread()
    {
        running=false;
    }

    private void handleLabelListChanges() {
        System.out.println("Label list has changed.");
    }
}
