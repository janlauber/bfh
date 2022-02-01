/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starting point of the Java FX application.
 */
public class App extends Application {

	static final int WIDTH = 640;
	static final int HEIGHT = 480;

	/**
	 * Start method called by the JavaFX framework upon calling launch().
	 *
	 * @param stage a (default) stage provided by the framework
	 */
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chart.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.setTitle("P&T2 - Pie Chart");
		stage.show();
	}

	/**
	 * Main entry point of the application.
	 *
	 * @param args not used
	 * @throws RuntimeException if anything goes wrong
	 */
	public static void main(String[] args) {
		// TODO Uncomment the following line when having completed class
		// SalesValueRepository.
		// SalesValueLoader.loadSalesValues();
		launch();
	}

}
