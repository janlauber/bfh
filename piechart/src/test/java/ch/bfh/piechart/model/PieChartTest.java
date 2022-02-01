/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.bfh.matrix.Matrix;
import ch.bfh.piechart.datalayer.SalesValue;

public class PieChartTest {

	private PieChart create() {
		List<SalesValue> values = new ArrayList<>();
		// CHECKSTYLE:OFF MagicNumber
		for (int i = 0; i < 4; i++) {
			SalesValue value = new SalesValue(i, 100);
			value.setPercentage(25.0);
			values.add(value);
		}
		return new ConcretePieChart(values);
	}

	private void testCoord(Matrix coords, int index, double x, double y) {
		double actualX = (coords.getNbOfLines() == 2) ? coords.get(0, index)
				: coords.get(0, index) / coords.get(2, index);
		double actualY = (coords.getNbOfLines() == 2) ? coords.get(1, index)
				: coords.get(1, index) / coords.get(2, index);
		assertEquals(x, actualX, 1e-5);
		assertEquals(y, actualY, 1e-5);
	}

	private void testCoords(PieChart chart, int x, int y, int r) {
		Matrix coords = chart.getSlice(0).getCoords();
		assertEquals(3, coords.getNbOfColumns());
		testCoord(coords, 0, x, y);
		testCoord(coords, 1, x, y + r);
		testCoord(coords, 2, x + r, y);

		coords = chart.getSlice(1).getCoords();
		assertEquals(3, coords.getNbOfColumns());
		testCoord(coords, 0, x, y);
		testCoord(coords, 1, x + r, y);
		testCoord(coords, 2, x, y - r);

		coords = chart.getSlice(2).getCoords();
		assertEquals(3, coords.getNbOfColumns());
		testCoord(coords, 0, x, y);
		testCoord(coords, 1, x, y - r);
		testCoord(coords, 2, x - r, y);

		coords = chart.getSlice(3).getCoords();
		assertEquals(3, coords.getNbOfColumns());
		testCoord(coords, 0, x, y);
		testCoord(coords, 1, x - r, y);
		testCoord(coords, 2, x, y + r);
	}

	@Test
	public void testCreate() {
		PieChart chart = create();
		assertEquals(4, chart.getNbOfSlices());
	}

	@Test
	public void testScale() {
		PieChart chart = create();
		chart.setPosAndRadius(0, 0, 1000.0);
		testCoords(chart, 0, 0, 1000);
	}

	@Test
	public void testScaleAndMove() {
		PieChart chart = create();
		chart.setPosAndRadius(1000.0, 1000.0, 800.0);
		testCoords(chart, 1000, 1000, 800);
	}
}
