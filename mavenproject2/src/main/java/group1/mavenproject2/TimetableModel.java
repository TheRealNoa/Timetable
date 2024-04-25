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
    private ArrayList<int[]> coordinates = new ArrayList<>();
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
    int[] coordinatesArray = {columnIndex,rowIndex};
    coordinates.add(coordinatesArray);
    System.out.println("Added a label woohoo!");
    System.out.println(labelInfos.toString());
    label.setPrefSize(100,50);
    label.setStyle("-fx-border-color: black");
    addToGridPane(label,columnIndex,rowIndex);
    }
    
    public void addToGridPane(Label label, int columnIndex, int rowIndex)
    {
        System.out.println("Added to gridpane");
    Platform.runLater(() -> gridPane.add(label, columnIndex, rowIndex));
    }
    
    public void addEmptyCells()
    {
    for(int row = 1; row<=10; row++)
    {
        for (int col=1; col<=5; col++)
        {
            int[] tempCoord = {col,row};
            if(!coordinates.contains(tempCoord))
            {
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
        this.gridPane = gridPane; // Set GridPane when provided
    }
}

