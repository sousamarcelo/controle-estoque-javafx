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
	String nameUpdateTarget = null;

	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	public void insert() {
		
		////////////////////////// BLOCO 01 INICIO
		
		JFrame frameRegister = new JFrame("Cadastrar Produto");
		frameRegister.setSize(500, 250);
		frameRegister.setLocationRelativeTo(null);

		// painel dentro da janela
		JPanel panelRegister = new JPanel(new GridLayout(5, 2));

		// definindo campos dentro do painel
		JTextField nameField = new JTextField(20);
		JTextField descriptionField = new JTextField(20);
		JTextField priceField = new JTextField(20);
		JTextField quantityField = new JTextField(20);

		// nomeando os campos definidos acima
		JLabel nameLabel = new JLabel("Name: ");
		JLabel descriptionLabel = new JLabel("Descição: ");
		JLabel priceLabel = new JLabel("Preço: ");
		JLabel quantityLabel = new JLabel("Quantidade: ");

		// adicionando os campos e os rotulos no painel
		panelRegister.add(nameLabel);
		panelRegister.add(nameField);
		panelRegister.add(descriptionLabel);
		panelRegister.add(descriptionField);
		panelRegister.add(priceLabel);
		panelRegister.add(priceField);
		panelRegister.add(quantityLabel);
		panelRegister.add(quantityField);

		// Botão de cadastro
		JButton registerButton = new JButton("Cadastrar");

		// adicionando botão de cadastrar no painel de cadastros
		panelRegister.add(registerButton);
		
		////////////////////////// BLOCO 01 FIM


		////////////////////////// BLOCO 02 INICIO
		
		// evento de clique do botão de cadastro
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String description = descriptionField.getText();
				String priceAux = priceField.getText().replace(",", ".");
				Double price = Double.parseDouble(priceAux);
				Integer quantity = Integer.parseInt(quantityField.getText());

		////////////////////////// BLOCO 02 INICIO

		
				PreparedStatement preparedStatement = null;
		
				String sql = "INSERT INTO tb_product (name, description, price, quantity) " + "VALUES (?, ?, ?, ?)";
		
				try {
					preparedStatement = conn.prepareStatement(sql);
		
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, description);
					preparedStatement.setDouble(3, price);
					preparedStatement.setInt(4, quantity);
					int affectedLines = preparedStatement.executeUpdate();
		
					if (affectedLines > 0) {
						JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
						nameField.setText("");
						descriptionField.setText("");
						priceField.setText("");
						quantityField.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Falha ao cadastrar o produto.");
					}

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Falha ao execultar a query SQL.");
						throw new DbException("Falhar na execução do script SQL " + e1.getMessage());
					} finally {
						DB.closeStatment(preparedStatement);
					}
			}
		});
		frameRegister.add(panelRegister);
		frameRegister.setVisible(true);
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
				
		
		//----------------------------------------------------------------------------------------------------------------------
		
				PreparedStatement ps = null;
				ResultSet rst = null;
				String sql = null;
				
				
				
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


	public void listProduct() {
		
		// Criando a janela de lista de produtos
		JFrame frameList = new JFrame("Lista de Produtos");
		frameList.setSize(500, 250);
		
		// Centralizando janela
		frameList.setLocationRelativeTo(null);
		frameList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// criando um painel com grid 
		JPanel painelList = new JPanel(new GridLayout(1, 2));
		
		//Criando area para exibição da lista
		JTextArea areaList = new JTextArea(5, 20);
		areaList.setEditable(false);
		
		//adicionando area de consulta ao painel
		painelList.add(areaList);
		
		// iniciando processo de consulta
		
		PreparedStatement pst = null;
		ResultSet rst = null;
		
		String sql = "SELECT name, description, price FROM tb_product ORDER BY name";  // 		
		
		try {
			
			/* executando a consulta SQL para buscar pelo nome */
			pst = conn.prepareStatement(sql);
			//ps.setString(1, name);
			
			/* execultar a consultar e guarda-la em um resultset ele mantem uma extrutura de table */
			rst = pst.executeQuery();
			
			// limpando area de resultado de consulta
			areaList.setText("");
			
			
			
			while (rst.next()) {
				String result = "Nome: " + rst.getString("name") + ", Descrição: " + rst.getString("description") + ", Preço: " + rst.getDouble("price") + "\n";
				
				areaList.append(result);
			}
					
			
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "Falha  ao execução da query SQL de pesquisa.");
			throw new DbException("Falha  ao execução da query SQL de pesquisa: " + e2.getMessage());
		} finally {
			DB.closeStatment(pst);
			DB.closeResultSet(rst);
		}
		
		
		frameList.add(painelList);
		frameList.setVisible(true);
		
	}


	public void updateProduct() {
		
		
		
		// criando a janela de dialogo 
		JFrame frameUpdateTarget = new JFrame("Atualizar Produto");
		frameUpdateTarget.setSize(500,250);  //tamanha da janela
		
		// Centralizando Jalena na tela
		frameUpdateTarget.setLocationRelativeTo(null);				
		frameUpdateTarget.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Use um painel com layout de grade para organizar os rótulos e campos
		JPanel panelUpdateTarget = new JPanel(new GridLayout(2, 2));
		
		// campo inicial
		JTextField nameUpdateTargetField = new JTextField(20);
						
		// titulo do campo
		JLabel nameUpdateTargetLabel = new JLabel("Digite um nome");
		
		// adicionando o titulo e campo na janale
		panelUpdateTarget.add(nameUpdateTargetLabel);
		panelUpdateTarget.add(nameUpdateTargetField);
		
		// Criando botão consultar por nome
		JButton findProductButton = new JButton("Consular");
		
		// adicionando botão de consulta no painel 
		panelUpdateTarget.add(findProductButton);
		
		
		// criando area de exibição das consultas
		JTextArea resultArea = new JTextArea(5, 20);
		resultArea.setEditable(false);
		
		//adicionando area de consulta no painel de consultas
		panelUpdateTarget.add(resultArea);
		
		//----------------------------------------------------------------------------------------------------------------------
		
				
		findProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				//captudando texto do campo digitabel						
				String nameTarget = nameUpdateTargetField.getText();						
				
		
		//----------------------------------------------------------------------------------------------------------------------
		
				PreparedStatement ps = null;
				ResultSet rst = null;
				String sql = null;
				
				
				
				sql = "SELECT name, description, price FROM tb_product WHERE name=?";  // 		
				
				try {
					
					/* executando a consulta SQL para buscar pelo nome */
					ps = conn.prepareStatement(sql);
					ps.setString(1, nameTarget);
					
					/* execultar a consultar e guarda-la em um resultset ele mantem uma extrutura de table */
					rst = ps.executeQuery();
					
					// limpando area de resultado de consulta
					resultArea.setText("");
					
					
					
					while (rst.next()) {
						String result = "Nome: " + rst.getString("name") + ", Description: " + rst.getString("description") + ", Preço: " + rst.getDouble("price") + "\n";
						nameUpdateTarget = rst.getString("name");
						resultArea.append(result);
					}
					
					
					
					
				//////////////////////////BLOCO 01 INICIO
									
					JFrame frameRegister = new JFrame("Cadastrar Produto");
					frameRegister.setSize(500, 250);
					frameRegister.setLocationRelativeTo(null);
				
					// painel dentro da janela
					JPanel panelRegister = new JPanel(new GridLayout(5, 2));
				
					// definindo campos dentro do painel
					JTextField nameField = new JTextField(20);
					JTextField descriptionField = new JTextField(20);
					JTextField priceField = new JTextField(20);
					JTextField quantityField = new JTextField(20);
				
					// nomeando os campos definidos acima
					JLabel nameLabel = new JLabel("Name: ");
					JLabel descriptionLabel = new JLabel("Descição: ");
					JLabel priceLabel = new JLabel("Preço: ");
					JLabel quantityLabel = new JLabel("Quantidade: ");
				
					// adicionando os campos e os rotulos no painel
					panelRegister.add(nameLabel);
					panelRegister.add(nameField);
					panelRegister.add(descriptionLabel);
					panelRegister.add(descriptionField);
					panelRegister.add(priceLabel);
					panelRegister.add(priceField);
					panelRegister.add(quantityLabel);
					panelRegister.add(quantityField);
				
					// Botão de cadastro
					JButton registerButton = new JButton("Cadastrar");
				
					// adicionando botão de cadastrar no painel de cadastros
					panelRegister.add(registerButton);						
					
					// evento de clique do botão de cadastro
					registerButton.addActionListener(new ActionListener() {
						
				
						@Override
						public void actionPerformed(ActionEvent e) {
							
							
							String name = nameField.getText();
							String description = descriptionField.getText();
							String priceAux = priceField.getText().replace(",", ".");
							Double price = Double.parseDouble(priceAux);
							Integer quantity = Integer.parseInt(quantityField.getText());
				
					////////////////////////// BLOCO 02 INICIO
				
							
							
							PreparedStatement preparedStatement = null;
					
							String sql = "UPDATE tb_product SET name=?, description=?, price=?, quantity=? WHERE name = ?";
													
							
							try {
								preparedStatement = conn.prepareStatement(sql);
					
								preparedStatement.setString(1, name);
								preparedStatement.setString(2, description);
								preparedStatement.setDouble(3, price);
								preparedStatement.setInt(4, quantity);
								preparedStatement.setString(5, nameUpdateTarget);
								
								int affectedLines = preparedStatement.executeUpdate();
					
								if (affectedLines > 0) {
									JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
									nameField.setText("");
									descriptionField.setText("");
									priceField.setText("");
									quantityField.setText("");
								} else {
									JOptionPane.showMessageDialog(null, "Falha ao atualizar o produto.");
								}
				
								} catch (SQLException e1 ) {
									JOptionPane.showMessageDialog(null, "Falha ao execultar a query de update SQL.");
									throw new DbException("Falhar na execução do script de update SQL " + e1.getMessage());								
								} 								
								finally {
									DB.closeStatment(preparedStatement);
								}
						}
					});
					frameRegister.add(panelRegister);
					frameRegister.setVisible(true);						
										
					
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "Falha  ao execução da query SQL de pesquisa.");
					throw new DbException("Falha  ao execução da query SQL de pesquisa: " + e2.getMessage());
				} 
				
				finally {
					DB.closeStatment(ps);
					DB.closeResultSet(rst);
				}
			}
		
		});	
		
		
		frameUpdateTarget.add(panelUpdateTarget);
		frameUpdateTarget.setVisible(true); //tornando janela visivel
	}

	public void deleteProduct() {
		
		
		// criando a janela de dialogo 
				JFrame frameDelete = new JFrame("Excluir Produto");
				frameDelete.setSize(800,250);  //tamanha da janela
				
				// Centralizando Jalena na tela
				frameDelete.setLocationRelativeTo(null);				
				frameDelete.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// Use um painel com layout de grade para organizar os rótulos e campos
				JPanel panelDelete = new JPanel(new GridLayout(2, 2));
				
				// campo inicial
				JTextField nameDeleteField = new JTextField(20);
								
				// titulo do campo
				JLabel nameDeleteLabel = new JLabel("Digite um nome");
				
				// adicionando o titulo e campo na janale
				panelDelete.add(nameDeleteLabel);
				panelDelete.add(nameDeleteField);
				
				// Criando botão consultar por nome
				JButton deleteProductButton = new JButton("Deletar");
				
				// adicionando botão de consulta no painel 
				panelDelete.add(deleteProductButton);
				
				
				// criando area de exibição das consultas
				JTextArea deleteArea = new JTextArea(5, 20);
				deleteArea.setEditable(false);
				
				//adicionando area de consulta no painel de consultas
				panelDelete.add(deleteArea);
				
				//----------------------------------------------------------------------------------------------------------------------
				
				deleteProductButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						
						//captudando texto do campo digitabel						
						String name = nameDeleteField.getText();						
						
				
				//----------------------------------------------------------------------------------------------------------------------
				
						PreparedStatement ps = null;
						ResultSet rst = null;
						String sql = null;
						
						
						
						sql = "DELETE FROM tb_product WHERE name=?";						
						
						try {
							
							/* executando a consulta SQL para buscar pelo nome */
							ps = conn.prepareStatement(sql);
							ps.setString(1, name);
							
							/* execultar a consultar e guarda-la em um resultset ele mantem uma extrutura de table */
							int rows = ps.executeUpdate();
							
							// limpando area de resultado de consulta
							deleteArea.setText("");
							
							if (rows == 0) {
								JOptionPane.showMessageDialog(null, "Produto não existe");
								throw new DbException("Error: Id entered does not exist!");
							} else {
								JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!");
							}
										
																
							
							} catch (SQLException e2) {
								JOptionPane.showMessageDialog(null, "Falha  ao execução da query SQL de pesquisa.");
								throw new DbException("Falha  ao execução da query SQL de pesquisa: " + e2.getMessage());
							} finally {
								DB.closeStatment(ps);
								//DB.closeResultSet(rst);
							}
					}
				
				});	
				
				
				frameDelete.add(panelDelete);
				frameDelete.setVisible(true); //tornando janela visivel
		
	}
} 
