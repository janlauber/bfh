/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The class establishes and manages a connection to a database. Applications
 * may obtain several connection objects, thus, opening many connections to the
 * same database. Close a connection if not used any longer.
 *
 * @author Stephan Fischli
 */
public class ConnectionManager {

	private static final String PROPERTY_FILE_PATH = "/jdbc.properties";

	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;

	/**
	 * Load database driver using the properties as specified in file
	 * PROPERTY_FILE_PATH.
	 */
	static {
		try {
			Properties props = new Properties();
			props.load(ConnectionManager.class.getResourceAsStream(PROPERTY_FILE_PATH));
			Class.forName(props.getProperty("database.driver"));
			URL = props.getProperty("database.url");
			USER = props.getProperty("database.user");
			PASSWORD = props.getProperty("database.password");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Obtain a connection to the database. Parameter <code>autoCommit</code> sets
	 * this connection's auto-commit mode to the given state. If a connection is in
	 * auto-commit mode, then all its SQL statements will be executed and committed
	 * as individual transactions. Otherwise, its SQL statements are grouped into
	 * transactions that are terminated by a call to either the method
	 * <code>commit</code> or the method <code>rollback</code>.
	 *
	 * @param autoCommit if <code>true</code>, sets this connection int auto-commit
	 *                   mode
	 * @return a database connection
	 */
	public static Connection getConnection(boolean autoCommit) {
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(autoCommit);
			return connection;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Makes all changes made since the previous commit/rollback permanent and
	 * releases any database locks currently held by this <code>Connection</code>
	 * object. This method should be used only when auto-commit mode has been
	 * disabled.
	 *
	 * @param connection a database connection
	 */
	public static void commit(Connection connection) {
		try {
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Undoes all changes made in the current transaction and releases any database
	 * locks currently held by this <code>Connection</code> object. This method
	 * should be used only when auto-commit mode has been disabled.
	 *
	 * @param connection a database connection
	 */
	public static void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Releases this <code>Connection</code> object's database and JDBC resources
	 * immediately instead of waiting for them to be automatically released. Calling
	 * the method <code>close</code> on a <code>Connection</code> object that is
	 * already closed is a no-op.
	 *
	 * @param connection a database connection
	 */
	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
