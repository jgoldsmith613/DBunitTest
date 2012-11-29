package foo;

import java.io.FileInputStream;
import java.io.InputStream;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;



/*
 * Class to write data to a DB from a XML file.
 */
public class DatabaseWriter {

	private String databaseLocation;
	private String userID;
	private String password;
	private String fileLocation;
	private String jdbcDriver;

	public void writeToDatabase() throws Exception {
		IDatabaseConnection connection = DatabaseRunner.getIConnection(
				jdbcDriver, databaseLocation, userID, password);
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(ClassLoader.class.getResourceAsStream(fileLocation));
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		connection.close();
	}

	public String getDatabaseLocation() {
		return databaseLocation;
	}

	public void setDatabaseLocation(String databaseLocation) {
		this.databaseLocation = databaseLocation;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

}
