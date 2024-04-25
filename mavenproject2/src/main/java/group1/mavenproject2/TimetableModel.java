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
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class TimetableModel {
    private List<Label> labels = new ArrayList<>();
    private ArrayList<LabelInfo> labelInfos = new ArrayList<>();
    private GridPane gridPane;
    
    public TimetableModel(GridPane gridPane) {
        this.gridPane = gridPane;
    }
    
    /*public void addLabel(Label label, int columnIndex, int rowIndex) {
    if (!labels.contains(label)) {
       if (gridPane.getChildren().contains(label)) {
            gridPane.getChildren().remove(label);
        }
        labels.add(label);
        if(this.gridPane!=null){
        Platform.runLater(() -> gridPane.add(label, columnIndex, rowIndex));
        }else
        {
        System.out.println("GridPane is null");
        }
    }
    }*/
    //first add to list then get from list and apply
    public void addLabel(Label label, int columnIndex, int rowIndex) {
    labels.add(label);
    }
    public void addToLabelList(Label label, int columnIndex, int rowIndex)
    {
    LabelInfo temp = new LabelInfo(label,columnIndex,rowIndex);
    labelInfos.add(temp);
    }

    public void removeLabel(Label label) {
        labels.remove(label);
        gridPane.getChildren().remove(label);
    }

    public List<Label> getLabels() {
        return labels;
    }
    
    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane; // Set GridPane when provided
    }
}

