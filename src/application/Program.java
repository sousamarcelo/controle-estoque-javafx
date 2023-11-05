package application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.dao.DaoFactory;
import modelo.dao.ProductDaoJDBC;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);

		ProductDaoJDBC product = DaoFactory.createProductDao();

		// Criando a janela
		JFrame frame = new JFrame("Cadastro de Produtos");
		frame.setLocale(null); /// esperimento, se der problema retirar
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
		listProductsButton.setLocation(0, 0);

		painel.add(registerProductButton);
		painel.add(searchProductButton);
		painel.add(listProductsButton);
		painel.add(UpdateProductButton);
		painel.add(deleteProductButton);

		registerProductButton.addActionListener(new ActionListener() {

			@Override			
			public void actionPerformed(ActionEvent e) {
				
				product.insert();					

			}
		});

		searchProductButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				product.searchProduct();
			}
		});
		
		
		listProductsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				product.listProduct();
				
				
			}
		});
		
		

		frame.add(painel);
		frame.setVisible(true);

	}

}
