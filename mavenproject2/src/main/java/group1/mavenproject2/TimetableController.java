/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    public static ArrayList<ArrayList<String>> Inputs;
    static int timesChecked;
    public  static boolean running=true;
    public static int col;
    public static int row;
    public static TimetableModel model;
    private static volatile List<LabelInfo> labelsInfo = new ArrayList<>();
    
     public TimetableController(TimetableModel model, GridPane gridPane) {
        this.model = model;
        model.setGridPane(gridPane); // Pass GridPane to TimetableMode
        //processInputs();
    }
    
    TimetableController(ArrayList<ArrayList<String>> s, TimetableModel m)
    {
    this.Inputs = new ArrayList<>();
    this.Inputs=s;
    this.model=m;
    
    }

    public void openTimetable()// stupid name
    {
    if(Inputs.get(0).get(0).startsWith("Mon"))
    {
        //System.out.println("Inputs recieved:" + Inputs);
        synchronized(this)
        {
        ParallelTimetableProcessing.processing(Inputs);
        Platform.runLater(() -> displayTimtable());
        }
         // gosh this gave me a headache
        
        //NOTE TO SELF:
        //Apparently if you try to call a method like displayTimetable() which executes something
        //on the javaFX thread, then you have to call that method from a javaFX thread as well...
    }
    
    }
    public void displayTimtable()
    {
    if (AppView.currentStage != null) {
            AppView.currentStage.close();
    }    
        TimetableView timetableView = new TimetableView();
        Stage stage = new Stage();
        timetableView.start(stage);
        this.model = timetableView.model;
}
    //Experimental
    public void addLabel(Label label, int col, int row) {
        model.addLabel(label,col,row);
    }

    public void removeLabel(Label label) {
        model.removeLabel(label);
    }

   public void checkLabelList() {
    new Thread(() -> {
        List<Label> currentLabels;
        List<LabelInfo> localLabelsInfo;
        while (running) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //model.addLabel(l, 1, 1);
            //addlabelz(model,l,1,1);
            //addlabelz();
            synchronized(this)
        {
        localLabelsInfo = new ArrayList<>(labelsInfo);
        }
            //System.out.println("Label infos " +localLabelsInfo);
            //for (LabelInfo labelInfo : localLabelsInfo) { // Iterate over labelsInfo
              // System.out.println("Checked a label");
               //model.addLabel(labelInfo.getLabel(), labelInfo.getRow(), labelInfo.getCol());
            //}
            timesChecked++;
            List<Label> newLabels = model.getLabels();
            //synchronized (model) {
                currentLabels = new ArrayList<>(model.getLabels()); 
            //}
            if (!newLabels.equals(currentLabels)) {
                System.out.println("It changed");
                //synchronized (model) {
                    currentLabels = newLabels;
                //}
            }else
            {
            System.out.println("No change");
            }
        }
    }).start();
}
    public void stopThread()
    {
        running=false;
    }

    private void handleLabelListChanges() {
        System.out.println("Label list has changed.");
    }// will use this in a bit...
    
    


        public static void processInputs(ArrayList<String> list) { // TO RE-DO lol
        System.out.println("Started processing: " + list);
        String day = list.get(0);
        int column = findCol(day);
        int rowS=0;
        ArrayList<String[]> timeClasses = new ArrayList<>();
        if (list.size() > 2) {
            for (int i = 1; i < list.size(); i += 2) {
                String[] timeClass = new String[2];
                timeClass[0] = list.get(i);
                timeClass[1] = list.get(i + 1).trim();
                timeClasses.add(timeClass);
            }
            for (String[] tc : timeClasses) {
                rowS = findRow(tc[0]);
                //Label temp = new Label("trial");
                //labelsInfo.add(new LabelInfo(temp, column, rowS));
                System.out.println("For class: " + tc[0] + ", " + tc[1] + " on " + day + "We have entry: " + column + rowS);
                Label tempLabel = new Label(day);
                model.addLabel(tempLabel, column, rowS);
                //if(!labelsInfo.contains(tempLabel))
                //{
                //labelsInfo.add(new LabelInfo(tempLabel, column, rowS));
                //System.out.println("Added a label");
                //}
            }
            System.out.println("Labels:" + labelsInfo);
            //addlabelz();
        }else
        {
        System.out.println("Input size is less then 2 for " + day);
        }
        
    }
    private synchronized void addlabelz()
    {
    for (LabelInfo labelInfo : labelsInfo) { // Iterate over labelsInfo
        System.out.println("Checked a label");
            model.addLabel(labelInfo.getLabel(), labelInfo.getRow(), labelInfo.getCol());
       
    }
    }
    private synchronized static int findRow(String s)
    {
        String temp = s.substring(0,2);
    //System.out.println("temp" + temp);
    for(int i=0;i<TimetableView.times.length;i++)
    {
    if(temp.equals(TimetableView.times[i].substring(0,2)))
    {
    row =i+1;
    }
    }
    return row;
    }
    
    private synchronized static int findCol(String s)
    {
        int tempCol=0;
    for(int i=0;i<TimetableView.daysOfWeek.length;i++)
    {
    if(s.equals(TimetableView.daysOfWeek[i]))
    {
    tempCol =i+1;
    break;
    }
    }
    System.out.println("For " + s + " We have column:" + tempCol);
    return tempCol;
    }
    
    
}
