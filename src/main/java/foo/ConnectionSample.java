package foo;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;




/*
 * Useless Class, Just some code I did not want to get rid of that I was using as a reference.
 */
public class ConnectionSample {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws Exception {
	
		testJDBCConnection();
		/*TurnToXML();
		makeTables();*/
		 
		
	}
	
	
	
	public static void testJDBCConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager
		          .getConnection("jdbc:mysql://localhost:3306/blahblah", "dbunit", "redhat");
		PreparedStatement ps = connect.prepareStatement("SELECT * from city");
		ResultSet rs = ps.executeQuery();
		
		 while (rs.next()) {
			 System.out.println(rs.getString("name"));
		 }
	}
	
	public static void TurnToXML() throws Exception{
		 // database connection
        Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection(
        		"jdbc:mysql://localhost:3306/world", "dbunit", "redhat");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("FOO", "select * from city where CountryCode = 'AFG' ");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.xml"));

        // full database export
        IDataSet fullDataSet = connection.createDataSet();
        FlatDtdDataSet.write(fullDataSet, new FileOutputStream("full.dtd"));
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
        
	}
	
	
	public static void makeTables() throws Exception{
		
		
		
		
		  Class.forName("com.mysql.jdbc.Driver");
	      Connection jdbcConnection = DriverManager.getConnection(
	        		"jdbc:mysql://localhost:3306/test", "root", "0613");
	      IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        // ...

        // initialize your dataset here
	      IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("full.xml"));
        // ...

        try
        {
        	
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        }
        finally
        {
           System.out.println("done");
        }
	}

}
