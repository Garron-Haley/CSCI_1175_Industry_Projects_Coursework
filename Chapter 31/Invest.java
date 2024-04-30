import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

import java.awt.*;
import java.text.Format;


public class Invest extends Application {
    @Override
    public void start(Stage stage){
        Pane base = new Pane();

        GridPane pane = new GridPane();
        TextField IA = new TextField();
        TextField NOY = new TextField();
        TextField AIR = new TextField();
        TextField FV = new TextField();
        base.getChildren().add(pane);
            pane.setLayoutY( 25);
        pane.add(IA, 1, 0);
        pane.add(NOY, 1, 1);
        pane.add(AIR, 1, 2);
        pane.add(FV, 1, 3);
        pane.add(new Label("Investment Amount:"),0,0);
        pane.add(new Label("Number of Years:"),0,1);
        pane.add(new Label("Annual Interest Rate:"),0,2);
        pane.add(new Label("Future Value:"),0,3);

        Button calculate = new Button("Calculate");
        calculate.setLayoutX(200);
        calculate.setLayoutY(127);
        base.getChildren().add(calculate);
        MenuBar menu = new MenuBar();
        menu.setMinWidth(265);
        Menu menuBar = new Menu("Calculate");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e ->{
            System.exit(1);
        });
        menuBar.getItems().add(exit);
        base.getChildren().add(menu);
        menu.getMenus().add(menuBar);



        calculate.setOnAction(e ->{
            double amount = Double.parseDouble(String.valueOf(IA.getText()));
            int years = Integer.parseInt(String.valueOf(NOY.getText()));
            double rate = Double.parseDouble(String.valueOf(AIR.getText()));
            String string = new String();
            //Calculation here, string = calculation
            double result = (amount * (Math.pow((1 + rate), (years * 12))));
            string = String.format("$ %.2f" , result);
            FV.setText(string);
        });

        Scene scene = new Scene(base, 265, 175);
        stage.setTitle("Exercise31_17");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

}
