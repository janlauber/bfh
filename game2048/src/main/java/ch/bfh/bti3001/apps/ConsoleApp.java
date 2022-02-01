package ch.bfh.bti3001.apps;

import ch.bfh.bti3001.model.Grid;

import java.util.Scanner;

public class ConsoleApp {

	/**
	 * main method
	 * @param args arguments
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the 2048 Console App!");
		System.out.println("a=left, d=right, w=up, s=down, q=quit");
		Grid grid = new Grid(4,4);
		Scanner scanner = new Scanner(System.in);
		grid.setRandomCellValue();
		while(!grid.gameOver()) {
			System.out.println(grid.toString());
			String input = scanner.nextLine();
			switch (input) {
			case "a":
				try {
					grid.moveLeft();
					grid.setRandomCellValue();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Move <left> not possible, try again!");
				}
				break;
			case "d":
				try {
					grid.moveRight();
					grid.setRandomCellValue();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Move <right> not possible, try again!");
				}
				break;
			case "w":
				try {
					grid.moveUp();
					grid.setRandomCellValue();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Move <up> not possible, try again!");
				}
				break;
			case "s":
				try {
					grid.moveDown();
					grid.setRandomCellValue();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Move <down> not possible, try again!");
				}
				break;
			case "q":
				System.out.println("Bye!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid input!");
				break;
			}
		}
		System.out.println("Game Over!");
		System.exit(0);
	}
}
