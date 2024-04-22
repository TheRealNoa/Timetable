/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject5;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 *
 * @author noaca
 */
public class View extends StackPane {
    private Label label;

    public View() {
        label = new Label();
        getChildren().add(label);
    }

    public void updateLabel(String text) {
        label.setText(text);
    }
}
