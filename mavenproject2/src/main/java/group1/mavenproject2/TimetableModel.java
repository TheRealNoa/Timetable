package group1.mavenproject2;

/**
 *
 * @author noaca
 */
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class TimetableModel {
    private List<Label> labels = new ArrayList<>();
    private ArrayList<LabelInfo> labelInfos = new ArrayList<>();
    private ArrayList<int[]> coordinates = new ArrayList<>();
    private GridPane gridPane;

    public TimetableModel(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void addLabel(Label label, int columnIndex, int rowIndex) {
        labels.add(label);
    }

    public void addToLabelList(Label label, int columnIndex, int rowIndex) {
        LabelInfo temp = new LabelInfo(label, columnIndex, rowIndex);
        labelInfos.add(temp);
        int[] coordinatesArray = { columnIndex, rowIndex };
        coordinates.add(coordinatesArray);
        System.out.println("Added a label woohoo!");
        System.out.println(labelInfos.toString());
        label.setPrefSize(100, 50);
        label.setStyle("-fx-border-color: black");
        addToGridPane(label, columnIndex, rowIndex);
    }

    public void addToGridPane(Label label, int columnIndex, int rowIndex) {
        System.out.println("Added to gridpane");
        Platform.runLater(() -> gridPane.add(label, columnIndex, rowIndex));
    }

    public void updateAddCell(String info) {
        String[] updateInfo = info.split(",");
        String day = updateInfo[0];
        int localCol = findCol(day);
        String timePeriod = updateInfo[1];
        int localRow = findRow(timePeriod);
        String[] moduleNameL = updateInfo[2].split(":");
        String moduleName = moduleNameL[1].strip();
        System.out.println("Debug:" + localCol + ":" + localRow);
        Platform.runLater(() -> {
            gridPane.getChildren().removeIf(
                    node -> GridPane.getColumnIndex(node) == localCol && GridPane.getRowIndex(node) == localRow);
            Label newLabel = new Label("          " + moduleName + "\n" + "     " + timePeriod);
            newLabel.setPrefSize(100, 50);
            newLabel.setStyle("-fx-border-color: black");
            gridPane.add(newLabel, localCol, localRow);
        });
    }

    public void updateRemoveCell(String info) {
        String[] updateInfo = info.split(",");
        String day = updateInfo[0];
        int localCol = findCol(day);
        String timePeriod = updateInfo[1];
        int localRow = findRow(timePeriod);
        System.out.println("Debug:" + localCol + ":" + localRow);
        Platform.runLater(() -> {
            gridPane.getChildren().removeIf(
                    node -> GridPane.getColumnIndex(node) == localCol && GridPane.getRowIndex(node) == localRow);
            Label cellLabel = new Label();
            cellLabel.setPrefSize(100, 50);
            cellLabel.setStyle("-fx-border-color: black");
            gridPane.add(cellLabel, localCol, localRow);
        });
    }

    public synchronized static int findCol(String s) {
        int tempCol = 0;
        for (int i = 0; i < TimetableView.daysOfWeek.length; i++) {
            if (s.equals(TimetableView.daysOfWeek[i])) {
                tempCol = i + 1;
                break;
            }
        }
        return tempCol;
    }

    public synchronized static int findRow(String s) {
        int tempRow = 0;
        String temp = s.substring(0, 2);
        for (int i = 0; i < TimetableView.times.length; i++) {
            if (temp.equals(TimetableView.times[i].substring(0, 2))) {
                tempRow = i + 1;
            }
        }
        return tempRow;
    }

    public void addEmptyCells() {
        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 5; col++) {
                int[] tempCoord = { col, row };
                if (!coordinates.contains(tempCoord)) {
                    Label cellLabel = new Label();
                    cellLabel.setPrefSize(100, 50);
                    cellLabel.setStyle("-fx-border-color: black");
                    gridPane.add(cellLabel, col, row);
                }
            }
        }
    }

    public void removeLabel(Label label) {
        labels.remove(label);
        gridPane.getChildren().remove(label);
    }

    public List<LabelInfo> getLabels() {
        return labelInfos;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
}
