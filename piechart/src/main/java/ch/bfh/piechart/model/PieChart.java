/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

import ch.bfh.matrix.Matrix;

/**
 * interface hiding details of pie charts.
 */
public interface PieChart {

	/**
	 * Normalized translation vector.
	 * Use this vector to transform the coordinates of an attached slices to a detached slice.
	 */
	Matrix DETACH_VECTOR = new Matrix(new double[][]{
		{0.0},
		{0.2}, // 20% of the radius from the center
		{1.0}
	});

	/**
		* Returns the number of slices in this pie chart.
		*
		* @return number of slices
		*/
	int getNbOfSlices();

	/**
		* Returns the indexed slice of the pie chart.
		*
		* @param index index of a slice
		* @return a slice object
		*/
	PieChartSlice getSlice(int index);

	/**
		* Sets the center position and the radius for the pie chart.
		* This method is called from the UI when the size of the pane has changed.
		*
		* @param x the x-value of the center position
		* @param y the y-value of the center position
		* @param r the radius for the chart
		*/
	void setPosAndRadius(double x, double y, double r);

	/**
		* Adds an observer to the list of observers for this pie chart.
		*
		* @param observer reference to the observer
		*/
	void addObserver(PieChartObserver observer);

	/**
		* Called when the user clicks a slice in the user interface.
		*
		* @param index the index of the slice clicked
		*/
	void onClick(int index);

}
