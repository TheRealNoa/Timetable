package group1.mavenproject2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveClassStage extends Application {

    private Stage currentStage;

    @Override
    public void start(Stage primaryStage) {
        this.currentStage = primaryStage;

        // Create a "Classes here" button
        Button classesHereButton = new Button("Classes here");
        classesHereButton.setOnAction(e -> openClassRemovedStage());

        // Create the scene
        VBox layout = new VBox(10); // 10 is the spacing between items
        layout.getChildren().addAll(classesHereButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 200, 100); // Adjust dimensions as needed
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openClassRemovedStage() {
        if (currentStage != null) {
            currentStage.close();
        }

        Stage classRemovedStage = new Stage();
        Label classRemovedLabel = new Label("Class removed");
        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> classRemovedStage.close());

        VBox layout = new VBox(10); // 10 is the spacing between items
        layout.getChildren().addAll(classRemovedLabel, continueButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 200, 100); // Adjust dimensions as needed
        classRemovedStage.setScene(scene);
        classRemovedStage.show();

        this.currentStage = classRemovedStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
