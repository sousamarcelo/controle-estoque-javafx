package modelo.dao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.DB;
import db.DbException;

public class ProductDaoJDBC {

	Connection conn;

	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	public int insert(String name, String description, Double price, Integer quantity) {
		PreparedStatement preparedStatement = null;

		String sql = "INSERT INTO tb_product (name, description, price, quantity) " + "VALUES (?, ?, ?, ?)";

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

	public void searchProduct() {
		
		// criando a janela de dialogo 
		JFrame frameSearch = new JFrame("Pesquisar por nome de produto");
		frameSearch.setSize(800,250);  //tamanha da janela
		
		// Centralizando Jalena na tela
		frameSearch.setLocationRelativeTo(null);				
		frameSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Use um painel com layout de grade para organizar os rótulos e campos
		JPanel panelSearch = new JPanel(new GridLayout(2, 2));
		
		// campo inicial
		JTextField nameField = new JTextField(20);
						
		// titulo do campo
		JLabel nameLabel = new JLabel("Digite um nome");
		
		// adicionando o titulo e campo na janale
		panelSearch.add(nameLabel);
		panelSearch.add(nameField);
		
		// Criando botão consultar por nome
		JButton findProductButton = new JButton("Consular");
		
		// adicionando botão de consulta no painel 
		panelSearch.add(findProductButton);
		
		
		// criando area de exibição das consultas
		JTextArea resultArea = new JTextArea(5, 20);
		resultArea.setEditable(false);
		
		//adicionando area de consulta no painel de consultas
		panelSearch.add(resultArea);
		
		//----------------------------------------------------------------------------------------------------------------------
		
		findProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				//captudando texto do campo digitabel						
				String name = nameField.getText();						
				System.out.println(name);
		
		//----------------------------------------------------------------------------------------------------------------------
		
				PreparedStatement ps = null;
				ResultSet rst = null;
				String sql = null;
				
				System.out.println("name: " + name);
				
				sql = "SELECT name, description, price FROM tb_product WHERE name=?";  // 		
				
				try {
					
					/* executando a consulta SQL para buscar pelo nome */
					ps = conn.prepareStatement(sql);
					ps.setString(1, name);
					
					/* execultar a consultar e guarda-la em um resultset ele mantem uma extrutura de table */
					rst = ps.executeQuery();
					
					// limpando area de resultado de consulta
					resultArea.setText("");
					
					while (rst.next()) {
						String result = "Nome: " + rst.getString("name") + ", Description: " + rst.getString("description") + ", Preço: " + rst.getDouble("price") + "\n";
						resultArea.append(result);
					}
					
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "Falha  ao execução da query SQL de pesquisa.");
					throw new DbException("Falha  ao execução da query SQL de pesquisa: " + e2.getMessage());
				} finally {
					DB.closeStatment(ps);
					DB.closeResultSet(rst);
				}
			}
		
		});	
		
		
		frameSearch.add(panelSearch);
		frameSearch.setVisible(true); //tornando janela visivel
		
		
	}

} // OK
