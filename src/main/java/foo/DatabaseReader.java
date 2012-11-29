package foo;

import java.io.FileOutputStream;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/*
 * Class to read data from DB and turns it into a XML file.
 */
public class DatabaseReader {
	private String databaseLocation;
	private String userID;
	private String password;
	private String fileLocation;
	private String jdbcDriver;

	public void writeToXML() throws Exception {
		IDatabaseConnection connection = DatabaseRunner.getIConnection(
				jdbcDriver, databaseLocation, userID, password);
		IDataSet fullDataSet = connection.createDataSet();
		FlatXmlDataSet.write(fullDataSet, new FileOutputStream(fileLocation));
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
