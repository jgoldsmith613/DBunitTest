package foo;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/*
 * Helper class with methods used by the reader, writer and test classes.
 * 
 */
public class DatabaseRunner {

	/*
	 * Can run Reader and Writer from a main.
	 * Reader can be run to make the xml file used for the test.
	 * The file must then be moved to src/test/resources
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"foo/spring_startup.xml");
		
		
		/*DatabaseReader dbr = context.getBean(DatabaseReader.class);
		dbr.writeToXML();*/
		
		
		DatabaseWriter dbw = context.getBean(DatabaseWriter.class);
		dbw.writeToDatabase();

	}

	/*
	 * Create IDatabaseConnection which is a DBunit class.
	 * IDatabaseConnection contains a JDBC connection
	 */
	public static IDatabaseConnection getIConnection(String jdbcDriver,
			String databaseLocation, String userID, String password) throws Exception {
		
		Connection jdbcConnection = getConnection(jdbcDriver, databaseLocation, userID, password);
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		return connection;
	}
	
	/*
	 * Creates a Connection which is used by JDBC.
	 */
	public static Connection getConnection(String jdbcDriver,
			String databaseLocation, String userID, String password) throws Exception{
		Class.forName(jdbcDriver);
		Connection jdbcConnection = DriverManager.getConnection(
				databaseLocation, userID, password);
		return jdbcConnection;
	}

}
