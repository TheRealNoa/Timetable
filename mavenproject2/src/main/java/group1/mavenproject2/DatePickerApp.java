package group1.mavenproject2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.Month;
import java.time.YearMonth;

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
        // Do not call the getSelectedMonth () method here
        // selectedMonth = monthPicker.getSelectedMonth();
        // openDayPickerStage(selectedMonth);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    // Open a new stage for day selection
    private void openDayPickerStage(String month) {
        if (currentStage != null) {
            currentStage.close();
        }

        Stage dayPickerStage = new Stage();
        GridPane dayButtons = createDayButtons(month);

        Scene scene = new Scene(dayButtons, 300, 100); 

        dayPickerStage.setTitle("Select a Day");
        dayPickerStage.setScene(scene);
        dayPickerStage.centerOnScreen(); 
        dayPickerStage.show();

        this.currentStage = dayPickerStage;
    }

    private GridPane createDayButtons(String month) {
        GridPane dayButtons = new GridPane();
        dayButtons.setHgap(10); 
        dayButtons.setVgap(10); 

        final int monthNumber = Month.valueOf(month.toUpperCase()).getValue();
        YearMonth yearMonthObject = YearMonth.of(2024, monthNumber);  // 2024 is a leap year
        int daysInMonth = yearMonthObject.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            Button button = new Button(String.valueOf(day));
            button.setPrefSize(50, 50);
            final int finalDay = day;
            button.setOnAction(e -> showSelectedDay(month, finalDay));
            dayButtons.add(button, (day-1) % 7, (day-1) / 7);
        }

        return dayButtons;
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
