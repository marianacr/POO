package view;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;

import controller.ControladorTabuleiro;
import model.Pecas;
import model.Posicoes;
import model.Tabuleiro;
import model.TipoPeca;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class TabuleiroPainel extends JPanel implements ObservaSujeito, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int TXT_X=800;
	public static final int TXT_Y=800;
	public static int tam = 100;
	private Rectangle2D quadrados = new Rectangle2D.Double();
	private Tabuleiro tabuleiro ;
	private int lin = 8;
	private int col = 8 ;
	private Vector<Posicoes> posicoesPossiveis ;
	private  Object[] pecas = { "Bispo","Rainha", "Cavalo","Torre" };
	
	
	public TabuleiroPainel(Tabuleiro NovoTabuleiro) {
		tabuleiro = NovoTabuleiro;

	}

	public void paintComponent(Graphics g) {
		
	
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				
				quadrados.setRect(i*tam,j*tam,tam,tam);
				
				if ( (i+j) % 2 == 0 ){
					
					g2d.setPaint(Color.WHITE);
					g2d.fill(quadrados);
				}
				else{
					
					g2d.setPaint(Color.BLACK);
					g2d.fill(quadrados);
				}
			}
		}
		
		
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				
				Pecas p = tabuleiro.LocalizaPeca(i,j);
				
				if (p != null) {
					g2d.drawImage(p.getImage(), i*tam, j*tam, tam, tam, null);
				}
			}
		}
		
		if(lin < 8) {
			
			
			g2d.setPaint(Color.yellow);
			quadrados.setRect(lin*tam,col*tam,tam,tam);
			g2d.fill(quadrados);
			Pecas p = tabuleiro.LocalizaPeca(lin,col);
			g2d.drawImage(p.getImage(), lin*tam, col*tam, tam, tam, null);
			lin = col = 8;
			
		}
		
		if (posicoesPossiveis != null) {
			
			for (int i = 0; i < posicoesPossiveis.size(); i++) 
			{
				
				quadrados.setRect( posicoesPossiveis.get(i).getX()*tam, posicoesPossiveis.get(i).getY()*tam,tam,tam);
				g2d.setStroke(new BasicStroke(5)); //deixa o contorno mais grosso
				g2d.setPaint(Color.blue);
				g2d.draw(quadrados);
				  
			}
			posicoesPossiveis = null;
				
			
		}
		
	}
	

	
	public void QuadradoSelecionado(int lin, int col) {
		 
		     this.lin = lin;
		     this.col = col;
		 
	 }
	
	public void posicoesPermitidas(Vector<Posicoes> posicoes) {
		
		posicoesPossiveis = posicoes;
		
	}
	 
		
	 public void update(int i){
		 if (i == 1) {
			 repaint();
		}
		 else if( i == 2) {
			 popUpPromocao();
		 }
		 else if (i == 3) {
			 popUpXequeMate(i);
		 }
		 else if (i == 4) {
			 popUpXequeMate(i);
		 }
		 else
			 popUpCongelamento();
		 
		
	}
	 
	private void  popUpCongelamento() {
		
		int entrada;
		Object[] opcoes = { "OK" };
		UIManager.put("OptionPane.minimumSize",new Dimension(400,200)); 
	
			entrada = JOptionPane.showOptionDialog(null, "Congelamento : Foi empate !", "Xadrez", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes,  opcoes[0]);
				
			if(entrada == JOptionPane.OK_OPTION)
			{
				System.out.println("CONGELAMENTO REINICIA JOGO");
			}
		
	}
		
	
	 
	private void popUpXequeMate(int i) {
		int entrada;
		Object[] opcoes = { "OK" };
		UIManager.put("OptionPane.minimumSize",new Dimension(400,200)); 
		if (i == 3) {
			entrada = JOptionPane.showOptionDialog(null, "XEQUE MATE : Time Branco Venceu", "Xadrez", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes,  opcoes[0]);
		}
		else {
			entrada = JOptionPane.showOptionDialog(null, "XEQUE MATE : Time Preto Venceu", "Xadrez", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes,  opcoes[0]);
		}
		
			if(entrada == JOptionPane.OK_OPTION)
			{
				System.out.println("REINICIA JOGO");
			}
		
	}
	 
	public void popUpPromocao(){
		
		JPopupMenu popUp = new JPopupMenu("Promoção do Peão");
		double largura = this.getWidth()/2;
		double altura = this.getHeight()/10;
		
		
		JMenuItem label = new JMenuItem("Selecione uma peça:");
		label.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		popUp.add(label);
		
		JRadioButtonMenuItem torre = new JRadioButtonMenuItem("Torre");
		torre.setPreferredSize(new Dimension((int)largura,(int)altura));
		torre.addActionListener(this);
		popUp.add(torre);
		
		JRadioButtonMenuItem cavalo = new JRadioButtonMenuItem("Cavalo");
		cavalo.setPreferredSize(new Dimension((int)largura,(int)altura));
		cavalo.addActionListener(this);
		popUp.add(cavalo);
		
		JRadioButtonMenuItem bispo = new JRadioButtonMenuItem("Bispo");
		bispo.setPreferredSize(new Dimension((int)largura,(int)altura));
		bispo.addActionListener(this);
		popUp.add(bispo);
		
		JRadioButtonMenuItem rainha = new JRadioButtonMenuItem("Rainha");
		rainha.setPreferredSize(new Dimension((int)largura,(int)altura));
		rainha.addActionListener(this);
		popUp.add(rainha);
		
		popUp.show(this,(int)(this.getWidth()-largura)/2, (int)(this.getHeight()-5*altura)/2);
	}
	
	public void actionPerformed(ActionEvent e) {
	ControladorTabuleiro controlador = ControladorTabuleiro.getControladorTabuleiro();
	if (e.getActionCommand() != null) {
		switch (e.getActionCommand()) {
        case "Torre":
        	System.out.println( " TORRE" );
        	 controlador.PromocaoPeao(TipoPeca.Torre);
            break;
        case "Cavalo":
        	System.out.println( " CAVALO" );
        	controlador.PromocaoPeao(TipoPeca.Cavalo);
            break;
        case "Bispo":
        	System.out.println( " BISPO" );
        	controlador.PromocaoPeao(TipoPeca.Bispo);
            break;
        case "Rainha":
        	System.out.println( " RAINHA" );
        	controlador.PromocaoPeao(TipoPeca.Rainha);
            break;
		}
	}
		
		
	}
	

	
		
	
	

}

