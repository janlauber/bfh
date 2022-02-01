/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.datalayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SalesValueRepositoryTest {

	private static SalesValueRepository repository;
	private static final SalesValue salesValue = new SalesValue(11, 100);

	@BeforeAll
	public static void init() {
		Connection connection = ConnectionManager.getConnection(true);
		repository = new SalesValueRepository(connection);
	}

	@Test
	@Order(1)
	public void persistSalesValue() throws SQLException {
		int id = repository.persist(salesValue);
		SalesValue persistedSalesValue = repository.findById(id);
		assertEquals(salesValue, persistedSalesValue);
	}

	@Test
	@Order(2)
	public void findSalesValue() throws SQLException {
		List<SalesValue> salesValues = repository.findAll();
		assertTrue(salesValues.contains(salesValue));
		SalesValue foundSalesValue = repository.findById(salesValue.getId());
		assertEquals(salesValue, foundSalesValue);
	}

	@Test
	@Order(3)
	public void updateSalesValue() throws SQLException {
		salesValue.setPercentage(50);
		repository.update(salesValue);
		SalesValue updatedSalesValue = repository.findById(salesValue.getId());
		assertEquals(salesValue, updatedSalesValue);
	}

	@Test
	@Order(4)
	public void deleteSalesValue() throws SQLException {
		repository.delete(salesValue.getId());
		SalesValue deletedSalesValue = repository.findById(salesValue.getId());
		assertNull(deletedSalesValue);
	}
}
