package ch.bfh.bti3001;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import ch.bfh.bti3001.model.Grid;

public class GridTest {

	@Test
	public void testGrid_2_times_2() {

		Grid grid = new Grid(2, 2);

		grid.setValue(0, 0, 2);
		grid.setValue(0, 1, 2);
		grid.moveDown();
		assertEquals(0, grid.getValue(0, 0));
		assertEquals(4, grid.getValue(0, 1));
		assertEquals(0, grid.getValue(1, 0));
		assertEquals(0, grid.getValue(1, 1));
		assertFalse(grid.gameOver());

		grid.setValue(0, 0, 2);
		grid.setValue(1, 0, 2);
		grid.moveLeft();
		assertEquals(4, grid.getValue(0, 0));
		assertEquals(4, grid.getValue(0, 1));
		assertEquals(0, grid.getValue(1, 0));
		assertEquals(0, grid.getValue(1, 1));
		assertFalse(grid.gameOver());

		grid.moveDown();
		assertEquals(0, grid.getValue(0, 0));
		assertEquals(8, grid.getValue(0, 1));
		assertEquals(0, grid.getValue(1, 0));
		assertEquals(0, grid.getValue(1, 1));
		assertFalse(grid.gameOver());

		grid.setValue(0, 0, 2);
		grid.setValue(1, 0, 2);
		grid.moveRight();
		assertEquals(0, grid.getValue(0, 0));
		assertEquals(0, grid.getValue(0, 1));
		assertEquals(4, grid.getValue(1, 0));
		assertEquals(8, grid.getValue(1, 1));
		assertFalse(grid.gameOver());

		grid.setValue(0, 0, 2);
		grid.setValue(0, 1, 2);
		grid.moveDown();
		assertEquals(0, grid.getValue(0, 0));
		assertEquals(4, grid.getValue(0, 1));
		assertEquals(4, grid.getValue(1, 0));
		assertEquals(8, grid.getValue(1, 1));
		assertFalse(grid.gameOver());

		grid.setValue(0, 0, 2);
		grid.moveLeft();
		assertEquals(2, grid.getValue(0, 0));
		assertEquals(4, grid.getValue(0, 1));
		assertEquals(4, grid.getValue(1, 0));
		assertEquals(8, grid.getValue(1, 1));
		assertTrue(grid.gameOver());

	}

	@Test
	public void testGrid_4_times_4() {

		Grid grid = new Grid(4, 4);

		IntStream.range(0, 100).forEach(x -> {
			grid.setValue(0, 0, 2);
			grid.moveDown();
			grid.moveRight();
		});

		assertEquals(0, grid.getValue(0, 0));
		assertEquals(0, grid.getValue(0, 1));
		assertEquals(4, grid.getValue(0, 2));
		assertEquals(8, grid.getValue(0, 3));
		assertEquals(0, grid.getValue(1, 0));
		assertEquals(0, grid.getValue(1, 1));
		assertEquals(8, grid.getValue(1, 2));
		assertEquals(16, grid.getValue(1, 3));
		assertEquals(0, grid.getValue(2, 0));
		assertEquals(4, grid.getValue(2, 1));
		assertEquals(16, grid.getValue(2, 2));
		assertEquals(32, grid.getValue(2, 3));
		assertEquals(0, grid.getValue(3, 0));
		assertEquals(16, grid.getValue(3, 1));
		assertEquals(32, grid.getValue(3, 2));
		assertEquals(64, grid.getValue(3, 3));

		while (!grid.gameOver()) {
			grid.moveUp();
			grid.moveLeft();
			grid.setValue(3, 3, 2);
		}
		assertEquals(4, grid.getValue(0, 0));
		assertEquals(8, grid.getValue(0, 1));
		assertEquals(32, grid.getValue(0, 2));
		assertEquals(16, grid.getValue(0, 3));
		assertEquals(64, grid.getValue(1, 0));
		assertEquals(128, grid.getValue(1, 1));
		assertEquals(16, grid.getValue(1, 2));
		assertEquals(8, grid.getValue(1, 3));
		assertEquals(32, grid.getValue(2, 0));
		assertEquals(16, grid.getValue(2, 1));
		assertEquals(8, grid.getValue(2, 2));
		assertEquals(4, grid.getValue(2, 3));
		assertEquals(16, grid.getValue(3, 0));
		assertEquals(8, grid.getValue(3, 1));
		assertEquals(4, grid.getValue(3, 2));
		assertEquals(2, grid.getValue(3, 3));
	}

}
