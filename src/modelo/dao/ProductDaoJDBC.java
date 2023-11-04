package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DbException;

public class ProductDaoJDBC {
	
	Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public int insert(String name, String description, Double price, Integer quantity) {
		
		
		String sql = "INSERT INTO tb_product (name, description, price, quantity) "
				+ "VALUES (?, ?, ?, ?)";
		
		try {
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, description);
		preparedStatement.setDouble(3, price);
		preparedStatement.setInt(4, quantity);		
		int affectedLines = preparedStatement.executeUpdate();		
		return affectedLines;
		
		} catch (SQLException e) {
			throw new DbException("Falha de conexão com o banco: " + e.getMessage());
		}
	}
	
	

}
