package ch.bfh.akka.actorrace.frontend;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the second fxml gui with the controll buttons
 */
public class CentroDiControllo {
	private static CentroDiControllo instance;

	@FXML
	private Button start;

	@FXML
	private Button stop;

	@FXML
	private Button restart;

	@FXML
	private Label result;

	/**
	 * Singleton: method to get instance
	 * @return instance of the CentroDiControllo
	 */
	public static CentroDiControllo getInstance() {
		if (instance == null) {
			instance = new CentroDiControllo();
		}
		return instance;
	}

	/**
	 * Initialize of the fxml stage
	 */
	public void initialize() {
		// disable stop button first
		stop.setDisable(true);
	}

	/**
	 * Function of the start button:
	 * calls start function in main controller
	 */
	public void start() {
		// reset values
		result.setText("");
		start.setText("Start");
		stop.setDisable(false);
		start.setDisable(true);
		// start game
		Controllore.getInstance().startRace();
	}

	/**
	 * Function of the stop button:
	 * calls stop function in main controller
	 */
	public void stop() {
		// stop game
		Controllore.getInstance().stopRace();
		// reset values
		start.setText("Restart");
		start.setDisable(false);
		stop.setDisable(true);
	}

	/**
	 * Function to print result of an attore to the gui
	 * @param name is the name of the attore
	 * @param time is the time needed for the attore to finish
	 * @param found if the attore has found the goal
	 */
	public void result(String name, long time, int found) {
		Platform.runLater(() -> {
			// set text on result-label
			String actualText = result.getText();
			if(found == -1) {
				result.setText(actualText + "\n" + name + " has not found goal");
			} else if (found == 1){
				result.setText(actualText + "\n" + name + " has found goal in " + time + " seconds!");
			}

		});
	}
}
