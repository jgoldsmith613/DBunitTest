package foo;

import java.sql.Connection;
import java.sql.PreparedStatement;


/*
 * Demo class to test
 */
public class DoSomething {

	private String databaseLocation;
	private String userID;
	private String password;
	private String jdbcDriver;
	
	
	/*
	 * Just adds 10 to the population of a location
	 */
	public void addPopulation() throws Exception{
		Connection connection = DatabaseRunner.getConnection(jdbcDriver, databaseLocation, userID, password);
		PreparedStatement ps = connection.prepareStatement("UPDATE city SET Population = Population+10 WHERE ID = 1");
		ps.execute();
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
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	
	
	
	
}
