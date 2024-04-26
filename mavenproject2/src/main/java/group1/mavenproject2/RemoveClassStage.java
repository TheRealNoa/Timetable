package group1.mavenproject2;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveClassStage extends Application {

    private Stage currentStage;
    private static String DayOfWeek = "";

    public void sendDaySchedules() {
        TCPEchoClient.sendMessage("ShowDaySchedule," + DayOfWeek);
        System.out.println("Sent day schedule");
    }

    public static void showDaySchedule(ArrayList<String> daySchedules) {
        if (daySchedules == null || daySchedules.isEmpty()) {
            System.out.println("No schedule received.");
            return;
        }

        Stage scheduleStage = new Stage();
        VBox scheduleLayout = new VBox(10);
        scheduleLayout.setAlignment(Pos.CENTER);
        System.out.println(daySchedules);
        for (int i = 0; i < daySchedules.size(); i += 2) {
            String schedule1 = daySchedules.get(i);
            String schedule2 = (i + 1 < daySchedules.size()) ? daySchedules.get(i + 1) : "";
            String buttonText = schedule1 + (schedule2.isEmpty() ? "" : ", " + schedule2);

            Button button = new Button(buttonText);
            button.setOnAction(e -> {
                System.out.println("Button clicked: " + buttonText);
                System.out.println("Day of week:" + DayOfWeek);
                TCPEchoClient.sendMessage("RemClassMsG," + schedule1 + "," + DayOfWeek);
                scheduleStage.close();
            });
            scheduleLayout.getChildren().add(button);

        }

        Scene scene = new Scene(scheduleLayout, 400, 300);
        scheduleStage.setScene(scene);
        scheduleStage.setTitle("Day's Schedule");
        scheduleStage.show();
    }

    public void openDays() {
        if (currentStage != null) {
            currentStage.close();
        }

        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);

        Label infoLabel = new Label("Please select a day of the week: ");

        Stage daysStage = new Stage();
        GridPane daysButtons = new GridPane();

        for (int i = 0; i < MonthPickerView.daysOfWeek.length; i++) {
            String selectedDay = MonthPickerView.daysOfWeek[i];
            Button button = new Button(selectedDay);

            button.setOnAction(e -> {
                DayOfWeek = selectedDay;
                System.out.println("Day selected is: " + DayOfWeek);
                sendDaySchedules();
                daysStage.close();
            });

            daysButtons.add(button, i, 0);
        }

        daysButtons.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(infoLabel, daysButtons);
        Scene scene = new Scene(vb, 350, 50);
        daysStage.setScene(scene);
        daysStage.setTitle("Days of Week");
        daysStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        this.currentStage = primaryStage;

        Button classesHereButton = new Button("Classes here");
        classesHereButton.setOnAction(e -> {
            openDays();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(classesHereButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 200, 100);
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

        VBox layout = new VBox(10);
        layout.getChildren().addAll(classRemovedLabel, continueButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 200, 100);
        classRemovedStage.setScene(scene);
        classRemovedStage.show();

        this.currentStage = classRemovedStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}