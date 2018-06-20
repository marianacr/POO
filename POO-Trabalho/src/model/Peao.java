package model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Peao extends Pecas{
	
	public boolean PrimeiroMovimento;
	
	public Peao(int PecaLin, int PecaCol, int PecaCor)
	{
		lin = PecaLin;
		col = PecaCol;
		cor = PecaCor;
		PrimeiroMovimento = true;
		try {
			
			if (cor == 1) {
				img = ImageIO.read(new File("Imagens/CyanP.png"));
			}
			else {
				img = ImageIO.read(new File("Imagens/PurpleP.png"));
			}
			
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	public boolean MovimentosPermitidos(int PecaLin, int PecaCol, Tabuleiro tabuleiro) {
		 		// verifica se esta no primeiro movimento ou nao;
		 		QtdMovimento(tabuleiro);
		
			 	if (this.cor == branco){
			 		
			 		if(PrimeiroMovimento){
						if(this.lin == PecaLin && this.col+2 == PecaCol && !tabuleiro.posicaoOcupada(PecaLin, PecaCol))
							return true;
						else if(this.lin == PecaLin && this.col+1 == PecaCol && !tabuleiro.posicaoOcupada(PecaLin, PecaCol))
							return true;
						else if(this.lin-1 ==PecaLin && this.col+1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) && (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == preto))
							return true;
						else if(this.lin+1 == PecaLin && this.col+1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) &&(tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == preto))
							return true;
						else
							return false;
					}
			 		// nao é a primeira jogada, so pode andar uma casa
					else
					{
						if(this.lin  == PecaLin && this.col+1 == PecaCol && !tabuleiro.posicaoOcupada(PecaLin, PecaCol))
							return true;
						else if(this.lin -1 == PecaLin && this.col+1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) && (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == preto))
							return true;
						else if(this.lin +1 == PecaLin && this.col+1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) && (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == preto))
							return true;
						else
							return false;
					}
			 		
			 		
			 	}
			 	// peao preto 
			 	else {
			 		
			 		if(PrimeiroMovimento)
					{
						if(this.lin  == PecaLin && this.col-2 == PecaCol && !tabuleiro.posicaoOcupada(PecaLin, PecaCol))
							return true;
						else if(this.lin  == PecaLin && this.col-1 == PecaCol && !tabuleiro.posicaoOcupada(PecaLin, PecaCol))
							return true;
						else if(this.lin-1 == PecaLin && this.col-1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) && (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == branco))
							return true;
						else if(this.lin+1 == PecaLin && this.col-1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) && (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == branco))
							return true;
						else 
							return false;
					}
					else
					{
						if(this.lin  == PecaLin && this.col-1 == PecaCol && !tabuleiro.posicaoOcupada(PecaLin, PecaCol))
							return true;
						else if(this.lin-1 == PecaLin && this.col-1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) && (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == branco))
							return true;
						else if(this.lin+1 == PecaLin && this.col-1 == PecaCol && tabuleiro.posicaoOcupada(PecaLin, PecaCol) && (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == branco))
							return true;
						else 
							return false;
					}
			 	}
			 		
				
	 }
	public TipoPeca getTipo() {
		 
		return TipoPeca.Peao;
	 }
	private void QtdMovimento(Tabuleiro tabuleiro) {
		 
		 if(this.cor == preto)
	        {
	            if(this.col == 6 && !tabuleiro.posicaoOcupada(this.lin,5))
	            	PrimeiroMovimento = true;
	            else
	            	PrimeiroMovimento = false;
	        }
	        else
	        {
	            if(this.col == 1 && !tabuleiro.posicaoOcupada(this.lin,2))
	            	PrimeiroMovimento = true;
	            else
	            	PrimeiroMovimento = false;
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
