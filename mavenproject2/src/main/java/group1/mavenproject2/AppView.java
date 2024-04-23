/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

/**
 *
 * @author noaca
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AppView extends Application {
    private AppModel model;
    private AppController controller;
    public static Stage primaryStage;
    public static Stage currentStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        model = new AppModel();
        controller = new AppController(model);

        Button addButton = createStyledButton("Add Class");
        Button removeButton = createStyledButton("Remove class");
        Button displayTimetable = createStyledButton("Display timetable");
        Button stopButton = createStyledButton("Stop");
        Button earlyButton = createStyledButton("Move to mornings");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton,removeButton,displayTimetable,stopButton,earlyButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        Rectangle box = new Rectangle(800, 150);
        box.setFill(Color.GREY);
        StackPane stackPane = new StackPane(box, buttonBox);

        Scene scene = new Scene(stackPane, 800, 150);

        primaryStage.setTitle("Class Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Button Controlls
        addButton.setOnAction(e->{controller.handleButtonAction("Add Class");});
        removeButton.setOnAction(e->{controller.handleButtonAction("Remove Class");});
        displayTimetable.setOnAction(e->{controller.handleButtonAction("Display Timetable button");});
        stopButton.setOnAction(e->{controller.handleButtonAction("Stop button");});
        earlyButton.setOnAction(e->{controller.handleButtonAction("Move afternoon classes to mornings");});
    }

    private Button createStyledButton(String label) {
        Button button = new Button(label);
        button.setPrefSize(100, 100);
        button.setOnAction(event -> controller.handleButtonAction(label));
        return button;
    }
    
}

