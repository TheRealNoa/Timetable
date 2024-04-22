/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject5;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author noaca
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        model.setData("Hello MVC!");
        controller.init();
        Scene scene = new Scene(view, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MVC Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
