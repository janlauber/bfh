/*
 * Project and Training 2: Space News - Computer Science, Berner Fachhochschule
 */
package ch.bfh.spacenews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * Dummy application class demonstrating a JavaFX application.
 */
public class HelloFX extends Application {

	/**
	 * Start method called by the JavaFX framework upon calling launch().
	 *
	 * @param stage a (default) stage provided by the framework
	 */
	@Override
	public void start(Stage stage) throws Exception {
		final int w = 800;
		final int h = 600;

		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
		stage.setTitle("Space News App");

		Scene scene = new Scene(root, w, h);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Main entry point of the application.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {
		launch();
	}

}
