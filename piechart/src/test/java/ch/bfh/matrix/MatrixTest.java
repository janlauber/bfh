/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class MatrixTest {

	final private Random random = new Random();

	private double[][] randomValues(int lines, int columns) {
		double[][] values = new double[lines][columns];
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				values[i][j] = random.nextDouble();
			}
		}
		return values;
	}

	private Matrix unitMatrix(int size) {
		double[][] values = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				values[i][j] = 0.0;
			}
			values[i][i] = 1.0;
		}
		return new Matrix(values);
	}

	@Test
	public void testConstructor() {
		// CHECKSTYLE:OFF MagicNumber
		Matrix a = new Matrix(new double[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } });
		assertEquals(3, a.getNbOfLines());
		assertEquals(2, a.getNbOfColumns());

		a = new Matrix(new double[][] { { 1 }, { 2 } });
		assertEquals(2, a.getNbOfLines());
		assertEquals(1, a.getNbOfColumns());

		a = new Matrix(randomValues(33, 44));
		assertEquals(33, a.getNbOfLines());
		assertEquals(44, a.getNbOfColumns());
	}

	@Test
	public void testConstructorException() {
		assertThrows(Throwable.class, () -> new Matrix(new double[][] { { 1 }, { 3, 4 } }));
		assertThrows(Throwable.class, () -> new Matrix(new double[2][0]));
		assertThrows(Throwable.class, () -> new Matrix(new double[2][]));
		assertThrows(Throwable.class, () -> new Matrix(null));
	}

	@Test
	public void testImmutability() {
		double[][] values = new double[][] { { 1, 2, 3, 4 } };
		Matrix a = new Matrix(values);
		values[0][0] = 99;
		assertEquals(1, a.get(0, 0));

		Matrix b = new Matrix(new double[][] { { 0, 1, 2, 3 }, { 4, 5, 6, 7 } });
		Matrix c = new Matrix(new double[][] { { 0, 1, 2, 3 }, { 4, 5, 6, 7 } });
		b.multiply(555);
		c.add(b);
		b.transpose();
		c.multiply(b.transpose());
		assertEquals(b, c);
	}

	@Test
	public void testGet() {
		double[][] values = randomValues(8, 10);
		Matrix a = new Matrix(values);
		for (int i = 0; i < a.getNbOfLines(); i++) {
			for (int j = 0; j < a.getNbOfColumns(); j++) {
				assertEquals(values[i][j], a.get(i, j));
			}
		}
	}

	@Test
	public void testGetException() {
		double[][] values = randomValues(8, 10);
		Matrix a = new Matrix(values);
		assertThrows(Throwable.class, () -> a.get(-1, 1));
		assertThrows(Throwable.class, () -> a.get(1, -1));
		assertThrows(Throwable.class, () -> a.get(7, 10));
		assertThrows(Throwable.class, () -> a.get(8, 9));
	}

	@Test
	public void testTranspose() {
		Matrix a = new Matrix(randomValues(8, 10));
		Matrix b = a.transpose();
		Matrix c = b.transpose();

		assertEquals(a.getNbOfLines(), b.getNbOfColumns());
		assertEquals(a.getNbOfColumns(), b.getNbOfLines());
		assertEquals(a, c);
		for (int i = 0; i < a.getNbOfLines(); i++) {
			assertEquals(a.get(i, i), b.get(i, i));
			assertEquals(b.get(i, i), c.get(i, i));
			for (int j = 0; j < a.getNbOfColumns(); j++) {
				assertEquals(a.get(i, j), b.get(j, i));
				assertEquals(c.get(i, j), b.get(j, i));
			}
		}
	}

	@Test
	public void testMultiplyScalar() {
		Matrix a = new Matrix(new double[][] { { 1, 2, 3 } });
		Matrix b = new Matrix(new double[][] { { 10, 20, 30 } });
		assertEquals(b, a.multiply(10.0));

		Matrix c = new Matrix(randomValues(10, 8));
		assertEquals(c, c.multiply(1.0e-8).multiply(1.0e8));

		assertEquals(c, c.multiply(1.0));

		Matrix d = c.multiply(0.0);
		assertEquals(0.0, d.get(5, 5));
	}

	@Test
	public void testMultiplyMatrix() {
		Matrix a = new Matrix(new double[][] { { 5.0, 4.0, 3.0 }, { 6.0, 5.0, 4.0 }, });
		Matrix b = new Matrix(new double[][] { { 1.0, 4.0 }, { 2.0, 5.0 }, { 3.0, 6.0 }, });
		Matrix c = new Matrix(new double[][] { { 22.0, 58.0 }, { 28.0, 73.0 }, });
		assertEquals(a.multiply(b), c);

		Matrix d = b.multiply(c);
		assertEquals(b.getNbOfLines(), d.getNbOfLines());
		assertEquals(c.getNbOfColumns(), d.getNbOfColumns());

		Matrix e = new Matrix(randomValues(5, 3));
		Matrix i = unitMatrix(3);
		assertEquals(e, e.multiply(i));
		assertEquals(i, i.multiply(i));
	}

	@Test
	public void testMultiplyMatrixException() {
		Matrix a = new Matrix(randomValues(4, 6));
		assertThrows(Throwable.class, () -> a.multiply(a));
		Matrix b = new Matrix(randomValues(6, 4));
		assertThrows(Throwable.class, () -> b.multiply(b));
		assertThrows(Throwable.class, () -> a.multiply(unitMatrix(5)));
		assertThrows(Throwable.class, () -> b.multiply(unitMatrix(5)));
		assertThrows(Throwable.class, () -> a.multiply(unitMatrix(4)));
		assertThrows(Throwable.class, () -> b.multiply(unitMatrix(6)));
	}

	@Test
	public void testAdd() {
		Matrix a = new Matrix(new double[][] { { 5.0, 4.0, 3.0, 2.0 }, { 6.0, 5.0, 4.0, 3.0 }, });
		Matrix b = new Matrix(new double[][] { { 1.0, 2.0, 3.0, 4.0 }, { 5.0, 6.0, 7.0, 8.0 }, });
		Matrix minusB = new Matrix(new double[][] { { -1.0, -2.0, -3.0, -4.0 }, { -5.0, -6.0, -7.0, -8.0 }, });
		Matrix c = new Matrix(new double[][] { { 6.0, 6.0, 6.0, 6.0 }, { 11.0, 11.0, 11.0, 11.0 }, });
		assertEquals(c, a.add(b));
		assertEquals(a, a.add(b).add(minusB));

		Matrix d = new Matrix(randomValues(4, 6));
		Matrix e = new Matrix(randomValues(6, 4));
		assertNotNull(d.add(e.transpose()));
	}

	@Test
	public void testAddException() {
		Matrix a = new Matrix(randomValues(4, 6));
		assertThrows(Throwable.class, () -> a.add(a.transpose()));
		Matrix b = new Matrix(randomValues(6, 4));
		assertThrows(Throwable.class, () -> b.add(b.transpose()));
		assertThrows(Throwable.class, () -> a.add(b));
	}

	@Test
	public void testEqualsAndHashCode() {
		double[][] values = randomValues(8, 10);
		double[][] values2 = new double[8][10];
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				double d = values[i][j] * 1e-8;
				values2[i][j] = d * 1e8;
			}
		}
		Matrix a = new Matrix(values);
		Matrix b = new Matrix(values2);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());

		a = unitMatrix(55);
		b = unitMatrix(55);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testNotEquals() {
		double[][] values = randomValues(8, 10);
		Matrix a = new Matrix(values);
		values[4][5] += 1e7;
		Matrix b = new Matrix(values);
		assertNotEquals(a, b);

		Matrix c = b.transpose();
		assertNotEquals(c, b);

		assertNotEquals(unitMatrix(4), unitMatrix(5));

		assertNotEquals(c, null);
		assertNotEquals(null, a);
	}
}
