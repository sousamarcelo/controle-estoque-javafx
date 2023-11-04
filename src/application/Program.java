package application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.DB;
import db.DbException;
import modelo.dao.DaoFactory;
import modelo.dao.ProductDaoJDBC;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		 ProductDaoJDBC product = DaoFactory.createProductDao();
		
		// Criando a janela
		JFrame frame = new JFrame("Cadastro de Produtos");		
		frame.setLocale(null); ///esperimento, se der problema retirar		
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
				
		// criando um grid
		JPanel painel = new JPanel(new GridLayout(5, 2));
				
		JButton registerProductButton = new JButton("Cadastrar Produto");	
		JButton searchProductButton = new JButton("Encontrar Produto");		
		JButton listProductsButton = new JButton("Listar Produtos");
		JButton UpdateProductButton = new JButton("Atualizar Produto");
		JButton deleteProductButton = new JButton("Excluir Produto");	
				
		registerProductButton.setAlignmentX(500);
		listProductsButton.setLocation(0,0);
		
		painel.add(registerProductButton);
		painel.add(searchProductButton);
		painel.add(listProductsButton);
		painel.add(UpdateProductButton);
		painel.add(deleteProductButton);
				
		registerProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
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
						
						// acessando banco para gravar a inclusão
						int affectedLines = product.insert(name, description, price, quantity);
													
						if (affectedLines > 0) {
							JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
							nameField.setText("");
							descriptionField.setText("");
							priceField.setText("");
							quantityField.setText("");
						} else {
							JOptionPane.showMessageDialog(null, "Falha ao cadastrar o produto.");
						}
					}
				});
				
				
				
				frameRegister.add(panelRegister);
				frameRegister.setVisible(true);
			}
		});		
		
		searchProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				// criando a janela de dialogo 
				JFrame frameSearch = new JFrame("Pesquisar por nome de produto");
				frameSearch.setSize(800,250);  //tamanha da janela
				
				// Centralizando Jalena na tela
				frameSearch.setLocationRelativeTo(null);				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
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
				JButton searchProductButton = new JButton("Consular");
				
				// adicionando botão de consulta no painel 
				panelSearch.add(searchProductButton);
				
				
				// criando area de exibição das consultas
				JTextArea resultArea = new JTextArea(5, 20);
				resultArea.setEditable(false);
				
				//adicionando area de consulta no painel de consultas
				panelSearch.add(resultArea);				
				
				searchProductButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ResultSet rs = null;
						
						//captudando texto do campo digitabel						
						String name = nameField.getText();						
						System.out.println(name);
						
						// executando consulta passando nome capturado do painel
						rs = product.searchProduct(name);
				
						// limpando area de resultado de consulta
						resultArea.setText("");
						
						try {
							while (rs.next()) {
								String result = "Nome: " + rs.getString("name") + ", Description: " + rs.getString("description") + ", Preço: " + rs.getDouble("price") + "\n";
								resultArea.append(result);
							}
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "Erro: Não foi possível realizar a consulta.");
							throw new DbException("Erro: Não foi possivel realizar a consulta: " + e1.getMessage());
												
						} finally {					
							DB.closeResultSet(rs);
						}	
						
					}
				});	
				
				
				frameSearch.add(panelSearch);
				frameSearch.setVisible(true); //tornando janela visivel
				
			}
		});
		
		
		frame.add(painel);
		frame.setVisible(true);		
		
	}

}
