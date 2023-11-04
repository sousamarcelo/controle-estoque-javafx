package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import db.DB;
import db.DbException;

public class ProductDaoJDBC {
	
	Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public int insert(String name, String description, Double price, Integer quantity) {
		PreparedStatement preparedStatement = null;
		
		String sql = "INSERT INTO tb_product (name, description, price, quantity) "
				+ "VALUES (?, ?, ?, ?)";
		
		try {
		preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, description);
		preparedStatement.setDouble(3, price);
		preparedStatement.setInt(4, quantity);		
		int affectedLines = preparedStatement.executeUpdate();
		
		
		return affectedLines;
		
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Falha ao execultar a query SQL.");
			throw new DbException("Falhar na execução do script SQL " + e.getMessage());
		} finally {			
			DB.closeStatment(preparedStatement);
		}
	}
	
	public ResultSet searchProduct(String name) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		
		System.out.println("name: " + name);
		
		sql = "SELECT name, description, price FROM tb_product WHERE name=?";  // 
		
		
		try {
			
			/* executando a consulta SQL para buscar pelo nome */
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			
			/* execultar a consultar e guarda-la em um resultset ele mantem uma extrutura de table */
			rs = ps.executeQuery();
			
			return rs;
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Falha  ao execução da query SQL de pesquisa.");
			throw new DbException("Falha  ao execução da query SQL de pesquisa: " + e.getMessage());
		} finally {
			//DB.closeStatment(ps);
		}
		
		
	}
	
	

}
