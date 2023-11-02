package application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		/* Criando a janela */
		JFrame frame = new JFrame("Cadastro de Produtos");		
		frame.setLocale(null); ///esperimento, se der problema retirar		
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
				
		/* criando um grid */
		JPanel painel = new JPanel(new GridLayout(5, 2));
				
		JButton registerProductButton = new JButton("Cadastrar Produto");		
		JButton findProductButton = new JButton("Encontrar Produto");
		JButton insertProductButton = new JButton("Inserir Produto");
		JButton deleteProductButton = new JButton("Excluir Produto");	
				
		registerProductButton.setAlignmentX(500);
		findProductButton.setLocation(0,0);
		
		painel.add(registerProductButton);
		painel.add(findProductButton);
		painel.add(insertProductButton);
		painel.add(deleteProductButton);
				
		registerProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cliquei aqui");
				
				JFrame frameRegister = new JFrame("Cadastrar Produto");				
				frameRegister.setSize(500, 250);				
				frameRegister.setLocationRelativeTo(null);				
				
				/* painel dentro da janela */
				JPanel panelRegister = new JPanel(new GridLayout(5, 2));
				
				/* definindo campos dentro do painel */
				JTextField nameField = new JTextField(20);
				JTextField descriptionField = new JTextField(20);
				JTextField priceField = new JTextField(20);
				JTextField quantityField = new JTextField(20);
				
				/* nomeando os campos definidos acima */
				JLabel nameLabel = new JLabel("Name: ");
				JLabel descriptionLabel = new JLabel("Descição: ");
				JLabel priceLabel = new JLabel("Preço: ");
				JLabel quantityLabel = new JLabel("Quantidade: ");	
				
				/* adicionando os campos e os rotulos no painel*/
				panelRegister.add(nameLabel);
				panelRegister.add(nameField);				
				panelRegister.add(descriptionLabel);
				panelRegister.add(descriptionField);				
				panelRegister.add(priceLabel);
				panelRegister.add(priceField);				
				panelRegister.add(quantityLabel);
				panelRegister.add(quantityField);
				
				/* Botão de cadastro */
				JButton registerButton = new JButton("Cadastrar");
				
				/* adicionando botão de cadastrar no painel de cadastros */
				panelRegister.add(registerButton);
				
				/* evento de clique do botão de cadastro */
				registerButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String name = nameField.getText();
						String description = descriptionField.getText();
						String priceAux = priceField.getText().replace(",", ".");
												
						Integer quantity = Integer.parseInt(quantityField.getText());
						
						Double price = Double.parseDouble(priceAux);
						
						Connection conn = DB.getConnection();
						
						String sql = "INSERT INTO tb_product (name, description, price, quantity) "
									+ "VALUES (?, ?, ?, ?)";
						
						try {
							
							PreparedStatement preparedStatement = conn.prepareStatement(sql);
							
							preparedStatement.setString(1, name);
							preparedStatement.setString(2, description);
							preparedStatement.setDouble(3, price);
							preparedStatement.setInt(4, quantity);
							
							int affectedLines = preparedStatement.executeUpdate();
							
							if (affectedLines > 0) {
								JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
								nameField.setText("");
								descriptionField.setText("");
								priceField.setText("");
								quantityField.setText("");
							} else {
								JOptionPane.showMessageDialog(null, "Falha ao cadastrar o usuário.");
							}
							DB.closeStatment(preparedStatement);
							DB.closeConnection();
							
						} catch (SQLException e1) {							
							e1.printStackTrace();
						}									
						
					}
				});
				
				frameRegister.add(panelRegister);
				frameRegister.setVisible(true);
			}
		});		
		
		frame.add(painel);
		frame.setVisible(true);		
		
	}

}
