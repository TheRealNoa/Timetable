/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import static group1.mavenproject2.App.currentStage;
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
public class AppController {
    private AppModel model;

    public AppController(AppModel model) {
        this.model = model;
    }

    public void handleButtonAction(String label) {
        if (label.equals("Add Class")) {    
        openNewStage();
        } 
        else if (label.equals("Remove Class")) 
        {
        RemoveClassStage removeClassStage = new RemoveClassStage();
        try{
        removeClassStage.start(new Stage());
        }catch(Exception ex)
                {
                ex.printStackTrace();
                }
        }
        else if (label.equals("Display Timetable button"))
        {
            TCPEchoClient.sendMessage("TD");
        }
        else if (label.equals("Stop button"))
        {
        TCPEchoClient.sendMessage("STOP");
        }
        else if(label.equals("Move afternoon classes to mornings"))
        {
        TCPEchoClient.sendMessage("EarlyTimes");
        }
    }
   
    private void openNewStage() {
    if (currentStage != null) {
            currentStage.close();
    }    
    Stage newStage = new Stage();
    Button dateButton = new Button("Date");

    dateButton.setOnAction(e -> {
        MonthPickerView monthPicker = new MonthPickerView();
        try {
            monthPicker.start(new Stage());
            currentStage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    HBox newButtonBox = new HBox(10);
    newButtonBox.getChildren().addAll(dateButton);
    newButtonBox.setAlignment(Pos.CENTER);

    Rectangle newBox = new Rectangle(300, 100);
    newBox.setFill(Color.GREY);
    StackPane newStackPane = new StackPane(newBox, newButtonBox);

    Scene newScene = new Scene(newStackPane, 300, 100);
    newStage.setTitle("Additional Options");
    newStage.setScene(newScene);
    newStage.show();
    App.currentStage= newStage;
}
}

