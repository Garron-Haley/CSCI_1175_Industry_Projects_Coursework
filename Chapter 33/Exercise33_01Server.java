// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Exercise33_01Server extends Application {
  // Text area for displaying contents
  private TextArea ta = new TextArea();

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {

    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }
  public Exercise33_01Server(){
    ServerSocket serverSocket = null;
    try{
      serverSocket = new ServerSocket(8000);
      ta.appendText("server started at " + new Date() + '\n');
      Socket socket = serverSocket.accept();

      DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
      DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
      while(true){
        double interest = inputFromClient.readDouble();
        int year = inputFromClient.readInt();
        double loanAmount = inputFromClient.readDouble();

        Loan loan = new Loan(interest, year, loanAmount);
        double monthlyPay = loan.getMonthlyPayment();
        double totalPay = loan.getTotalPayment();

        outputToClient.writeDouble(monthlyPay);
        outputToClient.writeDouble(totalPay);
        ta.appendText("annual interest rate " + interest + "\n");
        ta.appendText("number of years " + year + "\n");
        ta.appendText("loan amount" + loanAmount + "\n");
        ta.appendText("monthlyPayment " + monthlyPay + "\n");
        ta.appendText("totalPayment" + totalPay + "\n");
      }
    } catch (IOException ex){
      System.err.println(ex);
    }
  }
    
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {

    launch(args);
  }
}
