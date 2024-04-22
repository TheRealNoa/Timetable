package group1.mavenproject2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class App extends Application {
    public static Stage primaryStage;
    public static Stage currentStage;
    public int pageNumber;
   @Override
    public void start(Stage primaryStage) {
        pageNumber=1;
//TCPEchoClient.sendMessage("Connection test");
        this.primaryStage=primaryStage;
        this.currentStage = primaryStage;
        // Create buttons
        Button addButton = createStyledButton("Add Class");
        Button removeButton = createStyledButton("Remove Class");
        Button displayButton = createStyledButton("Display Schedule");
        Button removeClassButton = createStyledButton("Remove a class entirely");
        Button createAClass = createStyledButton("Create a Class");
        Button back = new Button("Back");
        Button earlyTimes = new Button("Early");
        
        
        
        Button stop = new Button("Stop");
        
        earlyTimes.setOnAction(e->
        {
        TCPEchoClient.sendMessage("EarlyTimes");
        });
        
        back.setOnAction(e->
        {
        try
        {
        System.out.println("");
        if(pageNumber<2)
        {
        throw new IncorrectActionException();
        }
        }catch(IncorrectActionException ex)
        {
            System.out.println(ex.getMessage());
        }
        });
        
        createAClass.setOnAction(e->
        {
            ClassCreation cc = new ClassCreation();
            try {
            currentStage.close();
            cc.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
        removeClassButton.setOnAction(e->
        {
           MonthPickerView.removeClassEntirely(MonthPickerView.currentStage);
        });
        
        stop.setOnAction(e->
        {
        TCPEchoClient.sendMessage("STOP");
        });
        
        displayButton.setOnAction(e->
        {
        TCPEchoClient.sendMessage("TD");
        });
        removeButton.setOnAction(e -> {
        RemoveClassStage removeClassStage = new RemoveClassStage();
        try {
            removeClassStage.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
        
        // Arrange buttons horizontally
        HBox buttonBox = new HBox(10); // 10 is the spacing between buttons
        buttonBox.getChildren().addAll(back,addButton, removeButton, displayButton,removeClassButton,createAClass, stop, earlyTimes);
        buttonBox.setAlignment(Pos.CENTER);

        // Create a grey box around the buttons
        Rectangle box = new Rectangle(800, 150);
        box.setFill(Color.GREY);
        StackPane stackPane = new StackPane(box, buttonBox);

        // Create the scene
        Scene scene = new Scene(stackPane, 800, 150); // Adjust dimensions as needed

        // Set the scene and show the stage
        primaryStage.setTitle("Class Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create a styled button
    private Button createStyledButton(String label) {
        Button button = new Button(label);
        button.setPrefSize(100, 100);
        button.setOnAction(e -> {
            if (label.equals("Add Class")) {
                openNewStage();
            } else {
                System.out.println("CLICKED");
            }
        });
        return button;
    }
    private void openNewStage() {
    if (currentStage != null) {
            currentStage.close();
    }    
    Stage newStage = new Stage();
    Button dateButton = createStyledButton("Date");

    dateButton.setOnAction(e -> {
        MonthPickerView monthPicker = new MonthPickerView();
        try {
            monthPicker.start(new Stage());
            currentStage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    HBox newButtonBox = new HBox(10);
    newButtonBox.getChildren().addAll(dateButton);
    newButtonBox.setAlignment(Pos.CENTER);

    Rectangle newBox = new Rectangle(300, 100);
    newBox.setFill(Color.GREY);
    StackPane newStackPane = new StackPane(newBox, newButtonBox);

    Scene newScene = new Scene(newStackPane, 300, 100);
    newStage.setTitle("Additional Options");
    newStage.setScene(newScene);
    newStage.show();
    this.currentStage = newStage;
}
    public static void main(String[] args) {
       
        Thread ServerConnect = new Thread(()->
        {
        TCPEchoClient.main(args);
        });
         Thread ConnectionCheck = new Thread(()->
        {
        TCPEchoClient.start();
        if(TCPEchoClient.socket!=null)
        {
        ServerConnect.start();
        }else
        {
        System.out.println("Socket not found. Client app running without connection to server.");
        }
        });
        ConnectionCheck.start();
        
        launch(args);
   }
    }