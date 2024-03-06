package group1.mavenproject2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MonthPickerApp extends Application {
    private Stage currentStage;

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
        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

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

    Stage selectedMonthStage = new Stage();
    Button selectedMonthButton = createStyledButton(month);
    selectedMonthButton.setOnAction(e -> selectedMonthStage.close());

    // Create the scene
    Scene scene = new Scene(selectedMonthButton, 200, 100); // Adjust dimensions as needed

    // Set the scene and show the stage
    selectedMonthStage.setTitle("Selected Month");
    selectedMonthStage.setScene(scene);
    selectedMonthStage.show();

    this.currentStage = selectedMonthStage;
}

    public static void main(String[] args) {
        launch(args);
    }
}
