package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class TelaInicial extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame telaHome = new JFrame("Xadrex");
    public final int LARG_DEFAULT=800;
    public final int ALT_DEFAULT=800;

	 public TelaInicial(){		
	    	
		 
		    	
	   	
			Dimension tamTela = new Dimension(LARG_DEFAULT, ALT_DEFAULT);
	    	JButton b1 = new JButton("Novo Jogo");
			JButton b2 = new JButton("Carregar Jogo");
			b1.addActionListener(this);
			b2.addActionListener(this);
			telaHome.setSize(tamTela);//Tamanho da tela  
			telaHome.setLocationRelativeTo(null);// centraliza a tela
			telaHome.getContentPane().setBackground(Color.white);//Cor de fundo
			telaHome.getContentPane().setLayout(null);//Sem Gerenciador de Layout
			b1.setBounds(300, 185, 200, 100);//(x, y, largura, altura)
			b2.setBounds(300, 325, 200, 100);
			telaHome.getContentPane().add(b1);
			telaHome.getContentPane().add(b2);
			telaHome.setDefaultCloseOperation(EXIT_ON_CLOSE);
			telaHome.setVisible(true);
			}
	    
	    
	    
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() != null) {
				
				switch (e.getActionCommand()) {
		        case "Novo Jogo":
		        	System.out.println( " novo jogo" );
		        	telaHome.dispose();
		        	Iniciar.Iniciar();
		            break;
		        case "Carregar Jogo":
		        	System.out.println( " Carregar" );
		        	SalvarCarregar.Carregar();
		            break;
		       
				}
			
			}
		}

	
	

}


