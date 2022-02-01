/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.datalayer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

public class ConnectionManagerTest {

	@Test
	public void testConnectionNonAutoCommit() throws Exception {
		Connection connection;
		connection = ConnectionManager.getConnection(false);
		assertNotNull(connection, "Could not obtain connection to database");
		connection.close();
	}

	@Test
	public void testConnectionAutoCommit() throws Exception {
		Connection connection = ConnectionManager.getConnection(true);
		assertNotNull(connection, "Could not obtain connection to database");
		connection.close();
	}

	@Test
	public void testSeparateConnections() throws Exception {
		Connection connection1 = ConnectionManager.getConnection(true);
		Connection connection2 = ConnectionManager.getConnection(true);
		assertNotNull(connection1, "Could not obtain connection 1 to database");
		assertNotNull(connection2, "Could not obtain connection 2 to database");
		assertNotSame(connection1, connection2);
		connection1.close();
		connection2.close();
	}
}
