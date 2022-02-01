package ch.bfh.akka.actorrace.frontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Starts the fxml view for the game
 */
public class Principale extends Application {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;

	/**
	 * Starts the fxml stage
	 */
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view.fxml"));
			loader.setController(Controllore.getInstance());
			Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
			URL url = getClass().getClassLoader().getResource("application.css");
			scene.getStylesheets().add(url.toExternalForm());
			stage.setOnCloseRequest(e -> {
				Platform.exit();
			});
			stage.setTitle("Actor-Race Project");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main function, entrypoint of the program
	 * @param args the default args by starting the program
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
