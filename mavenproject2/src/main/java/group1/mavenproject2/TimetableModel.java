/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

/**
 *
 * @author noaca
 */
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;

public class TimetableModel {
    private List<Label> labels = new ArrayList<>();
    private GridPane gridPane;
    
    public TimetableModel(GridPane gridPane) {
        this.gridPane = gridPane;
    }
    
     public void addLabel(Label label,int columnIndex, int rowIndex) {
        if (!labels.contains(label)) {
            // Remove the label from the grid if it's already added
            if (gridPane.getChildren().contains(label)) {
                gridPane.getChildren().remove(label);
            }
            labels.add(label);
            gridPane.add(label, columnIndex, rowIndex);
        }
    }

    public void removeLabel(Label label) {
        labels.remove(label);
        gridPane.getChildren().remove(label);
    }

    public List<Label> getLabels() {
        return labels;
    }
}

