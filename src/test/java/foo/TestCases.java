package foo;

import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.Assertion;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestCases {

	private static DatabaseWriter databaseWriter;
	private static DoSomething doSomething;
	private static IDatabaseConnection connection;
	private static Properties prop;

	
	/*
	 * Set up to be used by test
	 * Loads Test Data into the database
	 */
	@BeforeClass
	public static void setUp() throws Exception{
		System.out.println("Before");
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"foo/spring_startup.xml");
		databaseWriter = context.getBean(DatabaseWriter.class);
		doSomething = context.getBean(DoSomething.class);
		databaseWriter.writeToDatabase();
		prop = new Properties();
		prop.load(ClassLoader.class.getResourceAsStream("/foo/project.properties"));
		connection = DatabaseRunner.getIConnection(prop.getProperty("db.databaseJDBCDriver"), prop.getProperty("db.destinationDataLocation"), prop.getProperty("db.userID"), prop.getProperty("db.password"));
	}

	/*
	 * Test, validates that doSomething.addPopulation added the correct amount and loaded the result into the database
	 */
	@Test
	public void validateAddPopulation() throws Exception {
		System.out.println("test 1");
		doSomething.addPopulation();
		IDataSet fullDataSet = connection.createDataSet();
		System.out.println(connection.getConnection().isClosed());
		IDataSet expected = new FlatXmlDataSetBuilder().build(ClassLoader.class.getResourceAsStream(prop.getProperty("db.expectedOutputFileName")));
		ITable blah = expected.getTable("city");
		/*fullDataSet.getTable("city");
		fullDataSet.getTable("country");
		fullDataSet.getTable("countrylanguage");*/
		/*for(String str:expected.getTableNames()){
			System.out.println(str);
		}
		for(String str:fullDataSet.getTableNames()){
			System.out.println(str);
		}*/
		Assertion.assertEquals(expected, fullDataSet);
	}
	
	/*
	 * Closes all still open connections
	 */
	@AfterClass
	public static void tearDown() throws SQLException{
		connection.close();
	}

}
