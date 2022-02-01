package ch.bfh.piechart.model;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.matrix.GraphicOps;
import ch.bfh.matrix.Matrix;

public class MockPieChart implements PieChart {

	private final List<MockSlice> slices = new ArrayList<>();

	public MockPieChart() {
		double startAngle = 2.0 * Math.PI;
		// CHECKSTYLE:OFF MagicNumber
		for (int i = 0; i < 4; i++) {
			double endAngle = startAngle - 0.25 * 2.0 * Math.PI;
			slices.add(new MockSlice(startAngle, endAngle));
			startAngle = endAngle;
		}
	}

	@Override
	public int getNbOfSlices() {
		return 4;
	}

	@Override
	public PieChartSlice getSlice(int index) {
		return slices.get(index);
	}

	@Override
	public void setPosAndRadius(double x, double y, double r) {
		// not yet implemented
	}

	@Override
	public void addObserver(PieChartObserver observer) {
		// not yet implemented
	}

	@Override
	public void onClick(int index) {
		// not yet implemented
	}

	public static class MockSlice implements PieChartSlice {

		private final Matrix coords;

		public MockSlice(double startAngle, double endAngle) {
			Matrix start = GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR, startAngle);
			Matrix end = GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR, endAngle);
			coords = new Matrix(new double[][]{{0.0, start.get(0, 0), end.get(0, 0)},
				{0.0, start.get(1, 0), end.get(1, 0)}, {1.0, 1.0, 1.0}});
		}

		@Override
		public Matrix getCoords() {
			// with fixed screen dimensions
			double width = 320;
			double height = 240;
			double r = Math.min(width, height) * 0.8;
			// use the GraphicOps to transform the coordinates to the right size and position
			Matrix transformation =
											GraphicOps.translate(width, height).multiply(GraphicOps.scale(r));
			return transformation.multiply(coords);
		}

		@Override
		public void move() {
			// not yet implemented
		}
	}
}
