This is a Demo Project using DBunit to create a DB from a XML file.
The purpose of this is so test data is consistent and does not change because others are sharing the same database.

DatabaseReader is used to connect to a database and turn it into a XML file to be loaded at a later point.
DatabaseWriter writes a XML file to a database.
DatabaseRunner contains some helper methods and a main to run the DatabaseReader in for initial creation of the XML file.
DoSomething is just used to edit the data so there is something to test.

One important thing to note is that before the DatabaseWriter is run, the database needs to have the same table structure as the 
original source of the data.  To do this make a ddl of the database.  With mySQL this can be done with mysqldump.

Then run the resulting SQL file on a database to create a database with the correct format.
In src/test/resources there is a demo DDL called WorldDDL.sql which can be used with this example.

