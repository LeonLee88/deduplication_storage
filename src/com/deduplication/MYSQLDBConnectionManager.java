package com.deduplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class MYSQLDBConnectionManager {

	private Connection conn = null;

	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public Connection connect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/dfs?"
					+ "user=admin&password=password1");

			// // statements allow to issue SQL queries to the database
			// statement = (Statement) conn.createStatement();
			// // resultSet gets the result of the SQL query
			// resultSet = statement.executeQuery("select * from file");
			// writeResultSet(resultSet);

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return conn;
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// resultSet is initialised before the first data set
		while (resultSet.next()) {
			// it is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g., resultSet.getSTring(2);
		}
	}

	public void writeChunk(String chunkID, byte[] chunkData) {
		try {
			conn = connect();
			preparedStatement = (PreparedStatement) conn.prepareStatement("insert into chunk values (?,?)");
			preparedStatement.setString(1, chunkID);
			preparedStatement.setBytes(2, chunkData);
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
