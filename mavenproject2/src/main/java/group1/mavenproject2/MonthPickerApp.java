package group1.mavenproject2;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class MonthPickerApp extends Application {
    public static Stage currentStage;

    // Declare the variables for the selected month and day
    private static String selectedMonth1;
    private static String selectedMonth2;
    private static int selectedDay1;
    private static int selectedDay2;
    

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
    public static String DayOfWeek = "";
    public static Stage daysStage;
   public static String[] daysOfWeek = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
   static Stage removeClassStage;
   public static void removeClassEntirely(Stage removeClassStage) {
       VBox root = new VBox();
       MonthPickerApp.removeClassStage = removeClassStage; 
       Button removeClass = new Button("Remove Class");
        removeClass.setOnAction(e->
        {
        });
        for(String s:classes)
        {
        Button classButton = new Button(s);
        root.getChildren().add(classButton);
        classButton.setOnAction(e->
        {
            classSelected = classButton.getText();
        TCPEchoClient.sendMessage("DeleteClasses," + classSelected);
        });
        }
        
        root.getChildren().add(removeClass);

        Scene scene = new Scene(root, 200, 100);
        removeClassStage = new Stage();
        removeClassStage.setScene(scene);
        removeClassStage.setTitle("Remove Class");
        removeClassStage.show();
    }
   public void displayDayOfWeek() {
        if (currentStage != null) {
        currentStage.close();
    }
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        
        Label infoLabel = new Label("Please select a day of the week: ");
        
        this.daysStage = new Stage();
        GridPane daysButtons = new GridPane();
        for (int i = 0; i < daysOfWeek.length; i++) {
            Button button = new Button(daysOfWeek[i]);
            button.setOnAction(e -> {
                // I need to now insert time select...
                System.out.println("Clicked on: " + daysOfWeek[daysButtons.getChildren().indexOf(button)]);
                DayOfWeek = daysOfWeek[daysButtons.getChildren().indexOf(button)];
                displayModuleSelection();
                daysStage.close(); // Close the window after clicking a button
            });
            daysButtons.add(button, i, 0);
        }
        daysButtons.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(infoLabel,daysButtons);
        Scene scene = new Scene(vb, 350, 150);
        daysStage.setScene(scene);
        daysStage.setTitle("Days of Week");
        daysStage.show();
    }
   static double upStartTime=9;
   static String StartTime = "";
   static String EndTime = "";
   static boolean empty=true;
   static String ClassName = "";
   private void check()
   {
   if(empty)
   {
   Alert a = new Alert(Alert.AlertType.ERROR);
   a.setContentText("Please type in the class name.");
   a.show();
   }else
   {
   displayClassSelectionMsg();
   currentStage.close();
   //displayTimeSelection();
   }
   }
   static Stage showDayScheduleScene=new Stage();
   private void displayClassSelectionMsg()
   {
   TCPEchoClient.sendMessage("RCL");
   
   }
   public boolean isOpen;
   public static String classSelected="";
   public static ArrayList<String> classes= new ArrayList<>();;
    public static void showClasses(ArrayList<String> classList) {
        if (classList == null || classList.isEmpty()) {
            System.out.println("No schedule received.");
            return;
        }
        MonthPickerApp m = new MonthPickerApp();
        VBox vb = new VBox(10);
        for(int i=1;i<classList.size();i++)
        {
            String s = classList.get(i);
            classes.add(s);
        Button b = new Button(s);
        vb.getChildren().add(b);
        b.setOnAction(e->
        {
        classSelected=b.getText();
        currentStage.close();
        displayTimeSelection();
        });
        }
        Scene timeSelectionScene = new Scene(vb,300,200);
  
        showDayScheduleScene.setTitle("Class selection");
        showDayScheduleScene.setScene(timeSelectionScene);
        showDayScheduleScene.show();
        currentStage = showDayScheduleScene;
        
    }
   private void displayModuleSelection()
   {
   Label selectClass = new Label("Please enter module name:");
   TextField tf = new TextField();
   tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                empty = false;
                ClassName=newValue;
            } else {
                empty=true;
            }
        });
   VBox vb = new VBox();
   Button ok = new Button("Ok");
   vb.setAlignment(Pos.CENTER);
   vb.getChildren().addAll(selectClass,tf,ok);
   ok.setOnAction(e->
           check()
        );
   Scene timeSelectionScene = new Scene(vb,300,300);
  
        Stage finalResultStage = new Stage();
        finalResultStage.setTitle("Class selection");
        finalResultStage.setScene(timeSelectionScene);
        finalResultStage.show();
        this.currentStage = finalResultStage;
   
   } 
   public static void displayTimeSelection()
    {
        Label InfoLabel = new Label("Select the Start time and end time");
        Label StartTimeLabel = new Label("Start time: ");
        Label EndTimeLabel = new Label("End time: ");
    //Two inputs:
    //Start time: 
        //Note: Even though it's time, the values outputed are
        // still regular numbers, so e.g. 9,9.5,10,10.5 ...
        Slider startSlider = new Slider(9,18,1);
        StartTime = "9";
        startSlider.setBlockIncrement(1);
        startSlider.setMinorTickCount(1);
        startSlider.setMajorTickUnit(1);
        startSlider.setSnapToTicks(true);
        startSlider.setShowTickLabels(true);
        startSlider.setShowTickMarks(true);
        
    //End time:
        Slider endSlider = new Slider(upStartTime+0.5,18,1);
        EndTime = upStartTime + "";
        endSlider.setBlockIncrement(1);
        endSlider.setMinorTickCount(1);
        endSlider.setMajorTickUnit(1);
        endSlider.setSnapToTicks(true);
        endSlider.setShowTickLabels(true);
        endSlider.setShowTickMarks(true);
        
        endSlider.setLabelFormatter(new javafx.util.StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                if (Math.abs(value - Math.round(value)) == 0.5) {
                    return String.format("%.0f:30", Math.floor(value));
                } else {
                    return String.format("%.0f", value);
                }
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        
        //some fancy code for the end time slider :)
        //Basically, whatever we select as the start time, the first value of
        //end time is going to change accordingly.
        //I think that'll save some hassle down the line
        
        startSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) 
            {
                if(new_val.doubleValue()%0.5==0)
                // this is just to avoid all the actual values that the slider "outputs"
                    System.out.println(new_val.doubleValue());
                upStartTime=new_val.doubleValue()+0.5;
                endSlider.setMin(upStartTime);
            }
        });
        
        
        Button okButton = new Button("ok");
        startSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                StartTime = newValue.doubleValue() + "";
            }
        });
        endSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                EndTime = newValue.doubleValue() + "";
            
            }
        });

        String StartDate = selectedMonth1 + " " + selectedDay1;
        String EndDate = selectedMonth2 + " " + selectedDay2;
        okButton.setOnAction(e->
        {
            TCPEchoClient.sendClientData(StartDate , EndDate, DayOfWeek, StartTime, EndTime, ClassName, classSelected);
            
        }
        );
        
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(InfoLabel,StartTimeLabel,startSlider,EndTimeLabel,endSlider,okButton);
        Scene timeSelectionScene = new Scene(vb,600,200);
        
        Stage timeSelectionStage = new Stage();
        timeSelectionStage.setTitle("Time selection");
        timeSelectionStage.setScene(timeSelectionScene);
        timeSelectionStage.show();
        
        currentStage = timeSelectionStage;
    }
    
    private void displayFinalResult() {
    // Create a button with the two selections
    Label InfoLabel = new Label("You have selected the period from: "+selectedMonth1 + " " + selectedDay1 + " to " + selectedMonth2 + " " + selectedDay2);
    Button finalResultButton = createStyledButton("Next");
    finalResultButton.setOnAction(e -> displayDayOfWeek());

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
