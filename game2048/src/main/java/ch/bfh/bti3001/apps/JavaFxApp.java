package ch.bfh.bti3001.apps;

import ch.bfh.bti3001.model.Cell;
import ch.bfh.bti3001.model.Grid;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

	private Grid grid;
	public int CELL_SIZE = 50;
	BorderPane root = new BorderPane();
	GridPane gridPane = new GridPane();

	/**
	 * start method
	 * @param stage stage of the application
	 */
	@Override
	public void start(Stage stage) {
		// initialize stage
		Scene scene = new Scene(root, 400, 400);
		// add gridPane to root
		root.getChildren().add(gridPane);
		BorderPane.setAlignment(gridPane, Pos.CENTER);
		// center gridPane in root
		gridPane.setLayoutX((scene.getWidth() - gridPane.getWidth()) / 2);
		gridPane.setLayoutY((scene.getHeight() - gridPane.getHeight()) / 2);
		gridPane.setAlignment(javafx.geometry.Pos.CENTER);

		// add title to root on top and center it
		Label title = new Label("2048");
		title.setStyle("-fx-font-size: " + CELL_SIZE + "px;");
		BorderPane.setAlignment(title, Pos.CENTER);
		root.setTop(title);

		stage.setTitle("2048");
		stage.setResizable(false);

		stage.setScene(scene);

		stage.show();

		// create grid
		grid = new Grid(4, 4);
		grid.setRandomCellValue();

		// get Cell values and display them
		updateGrid();

		// get Score and display it in the top right corner of the root

		Label scoreLabel = new Label("Score: " + grid.getScore());
		scoreLabel.setStyle("-fx-font-size: " + CELL_SIZE / 2 + "px;");
		// margin center of root
		scoreLabel.setLayoutX(root.getWidth() / 2 - scoreLabel.getWidth() / 2);
		scoreLabel.setLayoutY(0);
		BorderPane.setAlignment(scoreLabel, Pos.CENTER);
		root.setBottom(scoreLabel);

		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (grid.gameOver()) {
				// don't show gridPane
				root.getChildren().remove(gridPane);
				// display game over
				Label label = new Label("Game Over");
				label.setStyle("-fx-font-size: 50px;");
				label.setTextFill(Color.RED);
				label.setLayoutX((scene.getWidth() - label.getWidth()) / 2);
				label.setLayoutY((scene.getHeight() - label.getHeight()) / 2);
				root.setCenter(label);
			} else {
				switch (event.getCode()) {
					case UP -> {
						try {
							grid.moveUp();
							grid.setRandomCellValue();
							updateGrid();
							updateScore();
						} catch (IndexOutOfBoundsException e) {
							System.out.println("Move <up> not possible, try again!");
						}
					}
					case DOWN -> {
						try {
							grid.moveDown();
							grid.setRandomCellValue();
							updateGrid();
							updateScore();
						} catch (IndexOutOfBoundsException e) {
							System.out.println("Move <down> not possible, try again!");
						}
					}
					case LEFT -> {
						try {
							grid.moveLeft();
							grid.setRandomCellValue();
							updateGrid();
							updateScore();
						} catch (IndexOutOfBoundsException e) {
							System.out.println("Move <left> not possible, try again!");
						}
					}
					case RIGHT -> {
						try {
							grid.moveRight();
							grid.setRandomCellValue();
							updateGrid();
							updateScore();
						} catch (IndexOutOfBoundsException e) {
							System.out.println("Move <right> not possible, try again!");
						}
					}
					default -> System.out.println("Unknown key");
				}
			}
		});
	}

	/*
	 * update gridPane with new values
	 */
	public void updateGrid() {
		for (int i = 0; i < grid.getWidth(); i++) {
			for (int j = 0; j < grid.getHeight(); j++) {
				// add border
				Rectangle border = new Rectangle(CELL_SIZE, CELL_SIZE);
				Cell cell = grid.getCell(i, j);
				border.setFill(cell.getBackground());
				border.setStroke(Color.BLACK);
				gridPane.add(border, i, j);
				// add label with value and center it
				Label label = new Label(String.valueOf(grid.getCellValue(i, j)));
				label.setStyle("-fx-font-size: " + CELL_SIZE / 2 + "px;");
				label.setTextFill(cell.getForeground());
				GridPane.setHalignment(label, HPos.CENTER);
				GridPane.setValignment(label, VPos.CENTER);
				gridPane.add(label, i, j);
			}
		}
	}

	/*
	 * updates the score label
	 */
	public void updateScore() {
		// update score label
		Label scoreLabel = (Label) root.getChildren().get(root.getChildren().size() - 1);
		scoreLabel.setText("Score: " + grid.getScore());
	}

	/**
	 * main method
	 * @param args arguments
	 */
	public static void main(String[] args) {
		launch();
	}

}
