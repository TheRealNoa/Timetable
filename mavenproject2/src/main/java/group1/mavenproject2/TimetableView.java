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
    private TimetableModel model;
    private TimetableController controller;
   @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        
        model = new TimetableModel(gridPane);
        controller = new TimetableController(model);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 0; i < 5; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setFont(Font.font(16));
            gridPane.add(dayLabel, i + 1, 0);
        }

        for (int i = 0; i < 10; i++) {
            int hour = 9 + i;
            Label timeLabel = new Label(hour + ":00");
            timeLabel.setFont(Font.font(16));
            gridPane.add(timeLabel, 0, i + 1);
        }

        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 5; col++) {
                Label cellLabel = new Label();
                cellLabel.setPrefSize(100, 50);
                cellLabel.setStyle("-fx-border-color: black");
                gridPane.add(cellLabel, col, row);
                controller.addLabel(cellLabel); // Add labels to the controller
            }
        }

        Scene scene = new Scene(gridPane, 620, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Timetable");
        primaryStage.show();
        
        // Start a background thread to simulate changes to the timetable
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Label l = new Label("a");
                    Thread.sleep(2000); // Simulate checking for changes every 2 seconds
                    Platform.runLater(() -> {
                        if (random.nextBoolean()) {
                            controller.addLabel(l); // Simulate adding a random label
                        } else {
                            controller.removeRandomLabel(); // Simulate removing a random label
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}