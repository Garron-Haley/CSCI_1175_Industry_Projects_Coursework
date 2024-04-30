import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Exercise33_09Client extends Application {
  private static final long serialVersionUID = 1L;
  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
  private PrintWriter toServer;

  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
    taClient.setWrapText(true);
    taServer.setDisable(true);
    BorderPane pane1 = new BorderPane();
    pane1.setTop(new Label("History"));
    pane1.setCenter(new ScrollPane(taServer));
    BorderPane pane2 = new BorderPane();
    pane2.setTop(new Label("New Message"));
    pane2.setCenter(new ScrollPane(taClient));

    VBox vBox = new VBox(5);
    vBox.getChildren().addAll(pane1, pane2);
    Scene scene = new Scene(vBox, 200, 200);
    primaryStage.setTitle("Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    taClient.setOnKeyPressed(ke -> {

        if (ke.getCode() == KeyCode.ENTER) {
          try {
            System.out.println("testClient");
            toServer.print(taClient.getText());
            toServer.flush();
            taClient.setText("");
          } catch (ArrayIndexOutOfBoundsException e2) {
          }

        }

    });

    try {
      Socket socket = new Socket("localhost", 8000);
      toServer = new PrintWriter(socket.getOutputStream());
      new ReceiveMessage(socket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  class ReceiveMessage implements Runnable {
    private Socket socket;
    public ReceiveMessage(Socket socket) {
      this.socket = socket;
      Thread thread = new Thread(this);
      thread.start();
    }
    public void run() {
      try {
        @SuppressWarnings("resource")
        Scanner fromServer = new Scanner(socket.getInputStream());
        while(true) {


          String text = fromServer.nextLine();
          taServer.appendText(text + "\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}