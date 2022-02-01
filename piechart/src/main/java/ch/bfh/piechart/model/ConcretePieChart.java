/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.matrix.GraphicOps;
import ch.bfh.matrix.Matrix;
import ch.bfh.piechart.datalayer.SalesValue;

/**
 * Represents a pie chart.
 */
class ConcretePieChart implements PieChart {

	private final List<PieChartSlice> slices = new ArrayList<>();

	public static class PieChartSlice implements ch.bfh.piechart.model.PieChartSlice {

		private final double angleStart;
		private final double angleEnd;
		private double radius;
		private boolean isExploded = false;


		private Matrix coordinates;
		private Matrix startVector;

		PieChartSlice(double angleStart, double angleEnd) {
			coordinates = new Matrix(new double[][] {
					{0.0, GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR,
							angleStart).get(0, 0), GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR, angleEnd).get(0, 0)},
					{0.0, GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR,
							angleStart).get(1, 0), GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR, angleEnd).get(1, 0)},
					{1.0, 1.0, 1.0}});
			this.angleStart = angleStart;
			this.angleEnd = angleEnd;
		}

		public void move() {
			// CHECKSTYLE:OFF MagicNumber
			// expand the coordinates matrix by DETACH_VECTOR in the correct direction
			if (isExploded) {
				coordinates = startVector;
				isExploded = false;
			} else {
				startVector = coordinates;
				// get radius from start vector to center
				radius = Math.sqrt(Math.pow(coordinates.get(0, 0), 2) + Math.pow(coordinates.get(1, 0), 2));
				// get 20% of radius
				double radius20 = radius * 0.2;
				// get the coordinates 20% of radius away from the center
				for (int i = 0; i < radius20; i++) {
					coordinates = GraphicOps.translate(coordinates, GraphicOps.rotate(DETACH_VECTOR, angleStart));
					coordinates = GraphicOps.translate(coordinates, GraphicOps.rotate(DETACH_VECTOR, angleEnd));
				}
				isExploded = true;
			}
			// CHECKSTYLE:ON MagicNumber
		}

		public Matrix getCoords() {
			return coordinates;
		}

		public void setCoords(Matrix coords) {
			coordinates = coords;
		}
	}

	/**
	 * Creates a pie chart based on percentages in the list of SalesValue objects.
	 *
	 * @param values sales value objects
	 */
	ConcretePieChart(List<SalesValue> values) {
		double angleStart = Math.PI * 2;
		for (SalesValue value : values) {
			// CHECKSTYLE:OFF MagicNumber
			double angleEnd = angleStart - (value.getPercentage() / 100) * Math.PI * 2;
			slices.add(new PieChartSlice(angleStart, angleEnd));
			angleStart = angleEnd;
		}
	}

	/**
	 * Returns the number of slices in this pie chart.
	 *
	 * @return number of slices
	 */
	@Override
	public int getNbOfSlices() {
		return slices.size();
	}

	/**
	 * Returns the indexed slice of the pie chart.
	 *
	 * @param index slice index
	 * @return slice object
	 */
	@Override
	public PieChartSlice getSlice(int index) {
		return slices.get(index);
	}

	/**
	 * Sets the center position and the radius for the chart.
	 *
	 * @param x the x-value of the center position
	 * @param y the y-value of the center position
	 * @param r the radius for the chart
	 */
	@Override
	public void setPosAndRadius(double x, double y, double r) {
		// translate matrix and scaling matrix set
		Matrix translation = GraphicOps.translate(x, y);
		Matrix scaling = GraphicOps.scale(r);

		for (PieChartSlice slice : slices) {
			Matrix mid = new Matrix(new double[][] {
					{slice.getCoords().get(0, 0)},
					{slice.getCoords().get(1, 0)},
					{1.0}});
			Matrix begin = new Matrix(new double[][] {
					{slice.getCoords().get(0, 1)},
					{slice.getCoords().get(1, 1)},
					{1.0}});
			Matrix end = new Matrix(new double[][] {
					{slice.getCoords().get(0, 2)},
					{slice.getCoords().get(1, 2)},
					{1.0}});

			Matrix newMid = translation.multiply(scaling).multiply(mid);
			Matrix newBegin = translation.multiply(scaling).multiply(begin);
			Matrix newEnd = translation.multiply(scaling).multiply(end);

			// set new coordinates
			slice.setCoords(new Matrix(new double[][] {
					{newMid.get(0, 0), newBegin.get(0, 0), newEnd.get(0, 0)},
					{newMid.get(1, 0), newBegin.get(1, 0), newEnd.get(1, 0)},
					{1.0, 1.0, 1.0}}));

		}
		notifyObservers(null);

	}

	// ---------------------------------
	// implement observable pattern

	private final List<PieChartObserver> observers = new ArrayList<>();

	/**
	 * To be called when observers need to be notified that one or all slices need
	 * to be updated.
	 *
	 * @param slice the slice to be updated or null if all slices need to be updated
	 */
	@SuppressWarnings("unused")
	private void notifyObservers(PieChartSlice slice) {
		observers.forEach(observer -> observer.update(slice));
	}

	/**
	 * Adds an observer to the list of observers.
	 *
	 * @param observer reference to the observer.
	 */
	@Override
	public void addObserver(PieChartObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 *
	 * @param index the index of the slice clicked
	 */
	@Override
	public void onClick(int index) {
		PieChartSlice slice = getSlice(index);
		slice.move();
		notifyObservers(slice);
	}
}
