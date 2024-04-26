package group1.mavenproject2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DatePickerApp extends Application {
    private Stage currentStage;
    private String selectedMonth;

    public DatePickerApp(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    @Override
    public void start(Stage primaryStage) {
        this.currentStage = primaryStage;
        MonthPickerView monthPicker = new MonthPickerView();
        try {
            monthPicker.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showSelectedDay(String month, int day) {
        if (currentStage != null) {
            currentStage.close();
        }

        Stage selectedDayStage = new Stage();
        Button selectedDayButton = new Button(month + ", " + day);
        selectedDayButton.setPrefSize(150, 100);
        selectedDayButton.setOnAction(e -> selectedDayStage.close());

        Scene scene = new Scene(selectedDayButton, 200, 100);

        selectedDayStage.setTitle("Selected Day");
        selectedDayStage.setScene(scene);
        selectedDayStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
