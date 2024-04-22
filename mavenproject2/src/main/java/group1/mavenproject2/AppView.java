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

    @Override
    public void start(Stage primaryStage) {
        model = new AppModel();
        controller = new AppController(model);

        Button addButton = createStyledButton("Add Class");
        // Create other buttons similarly

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton); // Add other buttons
        buttonBox.setAlignment(Pos.CENTER);
        addButton.setOnAction(e->{controller.handleButtonAction("Add Class");});
        Rectangle box = new Rectangle(800, 150);
        box.setFill(Color.GREY);
        StackPane stackPane = new StackPane(box, buttonBox);

        Scene scene = new Scene(stackPane, 800, 150);

        primaryStage.setTitle("Class Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String label) {
        Button button = new Button(label);
        button.setPrefSize(100, 100);
        button.setOnAction(event -> controller.handleButtonAction(label));
        return button;
    }
}

