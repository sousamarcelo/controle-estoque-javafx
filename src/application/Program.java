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
import modelo.dao.DaoFactory;
import modelo.dao.ProductDaoJDBC;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		 ProductDaoJDBC product = DaoFactory.createProductDao();
		
		/* Criando a janela */
		JFrame frame = new JFrame("Cadastro de Produtos");		
		frame.setLocale(null); ///esperimento, se der problema retirar		
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
				
		/* criando um grid */
		JPanel painel = new JPanel(new GridLayout(5, 2));
				
		JButton registerProductButton = new JButton("Cadastrar Produto");	
		JButton searchProductsButton = new JButton("Listar Produtos");		
		JButton findProductButton = new JButton("Encontrar Produto");
		JButton UpdateProductButton = new JButton("Atualizar Produto");
		JButton deleteProductButton = new JButton("Excluir Produto");	
				
		registerProductButton.setAlignmentX(500);
		findProductButton.setLocation(0,0);
		
		painel.add(registerProductButton);
		painel.add(searchProductsButton);
		painel.add(findProductButton);
		painel.add(UpdateProductButton);
		painel.add(deleteProductButton);
				
		registerProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
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
						Double price = Double.parseDouble(priceAux);
						Integer quantity = Integer.parseInt(quantityField.getText());						
						
						/* acessando banco para gravar a inclusão */
						int affectedLines = product.insert(name, description, price, quantity);
													
						if (affectedLines > 0) {
							JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
							nameField.setText("");
							descriptionField.setText("");
							priceField.setText("");
							quantityField.setText("");
						} else {
							JOptionPane.showMessageDialog(null, "Falha ao cadastrar o usuário.");
						}
					}
				});
				
				
				
				frameRegister.add(panelRegister);
				frameRegister.setVisible(true);
			}
		});		
		
		searchProductsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/* criando a janela de dialogo */
				JFrame frameSearch = new JFrame("Listar/Pesquisar produtos");
				frameSearch.setSize(500,250);  //tamanha da janela
				
				/* Centralizando Jalena na tela */
				frameSearch.setLocationRelativeTo(null);				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// Use um painel com layout de grade para organizar os rótulos e campos
				JPanel panelSearch = new JPanel(new GridLayout(2, 2));
				
				/* campo inicial */
				JTextField nameField = new JTextField(20);
				
				/* titulo do campo */
				JLabel nameLabel = new JLabel("Digite um nome");
				
				/* adicionando o titulo e campo na janale */
				panelSearch.add(nameLabel);
				panelSearch.add(nameField);
				
				/* Criando botão consultar por nome */
				JButton searchProductButton = new JButton("Consular");
				
				/* adicionando botão de consulta no painel */
				panelSearch.add(searchProductButton);
				
/////////////// continhar aqui! botão criado, falta dar iniciar a consulta jdbc
				
				frameSearch.add(panelSearch);
				frameSearch.setVisible(true); //tornando janela visivel
				
			}
		});
		
		
		frame.add(painel);
		frame.setVisible(true);		
		
	}

}
