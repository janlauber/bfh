/*
	* Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
	*/
package ch.bfh.matrix;

/**
	* Represents a two-dimensional matrix of double-values. Objects are immutable
	* and methods implementing matrix operations always return new matrix objects.
	*/
public class Matrix {

	// expected precision in floating point calculations
	public static final double EPSILON = 1e-10;

	// the matrix values in lines and columns
	protected double[][] values;

	/**
		* Creates a matrix with values given in a two-dimensional array. First
		* dimension represents lines, second the columns.
		*
		* @param values a non-empty and rectangular two-dimensional array
		*/
	public Matrix(final double[][] values) throws IllegalArgumentException {
		// Check if the array is not null and not empty
		if (values == null || values.length == 0 || values[0].length == 0) {
			throw new IllegalArgumentException("Array is null or empty");
		} else {
			// Check if all lines have the same length
			for (int i = 1; i < values.length; i++) {
				if (values[i].length != values[0].length) {
					throw new IllegalArgumentException("Array is not rectangular");
				}
			}
			// Copy the values
			this.values = new double[values.length][values[0].length];
			for (int i = 0; i < values.length; i++) {
				System.arraycopy(values[i], 0, this.values[i], 0, values[0].length);
			}
		}
	}

	/**
		* @return the number of lines in this matrix
		*/
	public int getNbOfLines() {
		return this.values.length;
	}

	/**
		* @return the number of columns in this matrix
		*/
	public int getNbOfColumns() {
		return this.values[0].length;
	}

	/**
		* Returns the value at the given position in the matrix.
		*
		* @param line indicates the index for the line
		* @param col  indicates the index for the column
		* @return the value at the indicated position
		*/
	public double get(final int line, final int col) {
		return this.values[line][col];
	}

	public void set(final int line, final int col, final double value) {
		this.values[line][col] = value;
	}

	/**
		* Calculates the transpose of this matrix.
		*
		* @return the transpose of this matrix
		*/
	public Matrix transpose() {
		// Transpose the matrix
		double[][] transposed = new double[this.values[0].length][this.values.length];
		for (int i = 0; i < this.values.length; i++) {
			for (int j = 0; j < this.values[0].length; j++) {
				transposed[j][i] = this.values[i][j];
			}
		}
		return new Matrix(transposed);
	}

	/**
		* Calculates the product of this matrix with the given scalar value.
		*
		* @param scalar the scalar value to multiply with
		* @return the scalar product
		*/
	public Matrix multiply(final double scalar) {
		// Multiply the matrix with the scalar
		double[][] multiplied = new double[this.values.length][this.values[0].length];
		for (int i = 0; i < this.values.length; i++) {
			for (int j = 0; j < this.values[0].length; j++) {
				multiplied[i][j] = this.values[i][j] * scalar;
			}
		}
		return new Matrix(multiplied);
	}

	/**
		* Calculates the product of this matrix with another matrix.
		*
		* @param other the other matrix to multiply with
		* @return the matrix product
		*/
	public Matrix multiply(final Matrix other) {
		// Check if the other matrix is valid
		if (other.getNbOfLines() == this.getNbOfColumns()) {
			// Multiply the matrix with the other matrix
			double[][] multiplied = new double[this.values.length][other.values[0].length];
			for (int i = 0; i < this.values.length; i++) {
				for (int j = 0; j < other.values[0].length; j++) {
					for (int k = 0; k < this.values[0].length; k++) {
						multiplied[i][j] += this.values[i][k] * other.values[k][j];
					}
				}
			}
			return new Matrix(multiplied);
		} else {
			throw new IllegalArgumentException("Matrix dimensions do not match");
		}
	}

	/**
		* Calculates the sum of this matrix with another matrix.
		*
		* @param other the other matrix to add with
		* @return the matrix sum
		*/
	public Matrix add(final Matrix other) {
		// Check if the other matrix is valid
		if (other != null
				&& other.getNbOfLines() == this.getNbOfLines()
				&& other.getNbOfColumns() == this.getNbOfColumns()) {
			// Add the two matrices
			double[][] added = new double[this.values.length][this.values[0].length];
			for (int i = 0; i < this.values.length; i++) {
				for (int j = 0; j < this.values[0].length; j++) {
					added[i][j] = this.values[i][j] + other.values[i][j];
				}
			}
			return new Matrix(added);
		} else {
			throw new IllegalArgumentException("Matrix dimensions do not match");
		}
	}

	@Override
	public int hashCode() {
		// Calculate the hash code for every value in the matrix
		int hash = 0;
		for (double[] value : this.values) {
			for (int j = 0; j < this.values[0].length; j++) {
				hash += (int) (value[j] * 1000);
			}
		}
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		// Check if the object is valid
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		// Check if the matrices are equal
		Matrix other = (Matrix) obj;
		if (this.values.length == other.values.length && this.values[0].length == other.values[0].length) {
			for (int i = 0; i < this.values.length; i++) {
				for (int j = 0; j < this.values[0].length; j++) {
					// Check with EPSILON if the values are equal
					if (Math.abs(this.values[i][j] - other.values[i][j]) > EPSILON) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		// Create the string representation
		StringBuilder sb = new StringBuilder();
		for (double[] value : this.values) {
			for (int j = 0; j < this.values[0].length; j++) {
				sb.append(value[j]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
