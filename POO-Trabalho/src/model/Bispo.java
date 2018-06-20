package model;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Vector;



public class Bispo extends Pecas{
	
	public Bispo(int PecaLin, int PecaCol, int PecaCor)
	{
		lin = PecaLin;
		col = PecaCol;
		cor = PecaCor;
		try {
			
			if (cor == 1) {
				img = ImageIO.read(new File("Imagens/CyanB.png"));
			}
			else {
				img = ImageIO.read(new File("Imagens/PurpleB.png"));
			}
			
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	
	 public boolean MovimentosPermitidos(int PecaLin, int PecaCol, Tabuleiro tabuleiro){

			 if(this.lin == PecaLin || this.col == PecaCol )
					return false;

			 int direcao = obtemDirecao(PecaLin, PecaCol);
			 Posicoes inicio = new Posicoes(this.lin, this.col);
			 Posicoes fim = new Posicoes(PecaLin, PecaCol);
			 
			
			
			if(this.cor == branco ) { 
				return VerificaPosicoes(inicio, fim,preto, direcao, tabuleiro, 0);
			}
			else if (this.cor == preto){
				return VerificaPosicoes(inicio, fim, branco, direcao, tabuleiro, 0);
			}
			
			return true;
    }
	 
	 private int obtemDirecao(int PecaLin, int PecaCol) {
			
		 	if(PecaLin > this.lin){
				//desce para direita
				if(PecaCol > this.col){
					return 2;
				}
				//sobe para direita
				else{
					return 1;
				}	
			}
			else{
				//desce para esquerda
				if(PecaCol > this.col){
					return 3;
				}
				//sobe para esquerda
				else{
					return 4;
				}	
			}
	}
	 // qtdPeca = bispo nao pode pular nenhuma peca
	 private boolean VerificaPosicoes(Posicoes inicio, Posicoes fim, int adversario, int direcao, Tabuleiro tabuleiro, int qtdPeca){
			
			if( inicio.Igual(fim) && !tabuleiro.posicaoOcupada(fim.getX(), fim.getY()))
				return true;
			
			else if(inicio.Igual(fim) && tabuleiro.posicaoOcupada(fim.getX(), fim.getY()) && (tabuleiro.LocalizaPeca(fim.getX(), fim.getY()).getColor() == adversario)) 
				return true;
			
			else if(tabuleiro.posicaoOcupada(inicio.getX(), inicio.getY()) && qtdPeca > 0)
				return false;
			
			else
			{	//sobe para direita
				if(direcao == 1)
				{
					inicio.setPosicoes(inicio.getX()+1, inicio.getY()-1);
					if(inicio.getX() > fim.getX() || inicio.getY() < fim.getY())
						return false;
					
					return VerificaPosicoes(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
				//desce para direita
				else if(direcao == 2)
				{
					inicio.setPosicoes(inicio.getX()+1, inicio.getY()+1);
					if(inicio.getX() > fim.getX() || inicio.getY() > fim.getY())
						return false;
					
					return VerificaPosicoes(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
				//desce para esquerda
				else if(direcao == 3)
				{
					inicio.setPosicoes(inicio.getX()-1, inicio.getY()+1);
					if(inicio.getX() < fim.getX() || inicio.getY() > fim.getY())
						return false;
					
					return VerificaPosicoes(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
				//sobe para esquerda
				else
				{
					inicio.setPosicoes(inicio.getX()-1, inicio.getY()-1);
					if(inicio.getX() < fim.getX() || inicio.getY() < fim.getY())
						return false;
					
					return VerificaPosicoes(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
			}
	}
	 
	 
	 public TipoPeca getTipo() {
		 
		return TipoPeca.Bispo;
	 }
	 
	 public Vector<Posicoes> VetorMovimentos(Tabuleiro tabuleiro, boolean Xeque){
		 Vector<Posicoes> pos = new Vector<Posicoes>();

		 if (!Xeque) {
	        for(int i = 0; i < 8;i++ ){
	            for(int j = 0; j < 8;j++){
	            	 if( MovimentosPermitidos(i, j,tabuleiro))
		                    pos.add(new Posicoes(i, j));
	            }
	        }
	      }
		 else {
			 for(int i = 0; i < 8;i++ ){
		            for(int j = 0; j < 8;j++){
		            	
		            	 if( MovimentosPermitidos(i, j,tabuleiro) && !tabuleiro.VerificaJogadaXeque(this, i, j, tabuleiro))
		                     pos.add(new Posicoes(i, j));
		            	
		            }
		        }
			 
		 }
	        return pos;
	}



}
