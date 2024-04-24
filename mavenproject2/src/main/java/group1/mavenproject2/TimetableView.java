/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

/**
 *
 * @author noaca
 */
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TimetableView extends Application {
    Stage primaryStage;
    public TimetableModel model;
    public static TimetableController controller;
    private boolean addedCells = false;
    public  static boolean stopModel = false;
    public static String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    public static String[] times = {"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00"};
   @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < 5; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setFont(Font.font(16));
            gridPane.add(dayLabel, i + 1, 0);
        }

        for (int i = 0; i < 10; i++) {
            int hour = 9 + i;
            Label timeLabel = new Label(times[i]);
            timeLabel.setFont(Font.font(16));
            gridPane.add(timeLabel, 0, i + 1);
        }
        model = new TimetableModel(gridPane);
        controller = new TimetableController(model,gridPane);
        controller.model = model;
        System.out.println("Model from view from controller:" + controller.model);
        //controller.processInputs();
        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 5; col++) {
                Label cellLabel = new Label();
                cellLabel.setPrefSize(100, 50);
                cellLabel.setStyle("-fx-border-color: black");
                gridPane.add(cellLabel, col, row);
                controller.addLabel(cellLabel, col, row);
            }addedCells=true;
        }
        
        
            controller.checkLabelList();

        Scene scene = new Scene(gridPane, 620, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Timetable");
        primaryStage.show();
        primaryStage.setOnHidden(e -> {
            controller.stopThread();
    // This just stops the thread in the controller once this thread is exited
    // I tried just calling controller.stopThread() after launch but it didn't work
    // so this is the adjusted solution.
    });
        
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}