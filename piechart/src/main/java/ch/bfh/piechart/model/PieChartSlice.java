/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

import ch.bfh.matrix.Matrix;

/**
 * interface hiding details of a slice in a pie chart.
 */
public interface PieChartSlice {

	/**
	 * Returns the coordinates of the slice as a matrix. The matrix must contain 3
	 * columns, each representing a position: 0 : center position, 1 : start
	 * position of the arc, 2 : end position of the arc.
	 *
	 * @return the matrix containing the coordinates
	 */
	Matrix getCoords();

	/**
	 * Called when the user clicks the slice in the user interface.
	 */
	void move();

}
