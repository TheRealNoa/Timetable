/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group1.mavenproject2;

import javafx.stage.Stage;

/**
 *
 * @author noaca
 */
public class AppModel {
    private int pageNumber;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public void displayTimeTable()
    {
    TCPEchoClient.sendMessage("TD");
    }
    public void openRemoveClassStage()
    {
    RemoveClassStage removeClassStage = new RemoveClassStage();
        try{
        removeClassStage.start(new Stage());
        }catch(Exception ex)
                {
                ex.printStackTrace();
                }
    }
    public void afternoonToMorning()
    {
    TCPEchoClient.sendMessage("EarlyTimes");
    }
    public void terminateConnection()
    {
    TCPEchoClient.sendMessage("STOP");
    }
    public void openDateStage() {
    if (AppView.currentStage != null) {
            AppView.currentStage.close();
    }    
        MonthPickerView monthPicker = new MonthPickerView();
        try {
            monthPicker.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}

}

