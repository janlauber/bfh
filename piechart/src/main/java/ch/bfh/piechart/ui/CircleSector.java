/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.ui;

import ch.bfh.matrix.Matrix;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * JavaFX control representing a circle sector. To be used as visual
 * representation of a slice within a pie chart.
 */
public class CircleSector extends Path {

	static final int CLASSES = 10;
	static int classIndex = 0;

	/**
	 * Creates the control and sets the style class.
	 */
	public CircleSector() {
		super();

		getStyleClass().add("color" + classIndex);
		classIndex = (classIndex + 1) % CLASSES;
	}

	/**
	 * Updates the visual representation based on given positions.
	 *
	 * @param centerX x-value of the center.
	 * @param centerY y-value of the center.
	 * @param startX  x-value of the arc's start.
	 * @param startY  y-value of the arc's start.
	 * @param endX    x-value of the arc's end.
	 * @param endY    y-value of the arc's end.
	 */
	void update(double centerX, double centerY, double startX, double startY, double endX, double endY) {

		double r = Math.sqrt(Math.pow(startX - centerX, 2.0) + Math.pow(startY - centerY, 2.0));
		getElements().clear();
		getElements().add(new MoveTo(centerX, centerY));
		getElements().add(new LineTo(startX, startY));
		getElements().add(new ArcTo(r, r, 0, endX, endY, false, false));
		getElements().add(new ClosePath());
	}

	/**
	 * Updates the visual representation based on positions given in a matrix. The
	 * matrix must contain 3 columns: 0 = center position, 1 = arc's start position,
	 * 2 ? arc's end position.
	 *
	 * @param pos Matrix containing positions.
	 */
	public void update(Matrix pos) {
		// CHECKSTYLE:OFF MagicNumber
		if (pos.getNbOfLines() == 2) {
			// matrix contains normal coordinates only
			update(pos.get(0, 0), pos.get(1, 0), pos.get(0, 1), pos.get(1, 1), pos.get(0, 2), pos.get(1, 2));
		} else if (pos.getNbOfLines() == 3) {
			// matrix contains homogeneous coordinates
			update(pos.get(0, 0) / pos.get(2, 0), pos.get(1, 0) / pos.get(2, 0), pos.get(0, 1) / pos.get(2, 1),
					pos.get(1, 1) / pos.get(2, 1), pos.get(0, 2) / pos.get(2, 2), pos.get(1, 2) / pos.get(2, 2));
		}
	}
}
