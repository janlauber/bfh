package ch.bfh.piechart.datalayer;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;

/**
	* Reads data from a CSV file (having ";" as a value separator) and stores it in
	* a database using a <code>Connection</code> object for that database. Uses the
	* repository for sales values. Data in the file being read must match the
	* database scheme. <strong>Application should call static method
	* <code>loadSalesValues()</code> only once.</strong>
	*
	* @author Eric Dubuis
	*/
public class SalesValueLoader {
	/**
		* The file containing the data.
		*/
	public static final String FILENAME = "salesvalues.txt";

	// To prevent double loading
	private static boolean done = false;

	/**
		* Loads data found in <code>FILENAME</code> and stores it in the database
		* obtained by a <code>Connection</code> object. A <code>RuntimeException</code>
		* is thrown if there is any kind of error. The exception wraps the effective
		* exception indicating the cause.
		*
		* @throws RuntimeException if any kind of error occurs
		*/
	public static void loadSalesValues() {
		try {
			if (!done) {
				Connection connection = ConnectionManager.getConnection(true);
				SalesValueRepository repository = new SalesValueRepository(connection);

				Path file = Path.of(SalesValueLoader.class.getClassLoader().getResource(FILENAME).toURI());
				List<String> lines = Files.readAllLines(file, Charset.forName("UTF-8"));

				for (String line : lines) {
					String[] items = line.split(";");
					SalesValue salesValue = new SalesValue(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
					repository.persist(salesValue);
				}

				connection.close();
				done = !done;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
