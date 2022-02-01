package ch.bfh.akka.actorrace.frontend;

import akka.actor.typed.ActorSystem;
import ch.bfh.akka.actorrace.backend.GrossoCervello;
import ch.bfh.akka.actorrace.backend.LabirintoEstremo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * Main Controller for the game view
 */
public class Controllore {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	private static final int WIDTH_CENTRO = 400;
	private static final int HEIGHT_CENTRO = 400;
	private static final int LABIRINTO_X = 20;
	private static final int LABIRINTO_Y = 20;
	private static final int LABIRINTO_WALLS = 100;
	private static final int GOAL_X = 14;
	private static final int GOAL_Y = 11;
	private int width;
	private int height;
	private int gridHeight;
	private int gridWidth;
	private LabirintoEstremo lab;
	private Rectangle randLast;
	private Rectangle schneggoLast;
	private static Controllore instance;
	private ActorSystem<GrossoCervello.Message> system;

	@FXML
	private Group group;

	/**
	 * Initialize the controller
	 * @param width is the width of the fxml gui
	 * @param height is the height of the fxml gui
	 */
	private Controllore(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Singleton: method to get instance
	 * @return the instance of the Controllore
	 */
	public static Controllore getInstance() {
		if (instance == null) {
			instance = new Controllore(WIDTH, HEIGHT);
		}
		return instance;
	}

	/**
	 * start the control center fxml gui
	 * @throws IOException if a file (.fxml, .css) cannot be opened
	 */
	public void centroDiControllo() throws IOException {
		// create second fxml view for control buttons
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("centro.fxml"));
		loader.setController(CentroDiControllo.getInstance());
		Parent root = loader.load();
		Scene scene = new Scene(root, WIDTH_CENTRO, HEIGHT_CENTRO);
		URL url = getClass().getClassLoader().getResource("application.css");
		scene.getStylesheets().add(url.toExternalForm());
		stage.setOnCloseRequest(e -> {
			Platform.exit();
		});
		stage.setTitle("Centro Di Controllo");
		stage.setScene(scene);
		stage.setX(100);
		stage.setY(100);
		stage.show();
	}

	/**
	 * Create the walls randomly inside the labirinto
	 * @param walls is the number of walls
	 */
	public void labDesign(int walls) {
		// create walls in labirinto
		for (int i = 0; i < walls; i++) {
			int x = (int) (Math.random() * LABIRINTO_X);
			int y = (int) (Math.random() * LABIRINTO_Y);
			if (lab.getLabirinto()[x][y] != 13 && lab.getLabirinto()[x][y] != 1) {
				lab.setWall(x, y);
			} else {
				i--;
			}
		}
	}

	/**
	 * Initialize the fxml view of the labirinto
	 */
	public void initViewLabirinto() {
		// create inital fxml view of the labirinto
		for (int i = 0; i < LABIRINTO_X; i++) {
			for (int j = 0; j < LABIRINTO_Y; j++) {
				Rectangle rectangle = new Rectangle(gridHeight, gridWidth);
				rectangle.setStroke(Color.BLACK);
				rectangle.setTranslateX(i * gridHeight);
				rectangle.setTranslateY(j * gridWidth);
				if (lab.getLabirinto()[i][j] == 1) {
					rectangle.setFill(Color.BLACK);
				} else if (lab.getLabirinto()[i][j] == 13) {
					rectangle.setFill(Color.GREEN);
				} else {
					rectangle.setFill(Color.WHITE);
				}
				group.getChildren().add(rectangle);
			}
		}
	}

	/**
	 * Initialize of the fxml stage:
	 * - Create the labirinto
	 * - Initialize fxml view of labirinto
	 * - Starts control centre gui
	 */
	public void initialize() {
		System.out.println("Actor-Race Project");

		// create labirinto
		lab = new LabirintoEstremo(LABIRINTO_X, LABIRINTO_Y);
		lab.setGoal(GOAL_X, GOAL_Y);
		labDesign(LABIRINTO_WALLS);

		gridWidth = width / LABIRINTO_Y;
		gridHeight = height / LABIRINTO_X;

		// create init fxml view of labirinto
		initViewLabirinto();

		// start fxml stage centroDiControllo
		try {
			centroDiControllo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a GrossoCervello and start the game
	 */
	public void startRace() {
		// reset fxml view of Labirinto (needed if game is restarted)
		initViewLabirinto();

		// create the root-actor GrossoCervello and start the game
		schneggoLast = null;
		system = ActorSystem.create(GrossoCervello.create(), "system");
		system.tell(new GrossoCervello.StartAttores(lab));
	}

	/**
	 * Stop the race by sending a message to the GrossoCervello
	 */
	public void stopRace() {
		// stop the root-actor GrossoCervello
		system.tell(new GrossoCervello.Stop());
	}

	/**
	 * Update the fxml view of the labirinto. Is used after each movement of the attores
	 * @param randX is the x coordinate of the AttoreRand
	 * @param randY is the y coordinate of the AttoreRand
	 * @param schneggoX is the x coordinate of the AttoreSchneggo
	 * @param schneggoY is the y coordinate of the AttoreSchneggo
	 */
	public void update(int randX, int randY, int schneggoX, int schneggoY) {
		// update fxml view of the labirinto
		Platform.runLater(() -> {
			for (int i = 0; i < LABIRINTO_X; i++) {
				for (int j = 0; j < LABIRINTO_Y; j++) {
					if (lab.getLabirinto()[i][j] == 1) {
						// set the walls with color black
						Rectangle wall = new Rectangle(gridHeight, gridWidth);
						wall.setStroke(Color.BLACK);
						wall.setTranslateX(i * gridHeight);
						wall.setTranslateY(j * gridWidth);
						wall.setFill(Color.BLACK);
						group.getChildren().add(wall);
					} else if (lab.getLabirinto()[i][j] == 13) {
						// set the goal with color green
						Rectangle goal = new Rectangle(gridHeight, gridWidth);
						goal.setStroke(Color.BLACK);
						goal.setTranslateX(i * gridHeight);
						goal.setTranslateY(j * gridWidth);
						goal.setFill(Color.GREEN);
						group.getChildren().add(goal);
					} else if (i == randX && j == randY) {
						if (randLast != null) {
							// delete the previous rectangle
							group.getChildren().remove(randLast);
						}
						// set the new rectangle
						randLast = new Rectangle(gridHeight, gridWidth);
						randLast.setStroke(Color.BLACK);
						randLast.setTranslateX(i * gridHeight);
						randLast.setTranslateY(j * gridWidth);
						randLast.setFill(Color.RED);
						group.getChildren().add(randLast);
					} else if (i == schneggoX && j == schneggoY) {
						if (schneggoLast != null) {
							// reset the border of the previous rectangle
							group.getChildren().remove(schneggoLast);
							schneggoLast.setStroke(Color.BLACK);
							schneggoLast.setStrokeWidth(0.5);
							group.getChildren().add(schneggoLast);
						}
						// set the new rectangle
						schneggoLast = new Rectangle(gridHeight, gridWidth);
						schneggoLast.setStroke(Color.YELLOW);
						schneggoLast.setStrokeWidth(3);
						schneggoLast.setTranslateX(i * gridHeight);
						schneggoLast.setTranslateY(j * gridWidth);
						schneggoLast.setFill(Color.BLUE);
						group.getChildren().add(schneggoLast);
					}
				}
			}
		});
	}
}
