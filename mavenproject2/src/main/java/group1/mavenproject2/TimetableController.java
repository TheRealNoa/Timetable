package group1.mavenproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author noaca
 */
public class TimetableController {
    public static ArrayList<ArrayList<String>> Inputs = new ArrayList<>();
    static int timesChecked;
    public static boolean running = true;
    public static int col;
    public static int row;
    public static TimetableModel model;
    private static final Object lock = new Object();

    public static void getInputs() {
        openTimetable();
    }

    TimetableController(ArrayList<ArrayList<String>> s, TimetableModel m) {
        this.model = m;
    }

    public static synchronized void openTimetable() {
        if (Inputs.get(0).get(0).startsWith("Mon")) {
            System.out.println("Inputs recieved:" + Inputs);
            synchronized (lock) {
                System.out.println("Here inputs are:" + Inputs);
                ArrayList<ArrayList<String>> inputsCopy = new ArrayList<>(Inputs);
                Platform.runLater(() -> displayTimtable(inputsCopy));
            }
        }
        System.out.println("Model is:" + model);
    }

    public static void displayTimtable(ArrayList<ArrayList<String>> as) {
        System.out.println("AS:" + as);
        Inputs = as;
        if (AppView.currentStage != null) {
            AppView.currentStage.close();
        }
        TimetableView timetableView = new TimetableView();
        timetableView.setInputs(Inputs);
        Stage stage = new Stage();
        timetableView.start(stage);
    }

    public static void addLabel(Label label, int col, int row) {
        System.out.println("Called addLabel");
        model.addLabel(label, col, row);
    }

    public void updateAddCell(String info) {
        model.updateAddCell(info);
    }

    public void updateRemoveCell(String info) {
        model.updateRemoveCell(info);
    }

    public void removeLabel(Label label) {
        model.removeLabel(label);
    }

    public void checkLabelList() {
        new Thread(() -> {
            List<LabelInfo> currentLabels;
            while (running) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<LabelInfo> newLabels = model.getLabels();
                currentLabels = new ArrayList<>(model.getLabels());
                if (!newLabels.equals(currentLabels)) {
                    System.out.println("It changed");
                    currentLabels = newLabels;
                } else {
                    System.out.println(currentLabels + ":" + newLabels);
                    System.out.println("No change");
                }
            }
        }).start();
    }

    public void stopThread() {
        running = false;
    }

    private void handleLabelListChanges() {
        System.out.println("Label list has changed.");
    }

    public static void processInputs(ArrayList<String> list, TimetableModel m) {
        System.out.println("Model :" + m);
        System.out.println("Started processing: " + list);
        String day = list.get(0);
        int column = m.findCol(day);
        int rowS = 0;
        ArrayList<String[]> timeClasses = new ArrayList<>();
        if (list.size() > 2) {
            for (int i = 1; i < list.size(); i += 2) {
                String[] timeClass = new String[2];
                timeClass[0] = list.get(i);
                timeClass[1] = list.get(i + 1).trim();
                timeClasses.add(timeClass);
            }
            for (String[] tc : timeClasses) {
                rowS = m.findRow(tc[0]);
                System.out.println(
                        "For class: " + tc[0] + ", " + tc[1] + " on " + day + "We have entry: " + column + rowS);
                String[] moduleSplit = tc[1].split(":");
                String moduleName = moduleSplit[1].strip();
                Label tempLabel = new Label("          " + moduleName + "\n" + "     " + tc[0]);
                if (m != null) {
                    m.addToLabelList(tempLabel, column, rowS);
                } else {
                    System.out.println("Label is null");
                }
            }
        } else {
            System.out.println("Input size is less then 2 for " + day);
        }

    }

    private synchronized static int findCol(String s) {
        int tempCol = 0;
        for (int i = 0; i < TimetableView.daysOfWeek.length; i++) {
            if (s.equals(TimetableView.daysOfWeek[i])) {
                tempCol = i + 1;
                break;
            }
        }
        System.out.println("For " + s + " We have column:" + tempCol);
        return tempCol;
    }

}
