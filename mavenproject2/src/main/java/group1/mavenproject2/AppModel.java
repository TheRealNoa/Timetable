/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

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
public class AppModel {
    private int pageNumber;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public void displayTimeTable()
    {
    TCPEchoClient.sendMessage("TD");
    }
    public void openRemoveClassStage()
    {
    RemoveClassStage removeClassStage = new RemoveClassStage();
        try{
        removeClassStage.start(new Stage());
        }catch(Exception ex)
                {
                ex.printStackTrace();
                }
    }
    public void afternoonToMorning()
    {
    TCPEchoClient.sendMessage("EarlyTimes");
    }
    public void terminateConnection()
    {
    TCPEchoClient.sendMessage("STOP");
    }
    public void openDateStage() {
    if (AppView.currentStage != null) {
            AppView.currentStage.close();
    }    
    Stage newStage = new Stage();
    Button dateButton = new Button("Date");

    dateButton.setOnAction(e -> {
        MonthPickerView monthPicker = new MonthPickerView();
        try {
            monthPicker.start(new Stage());
            AppView.currentStage.close();
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
    AppView.currentStage= newStage;
}

}

