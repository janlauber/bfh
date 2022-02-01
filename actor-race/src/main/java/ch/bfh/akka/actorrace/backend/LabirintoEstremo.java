package ch.bfh.akka.actorrace.backend;

/**
 * Creates a labyrinth for the actors to navigate
 */
public class LabirintoEstremo {
	private final Integer[][] labirinto;
	private static final int GOAL = 13;
	private int x;
	private int y;

	/**
	 * Initializes the labirinto
	 * @param x is the x coordinate of the labirinto
	 * @param y is the y coordinate of the labirinto
	 */
	public LabirintoEstremo(int x, int y) {
		this.x = x;
		this.y = y;
		this.labirinto = new Integer[x][y];
		for (int i = 0; i < labirinto.length; i++) {
			for (int j = 0; j < labirinto[i].length; j++) {
				if (i == 0 || j == 0 || i == x - 1 || j == y - 1) {
					labirinto[i][j] = 1;
				} else {
					labirinto[i][j] = 0;
				}
			}
		}
	}

	/**
	 * @return the coordinates of x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return the coordinates of y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return labirinto
	 */
	public Integer[][] getLabirinto() {
		return labirinto;
	}

	/**
	 * Defines the goal of the matrix that the attores have to find
	 * @param x is the x coordinate in the labirinto
	 * @param y is the y coordinate in the labirinto
	 */
	public void setGoal(int x, int y) {
		labirinto[x][y] = GOAL;
	}

	/**
	 * Defines a field as wall
	 * @param x is the x coordinate in the labirinto
	 * @param y is the y coordinate in the labirinto
	 */
	public void setWall(int x, int y) {
		labirinto[x][y] = 1;
	}

	/**
	 * Defines a field as path
	 * @param x is the x coordinate in the labirinto
	 * @param y is the y coordinate in the labirinto
	 */
	public void setPath(int x, int y) {
		labirinto[x][y] = 2;
	}

	/**
	 * @return true if the x and y coordinates are a wall
	 * @param x is the x coordinate in the labirinto
	 * @param y is the y coordinate in the labirinto
	 */
	public boolean isWall(int x, int y) {
		return labirinto[x][y] == 1;
	}

	/**
	 * Defines a field as path
	 * @param x is the x coordinate in the labirinto
	 * @param y is the y coordinate in the labirinto
	 * @return if path exists in labirinto
	 */
	public boolean isPath(int x, int y) {
		return labirinto[x][y] == 2;
	}

	/**
	 * @return true if the x and y coordinates are the goal
	 * @param x is the x coordinate in the labirinto
	 * @param y is the y coordinate in the labirinto
	 */
	public boolean isGoal(int x, int y) {
		return labirinto[x][y] == GOAL;
	}
}
