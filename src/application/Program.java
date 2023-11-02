package application;

import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
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
		
		Connection conn = DB.getConnection();
		
		
		
		DB.closeConnection();

		
		
		
		
		frame.add(painel);
		frame.setVisible(true);
	}

}
