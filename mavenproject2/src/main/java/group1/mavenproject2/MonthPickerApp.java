package group1.mavenproject2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class MonthPickerApp extends Application {
    private Stage currentStage;

    // Declare the variables for the selected month and day
    private String selectedMonth1;
    private String selectedMonth2;
    private int selectedDay1;
    private int selectedDay2;

    // Add a list of days in each month
    private List<Integer> daysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

    // Create an array of months
    private String[] months = {
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    @Override
    public void start(Stage primaryStage) {
        this.currentStage = primaryStage;
        Button monthButton = createStyledButton("Choose Month");
        monthButton.setOnAction(e -> openMonthPickerStage());

        // Arrange the button
        VBox buttonBox = new VBox();
        buttonBox.getChildren().add(monthButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(buttonBox, 400, 150); // Adjust dimensions as needed

        // Set the scene and show the stage
        primaryStage.setTitle("Month Picker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create a styled button
    private Button createStyledButton(String label) {
        Button button = new Button(label);
        button.setPrefSize(150, 100);
        return button;
    }

    // Open a new stage for month selection
    private void openMonthPickerStage() {
        if (currentStage != null) {
            currentStage.close();
        }

        Stage monthPickerStage = new Stage();
        GridPane monthButtons = createMonthButtons();

        // Create the scene
        Scene scene = new Scene(monthButtons, 300, 100); // Adjust dimensions as needed

        // Set the scene and show the stage
        monthPickerStage.setTitle("Select a Month");
        monthPickerStage.setScene(scene);
        monthPickerStage.centerOnScreen(); // This will center the stage
        monthPickerStage.show();

        this.currentStage = monthPickerStage;
    }

    // Create buttons for each month
    private GridPane createMonthButtons() {
        GridPane monthButtons = new GridPane();
        monthButtons.setHgap(10); // Horizontal spacing
        monthButtons.setVgap(10); // Vertical spacing

        int rowIndex = 0;
        int colIndex = 0;
        for (String month : months) {
            Button button = createStyledButton(month);
            button.setOnAction(e -> showSelectedMonth(month));
            monthButtons.add(button, colIndex, rowIndex);

            colIndex++;
            if (colIndex > 3) {
                colIndex = 0;
                rowIndex++;
            }
        }

        return monthButtons;
    }

    // Show the selected month in a new stage
    private void showSelectedMonth(String month) {
        if (currentStage != null) {
            currentStage.close();
        }

        Stage dayPickerStage = new Stage();
        GridPane dayButtons = createDayButtons(month);

        // Create the scene
        Scene scene = new Scene(dayButtons, 400, 200); // Adjust dimensions as needed

        // Set the scene and show the stage
        dayPickerStage.setTitle("Select a Day");
        dayPickerStage.setScene(scene);
        dayPickerStage.show();

        this.currentStage = dayPickerStage;
    }

    // Create buttons for each day in the selected month
    private GridPane createDayButtons(String month) {
        GridPane dayButtons = new GridPane();
        dayButtons.setHgap(10); // Horizontal spacing
        dayButtons.setVgap(10); // Vertical spacing

        // Get the index of the selected month
        int monthIndex = Arrays.asList(months).indexOf(month);

        // Get the number of days in the selected month
        int days = daysInMonth.get(monthIndex);

        // Adjust for leap years if the month is February
        if (month.equals("February")) {
            // Get the current year from the system
            int year = Calendar.getInstance().get(Calendar.YEAR);

            // Check if the year is a leap year
            if (isLeapYear(year)) {
                // Add one more day
                days++;
            }
        }

        // Create an array of suffixes for the days
        String[] suffixes = {"st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th",
                "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                "st", "nd", "rd", "th", "th", "th", "th", "th", "th", "th",
                "st"};

        int rowIndex = 0;
        int colIndex = 0;
        for (int day = 1; day <= days; day++) {
            // Create a button with the day and the suffix
            String label = day + suffixes[day - 1];
            Button button = createStyledButton(label);

            // Create an instance of the DayButtonHandler class and pass it to the setOnAction method
            button.setOnAction(new DayButtonHandler(month, day));

            dayButtons.add(button, colIndex, rowIndex);

            colIndex++;
            if (colIndex > 7) {
                colIndex = 0;
                rowIndex++;
            }
        }

        return dayButtons;
    }

    // Show the selected day in a new stage
   // Show the selected day in a new stage
private void showSelectedDay(String month, int day) {
    Stage selectedDayStage = new Stage();
    if (currentStage != null) {
        currentStage.close();
    }

    // Check if the first selection has been made
    if (selectedMonth1 == null && selectedDay1 == 0) {
        // Store the selected month and day as the first selection
        selectedMonth1 = month;
        selectedDay1 = day;
    } else {
        // Store the selected month and day as the second selection
        selectedMonth2 = month;
        selectedDay2 = day;
    }

    // Create a button with the selected month and day
    VBox vb = new VBox(10);
    vb.setAlignment(Pos.CENTER);
    
    
    Label selectionInfo = new Label("You have selected:" + month + " " + day);
    Button selectedDayButton = createStyledButton("Continue");
    selectedDayButton.setAlignment(Pos.CENTER);

    // Modify the selectedDayButton.setOnAction method to check if the second selection has been made
    selectedDayButton.setOnAction(e -> {
        // Check if the second selection has been made
        if (selectedMonth2 == null && selectedDay2 == 0) {
            // If not, then open a new stage for month selection
            openMonthPickerStage();
        } else {
            // If yes, then close the stage and display the final result
            selectedDayStage.close();
            displayFinalResult();
        }
    });
    
    vb.getChildren().addAll(selectionInfo,selectedDayButton);

    // Create the scene
    Scene scene = new Scene(vb, 200, 100); // Adjust dimensions as needed

    // Declare the selectedDayStage variable as a local Stage object

    // Set the scene and show the stage
    selectedDayStage.setTitle("Selected Day");
    selectedDayStage.setScene(scene);
    selectedDayStage.show();

    this.currentStage = selectedDayStage;
}

    // Create a class that implements the EventHandler interface
    class DayButtonHandler implements EventHandler<ActionEvent> {
        // Declare the fields for the month and the day
        private String month;
        private int day;

        // Create a constructor that takes the month and the day as parameters
        public DayButtonHandler(String month, int day) {
            this.month = month;
            this.day = day;
        }

        // Override the handle method to call the showSelectedDay method
        @Override
        public void handle(ActionEvent event) {
            showSelectedDay(month, day);
        }
    }

    // Add a method to check if a year is a leap year
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
   private String[] daysOfWeek = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
    private void displayDayOfWeek() {
        if (currentStage != null) {
        currentStage.close();
    }
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        
        Label infoLabel = new Label("Please select a day of the week:");
        
        Stage daysStage = new Stage();
        GridPane daysButtons = new GridPane();
        for (int i = 0; i < daysOfWeek.length; i++) {
            Button button = new Button(daysOfWeek[i]);
            button.setOnAction(e -> {
                System.out.println("Clicked on: " + daysOfWeek[daysButtons.getChildren().indexOf(button)]);
                daysStage.close(); // Close the window after clicking a button
            });
            daysButtons.add(button, i, 0);
        }
        daysButtons.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(infoLabel,daysButtons);
        Scene scene = new Scene(vb, 350, 50);
        daysStage.setScene(scene);
        daysStage.setTitle("Days of Week");
        daysStage.show();
    }
    
    private void displayFinalResult() {
    // Create a button with the two selections
    Label InfoLabel = new Label("You have selected the period from:"+selectedMonth1 + " " + selectedDay1 + " to " + selectedMonth2 + " " + selectedDay2);
    Button finalResultButton = createStyledButton("Next");
    finalResultButton.setOnAction(e -> displayDayOfWeek()); // Exit the program when the button is clicked

    VBox vb = new VBox(10);
    vb.setAlignment(Pos.CENTER);
    vb.getChildren().addAll(InfoLabel,finalResultButton);
    // Create the scene
    Scene scene = new Scene(vb, 400, 100); // Adjust dimensions as needed

    // Set the scene and show the stage
    Stage finalResultStage = new Stage();
    finalResultStage.setTitle("Final Result");
    finalResultStage.setScene(scene);
    finalResultStage.show();

    this.currentStage = finalResultStage;
}

    public static void main(String[] args) {
        launch(args);
    }
}
