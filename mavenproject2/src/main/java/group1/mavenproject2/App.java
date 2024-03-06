package group1.mavenproject2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static InetAddress localhost;
    private static final int PORT = 25564;
    private TCPEchoClient tcpClient;

    
   private static void run() {
    Socket link = null;				//Step 1.
    try 
    {
	link = new Socket(localhost,PORT);		//Step 1.
	BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));//Step 2.
	PrintWriter out = new PrintWriter(link.getOutputStream(),true);	 //Step 2.

	//Set up stream for keyboard entry...
	BufferedReader userEntry =new BufferedReader(new InputStreamReader(System.in));
	String message = null;
        String response= null;
	
        System.out.println("Enter message to be sent to server: ");
        message =  userEntry.readLine();
        out.println(message); 		//Step 3.
        response = in.readLine();		//Step 3.
        System.out.println("\nSERVER RESPONSE> " + response);
    } 
    catch(IOException e)
    {
	e.printStackTrace();
    } 
    finally 
    {
        try 
        {
            System.out.println("\n* Closing connection... *");
            link.close();				//Step 4.
	}catch(IOException e)
        {
            System.out.println("Unable to disconnect/close!");
            System.exit(1);
	}
    }
 } // finish run method
    private Stage currentStage;
   @Override
    public void start(Stage primaryStage) {
        
        tcpClient = new TCPEchoClient();
        tcpClient.connectToTCP();
        
        this.currentStage = primaryStage;
        // Create buttons
        Button addButton = createStyledButton("Add Class");
        Button removeButton = createStyledButton("Remove Class");
        Button displayButton = createStyledButton("Display Schedule");

        // Arrange buttons horizontally
        HBox buttonBox = new HBox(10); // 10 is the spacing between buttons
        buttonBox.getChildren().addAll(addButton, removeButton, displayButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create a grey box around the buttons
        Rectangle box = new Rectangle(400, 150);
        box.setFill(Color.GREY);
        StackPane stackPane = new StackPane(box, buttonBox);

        // Create the scene
        Scene scene = new Scene(stackPane, 400, 150); // Adjust dimensions as needed

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
    Button timeButton = createStyledButton("Time");
    Button roomButton = createStyledButton("Room Number");

    dateButton.setOnAction(e -> {
        MonthPickerApp monthPicker = new MonthPickerApp();
        try {
            monthPicker.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    HBox newButtonBox = new HBox(10);
    newButtonBox.getChildren().addAll(dateButton, timeButton, roomButton);
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
        launch(args);
        Thread communication = new Thread   (()->
        {
        run();
        });
        communication.start();
   }
    }
