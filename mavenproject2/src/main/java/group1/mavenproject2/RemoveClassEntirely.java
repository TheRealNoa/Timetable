/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

/**
 *
 * @author noaca
 */
import static group1.mavenproject2.MonthPickerApp.showDayScheduleScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RemoveClassEntirely extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button removeClass = new Button("Remove Class");
        removeClass.setOnAction(e->
        {
        });
        StackPane root = new StackPane();
        root.getChildren().add(removeClass);

        Scene scene = new Scene(root, 200, 100);
       
        primaryStage.setScene(scene);
        primaryStage.setTitle("Remove Class");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

