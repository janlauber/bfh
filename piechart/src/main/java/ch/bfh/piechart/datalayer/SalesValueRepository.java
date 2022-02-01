/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.datalayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The repository for sales value records. Database access occurs via JDBC.
 */
public class SalesValueRepository {
	// TODO Complete JDBC-SQL statement
	private static final String FIND_ALL_QUERY = "SELECT * FROM SalesValue";
	// TODO Complete JDBC-SQL statement
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM SalesValue WHERE id = ?";
	// TODO Complete JDBC-SQL statement
	private static final String FIND_BY_PRODUCTID_QUERY = "SELECT * FROM SalesValue WHERE productID = ?";
	// TODO Complete JDBC-SQL statement
	private static final String INSERT_QUERY = "INSERT INTO SalesValue (productId, value, percentage) "
			+
			"VALUES(?, ?, ?)";
	// TODO Complete JDBC-SQL statement
	private static final String UPDATE_QUERY = "UPDATE SalesValue SET productId = ?, value = ?, "
			+
			"percentage = ? WHERE id = ?";
	// TODO Complete JDBC-SQL statement
	private static final String DELETE_QUERY = "DELETE FROM SalesValue WHERE id = ?";

	private static final Logger LOGGER = Logger.getLogger(SalesValueRepository.class.getName());

	private final Connection connection;

	public SalesValueRepository(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Reads all sales values from the database. Do not use this method if a huge
	 * amount of sales values must be expected.
	 *
	 * @return all sales values found in the database
	 * @throws SQLException if an error occurs when talking with the database
	 */
	public List<SalesValue> findAll() throws SQLException {
		List<SalesValue> salesValues = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
			LOGGER.info("Executing query: " + statement);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				salesValues.add(getSalesValue(results));
			}
			return salesValues;
		}
	}

	/**
	 * Finds a sales value, given its unique id.
	 *
	 * @param id the id of a sales value
	 * @return a sales value
	 * @throws SQLException if an error occurs when talking with the database
	 */
	public SalesValue findById(int id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
			// CHECKSTYLE:OFF MagicNumber
			statement.setInt(1, id);
			LOGGER.info("Executing query: " + statement);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return getSalesValue(results);
			} else {
				return null;
			}
		}
	}

	/**
	 * Finds a sales value, given its (potentially) non-unique product id. If
	 * several sales value records share the same product id, only one of them is
	 * silently returned. Null is returned of no sales value record matches the
	 * given product id.
	 *
	 * @param productId a product id of a sales value
	 * @return a sales value or null
	 * @throws SQLException if an error occurs when talking with the database
	 */
	public SalesValue findByProductId(int productId) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(FIND_BY_PRODUCTID_QUERY)) {
			// CHECKSTYLE:OFF MagicNumber
			statement.setInt(1, productId);
			LOGGER.info("Executing query: " + statement);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return getSalesValue(results);
			} else {
				return null;
			}
		}
	}

	/**
	 * Persists a sales value object. The id of the given object is set as well as
	 * returned by this method.
	 *
	 * @param salesValue a not yet persisted sales value object
	 * @return the id obtained by persisting the given sales value object
	 * @throws SQLException if an error occurs when talking with the database
	 */
	public int persist(SalesValue salesValue) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			// CHECKSTYLE:OFF MagicNumber
			statement.setInt(1, salesValue.getProductId());
			statement.setInt(2, salesValue.getValue());
			statement.setDouble(3, salesValue.getPercentage());
			LOGGER.info("Executing query: " + statement);
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			int id = keys.getInt(1);
			salesValue.setId(id);
			return id;
		}
	}

	/**
	 * Updates the database record for the given, previously persisted sales value
	 * object.
	 *
	 * @param salesValue a sales value object
	 * @return if any element of the database record was changed.
	 * @throws SQLException if an error occurs when talking with the database
	 */
	public boolean update(SalesValue salesValue) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			// CHECKSTYLE:OFF MagicNumber
			statement.setInt(1, salesValue.getProductId());
			statement.setInt(2, salesValue.getValue());
			statement.setDouble(3, salesValue.getPercentage());
			statement.setInt(4, salesValue.getId());
			LOGGER.info("Executing query: " + statement);
			return statement.executeUpdate() > 0;
		}
	}

	/**
	 * Deletes the database record matching the given id.
	 *
	 * @param id an id
	 * @return true if a matching record was successfully deleted
	 * @throws SQLException if an error occurs when talking with the database
	 */
	public boolean delete(long id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
			// CHECKSTYLE:OFF MagicNumber
			statement.setLong(1, id);
			LOGGER.info("Executing query: " + statement);
			return statement.executeUpdate() > 0;
		}
	}

	/**
	 * Helper method that returns a sales value object, given a single result of a
	 * database query.
	 *
	 * @param results the result of a database query
	 * @return as sales value object
	 * @throws SQLException if an error occurs when talking with the database
	 */
	private SalesValue getSalesValue(ResultSet results) throws SQLException {
		// Assertion: Every sales data item has an id, a productId and a value.
		// However, the value of percentage may be NULL which is automatically converted
		// to 0.
		SalesValue salesValue = new SalesValue(results.getInt("id"), results.getInt("productId"),
				results.getInt("value"));
		salesValue.setPercentage(results.getDouble("percentage"));
		return salesValue;
	}
}
