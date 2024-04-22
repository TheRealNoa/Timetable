/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

/**
 *
 * @author noaca
 */
public class AppController {
    private AppModel model;

    public AppController(AppModel model) {
        this.model = model;
    }

    public void handleButtonAction(String label) {
        if (label.equals("Add Class")) {    
        TCPEchoClient.sendMessage("TD");
        } else if (label.equals("Remove Class")) {
            // Handle Remove Class button action
        }
        // Handle other button actions similarly
    }
}

