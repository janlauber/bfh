/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

/**
 * Interface for objects observing pie charts.
 */
public interface PieChartObserver {

	/**
	 * Notifies the observer that one or all slices need to be updated.
	 *
	 * @param slice the slice to be updated or null to indicate that all slices have
	 *              to be updated
	 */
	void update(PieChartSlice slice);
}
