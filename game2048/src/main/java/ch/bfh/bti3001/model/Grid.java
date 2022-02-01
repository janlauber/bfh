package ch.bfh.bti3001.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Grid class represents a grid of cells.
 */
public class Grid {
	// Grid dimensions
	private final Cell[][] grid;
	private int Score;
	private int highestNumber;
	private final int width;
	private final int height;

	/**
	 *
	 * @param width width of grid
	 * @param height height of grid
	 */
	public Grid(int width, int height) {
		grid = new Cell[width][height];
		this.width = width;
		this.height = height;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grid[i][j] = new Cell();
			}
		}
	}

	/**
	 * Constructor with default grid size of 4x4
	 */
	public Grid() {
		grid = new Cell[4][4];
		this.width = 4;
		this.height = 4;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				grid[i][j] = new Cell();
			}
		}
	}

	/**
	 *
	 * @return random cell in grid which value is 0
	 */
	public Cell getRandomCell() {
		List<Cell> emptyCells = new ArrayList<Cell>();
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				if (cell.getValue() == 0) {
					emptyCells.add(cell);
				}
			}
		}
		int randomIndex = (int) (Math.random() * emptyCells.size());
		return emptyCells.get(randomIndex);
	}

	/**
	 *
	 * @return set random value of the random cell to 2 or 4
	 */
	public void setRandomCellValue() {
		if (!isGameOver()) {
			Cell cell = getRandomCell();
			int r = Math.random() > 0.1 ? 2 : 4;
			if (r > highestNumber) {
				highestNumber = r;
			}
			cell.setValue(r);
		}
	}

	/**
	 *
	 * @param x x value of cell
	 * @param y y value of cell
	 */
	public void setValue(int x, int y, int value) {
		grid[x][y].setValue(value);
	}

	/**
	 *
	 * @param x x value of cell
	 * @param y y value of cell
	 * @return value of cell at coordinates x,y
	 */
	public int getValue(int x, int y) {
		return grid[x][y].getValue();
	}

	/**
	 *
	 * @param direction direction of the slide move
	 */
	public void move(Direction direction) {
		// Move the grid in the given direction
		for (int i = 0; i < grid.length; i++) {
			List<Cell> cellSet = new ArrayList<Cell>();

			for (int j = 0; j < grid[i].length; j++) {
				switch (direction) {
					case UP -> cellSet.add(grid[i][j]);
					case DOWN -> cellSet.add(grid[i][grid.length - j - 1]);
					case LEFT -> cellSet.add(grid[j][i]);
					case RIGHT -> cellSet.add(grid[grid.length - j - 1][i]);
					default -> {
					}
				}
			}

			if (!(isEmptyCell(cellSet))) {
				slide(cellSet);
			}
		}
	}

	/**
	 *
	 * @param cellSet List of cells
	 * @return true if cell is empty
	 */
	private boolean isEmptyCell(List<Cell> cellSet) {
		// Check if the cellSet contains only empty cells
		for (Cell cell : cellSet) {
			if (cell.getValue() != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param cellSet List of cells
	 */
	private void slide(List<Cell> cellSet) {
		slideToEdge(cellSet);
		mergeCell(cellSet);
	}

	/**
	 *
	 * @param cellSet List of cells
	 */
	private void slideToEdge(List<Cell> cellSet) {
		// Slide the cellSet to the edge of the grid
		for (int i = 0; i < cellSet.size(); i++) {
			if (remainingIsZero(cellSet, i)) {
				return;
			}

			while (cellSet.get(i).getValue() == 0) {
				slideTo(cellSet, i);
			}
		}
	}

	/**
	 *
	 * @param cellSet List of cells
	 * @param index index of cell
	 */
	private boolean remainingIsZero(List<Cell> cellSet, int index) {
		List<Cell> remainingCell = new ArrayList<Cell>();
		for (int i = index; i < cellSet.size(); i++) {
			remainingCell.add(cellSet.get(i));
		}
		return isEmptyCell(remainingCell);
	}

	/**
	 *
	 * @param cellSet List of cells
	 * @param index index of cell
	 */
	private void slideTo(List<Cell> cellSet, int index) {
		// Slide the cellSet to the given index
		for (int i = index; i < cellSet.size() - 1; i++) {
			cellSet.get(i).setValue(cellSet.get(i + 1).getValue());
		}
		cellSet.get(cellSet.size() - 1).reset();
	}

	/**
	 *
	 * @param cellSet List of cells
	 */
	private void mergeCell(List<Cell> cellSet) {
		// Merge the cellSet
		for (int i = 0; i < cellSet.size() - 1; i++) {
			if (cellSet.get(i).equals(cellSet.get(i + 1))) {
				cellSet.get(i).merge(cellSet.get(i + 1));
				// Set Score
				Score += cellSet.get(i).getValue();
				if (cellSet.get(i).getValue() > highestNumber) {
					highestNumber = cellSet.get(i).getValue();
				}
				cellSet.get(i + 1).reset();
				slideTo(cellSet, i + 1);
			}
		}
	}

	/**
	 *
	 * @return true if game is over
	 */
	public boolean isGameOver() {
		if (hasEmptyCell()) {
			return false;
		}
		return !(hasEqualNeighbour());
	}

	/**
	 *
	 * @return true if grid has an empty cell
	 */
	private boolean hasEmptyCell() {
		// Check if the grid contains empty cells
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				if (cell.getValue() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @return true if grid has equal neighbour cells
	 */
	private boolean hasEqualNeighbour() {
		// Check if the grid contains equal neighbour cells
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (j < grid[i].length - 1) {
					if (grid[i][j].equals(grid[i][j + 1])) {
						return true;
					}
				}
				if (i < grid.length - 1) {
					if (grid[i][j].equals(grid[i + 1][j])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @param x x of cell
	 * @param y y of cell
	 * @return cell value in grid of specific cell
	 */
	public int getCellValue(int x, int y) {
		return grid[x][y].getValue();
	}

	/**
	 *
	 * @param x x of cell
	 * @param y y of cell
	 * @return cell of specific grid coordinate
	 */
	public Cell getCell(int x, int y) {
		return grid[x][y];
	}

	/**
	 * move up
	 */
	public void moveUp() {
		move(Direction.UP);
	}

	/**
	 * move down
	 */
	public void moveDown() {
		move(Direction.DOWN);
	}

	/**
	 * move left
	 */
	public void moveLeft() {
		move(Direction.LEFT);
	}

	/**
	 * move right
	 */
	public void moveRight() {
		move(Direction.RIGHT);
	}

	/**
	 *
	 * @return true if isGameOver() check is true
	 */
	public boolean gameOver() {
		return isGameOver();
	}

	/**
	 *
	 * @return Score int value
	 */
	public int getScore() {
		return Score;
	}

	/**
	 *
	 * @return width of grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 *
	 * @return height of grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 *
	 * @return String of the whole grid
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				sb.append(grid[x][y]);
				sb.append(" ");
				if (x == grid.length - 1) {
					sb.append("\n");
				}
			}
		}
		sb.append("Score   : ");
		sb.append(Score);
		sb.append("\n");
		sb.append("Highest : ");
		sb.append(highestNumber);

		return sb.toString();
	}

}
