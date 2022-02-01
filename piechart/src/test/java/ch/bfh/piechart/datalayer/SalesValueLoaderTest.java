/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.datalayer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SalesValueLoaderTest {

	@BeforeAll
	public static void init() throws Exception {
		SalesValueLoader.loadSalesValues();
	}

	@Test
	public void testloadSalesValues() throws Exception {
		Connection connection = ConnectionManager.getConnection(true);
		SalesValueRepository repository = new SalesValueRepository(connection);

		List<SalesValue> salesValues = repository.findAll();
		assertNotNull(salesValues);
		assertTrue(salesValues.size() > 0);
	}
}
