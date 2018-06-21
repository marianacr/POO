package model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Rei extends Pecas {
	
	public Rei(int PecaLin, int PecaCol, int PecaCor)
	{
		lin = PecaLin;
		col = PecaCol;
		cor = PecaCor;
		try {
			
			if (cor == 1) {
				img = ImageIO.read(new File("Imagens/CyanK.png"));
			}
			else {
				img = ImageIO.read(new File("Imagens/PurpleK.png"));
			}
			
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	 public boolean MovimentosPermitidos(int PecaLin, int PecaCol, Tabuleiro tabuleiro) {

		 	int adversario;
		 	if (this.cor == preto) {
		 		adversario = branco;
		 	}
		 	else {
		 		adversario = preto;
		 	}

	        if(this.lin-1 ==PecaLin && this.col == PecaCol &&  (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	        	return true;
	        else if(this.lin+1 ==PecaLin && this.col == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	            return true;
	        else if(this.lin ==PecaLin && this.col+1 == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	            return true;
	        else if(this.lin ==PecaLin && this.col-1 == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	            return true;
	        else if(this.lin+1 ==PecaLin && this.col+1 == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	            return true;
	        else if(this.lin-1 ==PecaLin && this.col+1 == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	            return true;
	        else if(this.lin+1 ==PecaLin && this.col-1 == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	            return true;
	        else if(this.lin-1 ==PecaLin && this.col-1 == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
	            return true;
	        else if (VerificaRoqueDir(PecaLin,PecaCol,tabuleiro) && !tabuleiro.XequeRei(this.cor))
	        	return true;
	        else if (VerificaRoqueEsq(PecaLin,PecaCol,tabuleiro) && !tabuleiro.XequeRei(this.cor))
	        	return true;
	        else
	            return false;
			
	 }
	 
	 public TipoPeca getTipo(){
		 
		return TipoPeca.Rei;
	 }
	 
	 public boolean VerificaRoqueDir(int PecaLin, int PecaCol,Tabuleiro tabuleiro) {
			//rei branco
			if (this.cor == branco) {
				
				// nao se movimentou
				if (this.lin == 4 && this.col == 0 && PecaLin == 7 && PecaCol == 0 && VerificaTorreDireita(tabuleiro)) {
					return true;
				}
				return false;
				
			}
			else {
				if (this.lin == 4 && this.col == 7 && PecaLin == 7 && PecaCol == 7 && VerificaTorreDireita(tabuleiro)) {
					return true;
				}
				return false;
				
			}
		}
	 public boolean VerificaRoqueEsq(int PecaLin, int PecaCol ,Tabuleiro tabuleiro) {
			//rei branco
			if (this.cor == branco) {
				
				if (this.lin == 4 && this.col == 0 && PecaLin == 0 && PecaCol == 0  && VerificaTorreEsquerda(tabuleiro)) {
					return true;
				}
				return false;
				
			}
			else {
				if (this.lin == 4 && this.col == 7 && PecaLin == 0 && PecaCol == 7  && VerificaTorreEsquerda(tabuleiro)) {
					return true;
				}
				return false;
				
			}
		}
	 
	 private boolean VerificaTorreDireita(Tabuleiro tabuleiro) {
		 
		 if( this.cor == branco) {
			 if( tabuleiro.LocalizaPeca(7,0) != null && tabuleiro.LocalizaPeca(7,0).getTipo() == TipoPeca.Torre) {
				 
				 if(!tabuleiro.posicaoOcupada(5, 0) && !tabuleiro.posicaoOcupada(6, 0))
					 return true;
				 
				 return false;
			 }
			 return false;
		 }
		 else {
			 
			 if( tabuleiro.LocalizaPeca(7,7) != null && tabuleiro.LocalizaPeca(7,7).getTipo() == TipoPeca.Torre) {
				 
				 if(!tabuleiro.posicaoOcupada(5, 7) && !tabuleiro.posicaoOcupada(6, 7))
					 return true;
				 
				 return false;
			 }
			 return false;
			 
		 }
	 }
	 
	 private boolean VerificaTorreEsquerda(Tabuleiro tabuleiro) {
		 
		 if(this.cor == branco) {
			 if( tabuleiro.LocalizaPeca(0,0) != null && tabuleiro.LocalizaPeca(0,0).getTipo() == TipoPeca.Torre) {
				 
				 if(!tabuleiro.posicaoOcupada(1, 0) && !tabuleiro.posicaoOcupada(2, 0) && !tabuleiro.posicaoOcupada(3, 0))
					 return true;
				 
				 return false;
			 }
			 return false;
		 }
		 else {
			 
			 if( tabuleiro.LocalizaPeca(0,7) != null && tabuleiro.LocalizaPeca(0,7).getTipo() == TipoPeca.Torre) {
				 
				 if(!tabuleiro.posicaoOcupada(1, 7) && !tabuleiro.posicaoOcupada(2, 7) && !tabuleiro.posicaoOcupada(3, 7) )
					 return true;
				 
				 return false;
			 }
			 return false;
			 
		 }
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
