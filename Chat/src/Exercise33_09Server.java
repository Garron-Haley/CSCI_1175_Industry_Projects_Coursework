import javafx.application.Application;
import javafx.event.EventHandler;
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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Exercise33_09Server extends Application {


    private static final long serialVersionUID = 1L;
    private TextArea taServer = new TextArea();
    private TextArea taClient = new TextArea();
    private PrintWriter toClient;



    @Override
    public void  start(Stage primaryStage) {
      taServer.setWrapText(true);
      taClient.setWrapText(true);
      taClient.setDisable(true);


      BorderPane pane1 = new BorderPane();
      pane1.setTop(new Label("History"));
      pane1.setCenter(new ScrollPane(taClient));
      BorderPane pane2 = new BorderPane();
      pane2.setTop(new Label("New Message"));
      pane2.setCenter(new ScrollPane(taServer));

      VBox vBox = new VBox(5);
      vBox.getChildren().addAll(pane1, pane2);

      Scene scene = new Scene(vBox, 200, 200);
      primaryStage.setTitle("Server");
      primaryStage.setScene(scene);
      primaryStage.show();

      try {
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket socket = serverSocket.accept();
        //toClient = new PrintWriter(socket.getOutputStream());
        toClient = new PrintWriter(socket.getOutputStream());
        new ReceiveMessage(socket);
      } catch (IOException e) {
        e.printStackTrace();
      }

      taServer.setOnKeyPressed( ke -> {
        if (ke.getCode() == KeyCode.ENTER) {
          try {
            System.out.println("testServer");
            //toClient.print(taServer.getText());
            toClient.print(taServer.getText());
            toClient.flush();
            taServer.setText("");
          } catch (ArrayIndexOutOfBoundsException e2) {
          }

        }
      });

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
          Scanner fromClient = new Scanner(socket.getInputStream());

          while(true) {
            String text = fromClient.nextLine();
            taClient.appendText(text + "\n");
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