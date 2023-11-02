package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	public static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("url");
				String user = props.getProperty("usuario");
				String password = props.getProperty("senhaBanco");
				conn = DriverManager.getConnection(url, user, password);
				
			} catch (SQLException e) {
				throw new DbException("Error connection data base: " + e.getMessage());
			}
		}
		return conn;
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException("Error: " + e.getMessage());
		}
	}
	
	
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException("fail close connection: " + e.getLocalizedMessage());
			}
		}
	}
	
	public static void closeStatment(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException("Fail close statment: " + e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException("Fail close resultSet: " + e.getMessage());
			}
		}
	}

}
