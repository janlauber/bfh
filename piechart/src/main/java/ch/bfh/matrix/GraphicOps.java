/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.matrix;

/**
 * Helper class creating and applying matrices for graphic operations. All
 * methods use homogeneous coordinates so that operations can easily be applied
 * using matrix multiplication only.
 */
public class GraphicOps {

	/** A unit vector in y direction **/
	public static final Matrix UNIT_Y_VECTOR = new Matrix(new double[][] { { 0.0 }, { 1.0 }, { 1.0 } });

	/** A null vector **/
	public static final Matrix NULL_VECTOR = new Matrix(new double[][] { { 0.0 }, { 0.0 }, { 1.0 } });

	/**
	 * Returns a rotation matrix for the given angle.
	 *
	 * @param angle rotation angle
	 * @return rotation matrix
	 */
	public static Matrix rotate(double angle) {
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		return (new Matrix(new double[][] { { c, -s, 0 }, { s, c, 0 }, { 0, 0, 1 } }));
	}

	/**
	 * Applies rotation to the given coordinates.
	 *
	 * @param coords coordinates to be rotated
	 * @param angle  rotation angle
	 * @return rotated coordinates
	 */
	public static Matrix rotate(Matrix coords, double angle) {
		return rotate(angle).multiply(coords);
	}

	/**
	 * Returns a scaling matrix for the given factor.
	 *
	 * @param factor scaling factor
	 * @return scaling matrix.
	 */
	public static Matrix scale(double factor) {
		return (new Matrix(new double[][] { { factor, 0, 0 }, { 0, factor, 0 }, { 0, 0, 1 } }));
	}

	/**
	 * Applies scaling to the given coordinates.
	 *
	 * @param coords coordinates to be scaled
	 * @param factor scaling factor
	 * @return scaled coordinates
	 */
	public static Matrix scale(Matrix coords, double factor) {
		return scale(factor).multiply(coords);
	}

	/**
	 * Returns a translation matrix for the given values.
	 *
	 * @param deltaX translation in x direction
	 * @param deltaY translation in y direction
	 * @return translation matrix
	 */
	public static Matrix translate(double deltaX, double deltaY) {
		return (new Matrix(new double[][] { { 1, 0, deltaX }, { 0, 1, deltaY }, { 0, 0, 1 } }));
	}

	/**
	 * Applies translation to the given coordinates.
	 *
	 * @param coords coordinates to be translated
	 * @param deltaX translation in x direction
	 * @param deltaY translation in y direction
	 * @return translated coordinates
	 */
	public static Matrix translate(Matrix coords, double deltaX, double deltaY) {
		return translate(deltaX, deltaY).multiply(coords);
	}

	/**
	 * Returns a translation matrix for the given translation vector.
	 *
	 * @param vector translation vector
	 * @return translation matrix
	 */
	public static Matrix translate(Matrix vector) {
		return translate(vector.get(0, 0) / vector.get(2, 0), vector.get(1, 0) / vector.get(2, 0));
	}

	/**
	 * Applies translation to the given coordinates.
	 *
	 * @param coords coordinates to be translated
	 * @param vector translation vector
	 * @return translated coordinates
	 */
	public static Matrix translate(Matrix coords, Matrix vector) {
		return translate(vector).multiply(coords);
	}

}
