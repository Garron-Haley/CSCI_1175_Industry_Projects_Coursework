import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.application.Application.launch;

public class FlagRisingAnimation extends Application implements Runnable {
	private Thread thread;
	private boolean running;
	private Stage window;
		public void run(){
			while (running){
				int y = 1;
			}
			stopThread();
		}
		public synchronized void startThread(){
			running = true;
			thread = new Thread(this, "yes");
			thread.start();
		}
		public synchronized void stopThread(){
			running = false;
			try {
				thread.join();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	 // Override the start method in the Application class
	public void start(Stage primaryStage) throws Exception {
		// Create a pane
		startThread();
		window = primaryStage;
		Pane pane = new Pane();

		// Add an image view and add it to pane
		ImageView imageView = new ImageView("image/us.gif");
		pane.getChildren().add(imageView);

		// Create a path transition
		PathTransition pt = new PathTransition(Duration.millis(10000),
				new Line(100, 200, 100, 0), imageView); pt.setCycleCount(5);
		pt.play(); // Start animation

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 250, 200);
		window.setOnCloseRequest(e ->{
			e.consume();
			close();
		});

		window.setTitle("FlagRisingAnimation"); // Set the stage title
		window.setScene(scene); // Place the scene in the stage
		window.show(); // Display the stage

	}
		public void close(){
			window.close();
			stopThread();
		}
	public static void main(String[] args) {
		launch(args);


	}

}