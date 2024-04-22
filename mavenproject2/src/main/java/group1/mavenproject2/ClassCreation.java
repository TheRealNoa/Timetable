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
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClassCreation extends Application {
     public String ClassName;
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Enter class name:");
        TextField tf = new TextField();
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                ClassName=newValue;
            }
        });
        Button ok = new Button("ok");
        ok.setOnAction(e->
        {
        TCPEchoClient.sendMessage("NewClass," + ClassName);
        primaryStage.close();
        App.currentStage=App.primaryStage;
        App.currentStage.show();
        MonthPickerView.classes.add(ClassName);
        });
        VBox root = new VBox(10);
        root.getChildren().addAll(label,tf,ok);

        Scene scene = new Scene(root, 200, 100);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Remove Class");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

