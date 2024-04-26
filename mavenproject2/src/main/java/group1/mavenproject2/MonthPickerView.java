package group1.mavenproject2;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class MonthPickerView extends Application {
    public static Stage currentStage;

    @Override
    public void start(Stage primaryStage) {
        displayDayOfWeek();
    }

    private Button createStyledButton(String label) {
        Button button = new Button(label);
        button.setPrefSize(150, 100);
        return button;
    }

    public static String DayOfWeek = "";
    public static Stage daysStage;
    public static String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
    static Stage removeClassStage;

    public static void removeClassEntirely(Stage removeClassStage) {
        VBox root = new VBox();
        MonthPickerView.removeClassStage = removeClassStage;
        Button removeClass = new Button("Remove Class");
        removeClass.setOnAction(e -> {
        });
        for (String s : classes) {
            Button classButton = new Button(s);
            root.getChildren().add(classButton);
            classButton.setOnAction(e -> {
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
                System.out.println("Clicked on: " + daysOfWeek[daysButtons.getChildren().indexOf(button)]);
                DayOfWeek = daysOfWeek[daysButtons.getChildren().indexOf(button)];
                displayModuleSelection();
                daysStage.close();
            });
            daysButtons.add(button, i, 0);
        }
        daysButtons.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(infoLabel, daysButtons);
        Scene scene = new Scene(vb, 350, 150);
        daysStage.setScene(scene);
        daysStage.setTitle("Days of Week");
        daysStage.show();
    }

    static double upStartTime = 9;
    static String StartTime = "";
    static String EndTime = "";
    static boolean empty = true;
    static String ClassName = "";

    private void check() {
        if (empty) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please type in the class name.");
            a.show();
        } else {
            displayClassSelectionMsg();
            currentStage.close();
        }
    }

    static Stage showDayScheduleScene = new Stage();

    private void displayClassSelectionMsg() {
        TCPEchoClient.sendMessage("RCL");

    }

    public boolean isOpen;
    public static String classSelected = "";
    public static ArrayList<String> classes = new ArrayList<>();;

    public static void showClasses(ArrayList<String> classList) {
        if (classList == null || classList.isEmpty()) {
            System.out.println("No schedule received.");
            return;
        }
        MonthPickerView m = new MonthPickerView();
        VBox vb = new VBox(10);
        for (int i = 1; i < classList.size(); i++) {
            String s = classList.get(i);
            classes.add(s);
            Button b = new Button(s);
            vb.getChildren().add(b);
            classSelected = b.getText();
            currentStage.close();
            displayTimeSelection();

        }

    }

    private void displayModuleSelection() {
        Label selectClass = new Label("Please enter module name:");
        TextField tf = new TextField();
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                empty = false;
                ClassName = newValue;
            } else {
                empty = true;
            }
        });
        VBox vb = new VBox();
        Button ok = new Button("Ok");
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(selectClass, tf, ok);
        ok.setOnAction(e -> check());
        Scene timeSelectionScene = new Scene(vb, 300, 300);

        Stage finalResultStage = new Stage();
        finalResultStage.setTitle("Class selection");
        finalResultStage.setScene(timeSelectionScene);
        finalResultStage.show();
        this.currentStage = finalResultStage;

    }

    public static void displayTimeSelection() {
        Label InfoLabel = new Label("Select the Start time and end time");
        Label StartTimeLabel = new Label("Start time: ");
        Label EndTimeLabel = new Label("End time: ");

        Slider startSlider = new Slider(9, 18, 1);
        StartTime = "9";
        startSlider.setBlockIncrement(1);
        startSlider.setMinorTickCount(1);
        startSlider.setMajorTickUnit(1);
        startSlider.setSnapToTicks(true);
        startSlider.setShowTickLabels(true);
        startSlider.setShowTickMarks(true);

        Slider endSlider = new Slider(upStartTime + 0.5, 18, 1);
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

        startSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                if (new_val.doubleValue() % 0.5 == 0)
                    System.out.println(new_val.doubleValue());
                upStartTime = new_val.doubleValue() + 0.5;
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

        String StartDate = "FutureProof";
        String EndDate = "Dates addition";
        okButton.setOnAction(e -> {
            TCPEchoClient.sendClientData(StartDate, EndDate, DayOfWeek, StartTime, EndTime, ClassName, classSelected);
            upStartTime = 9;
        });

        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(InfoLabel, StartTimeLabel, startSlider, EndTimeLabel, endSlider, okButton);
        Scene timeSelectionScene = new Scene(vb, 600, 200);

        Stage timeSelectionStage = new Stage();
        timeSelectionStage.setTitle("Time selection");
        timeSelectionStage.setScene(timeSelectionScene);
        timeSelectionStage.show();

        currentStage = timeSelectionStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
