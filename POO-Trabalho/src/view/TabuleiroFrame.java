package view;


import java.awt.Dimension;

import javax.swing.*;

import java.awt.Toolkit;
import model.Tabuleiro;



public class TabuleiroFrame extends JFrame {

	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
		public TabuleiroPainel painelTabuleiro;
	    public final int LARG_DEFAULT=800;
	    public final int ALT_DEFAULT=800;
	    
	    
	    public TabuleiroFrame(Tabuleiro NovoTabuleiro) {
	    	
	    	Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension ss = tk.getScreenSize();
			this.setLocation(ss.width/4, (2*ss.height-ss.width)/4);
			this.setSize(ss.width/2, ss.width/2);
			
			Dimension tamTabuleiro = new Dimension(ss.width/2, ss.width/2);
	    	painelTabuleiro = new TabuleiroPainel(NovoTabuleiro);
	    	painelTabuleiro.setPreferredSize( tamTabuleiro );
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
	    	setTitle("Xadrez");	
			getContentPane().add(painelTabuleiro);

		
		}
	    
	   
}
		
		
					
	




	
	 
 

