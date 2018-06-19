package view;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

import model.Pecas;
import model.Posicoes;
import model.Tabuleiro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class TabuleiroPainel extends JPanel implements ObservaSujeito{

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
	 
	 void unselectSquare(int row, int col) {
		 
	 }
	 
	 void unselectAll() {
		 
	 }

	
	 public void update(){
		 repaint();
	}

	
		
	
	

}

