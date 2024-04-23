package com.mycompany.mavenproject7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the grid pane for the timetable
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
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
        
        cellLabel.setAlignment(Pos.CENTER);
        
        gridPane.add(cellLabel, col, row);
    }
}
        Scene scene = new Scene(gridPane, 620, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Timetable");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
