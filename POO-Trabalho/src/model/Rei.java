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

	        if(this.lin-1 ==PecaLin && this.col == PecaCol && (!tabuleiro.posicaoOcupada(PecaLin, PecaCol) || (tabuleiro.LocalizaPeca(PecaLin, PecaCol).getColor() == adversario)) )
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
	        else
	            return false;
	 }
	 
	 public TipoPeca getTipo(){
		 
		return TipoPeca.Rei;
	 }
	 
	 public Vector<Posicoes> VetorMovimentos(Tabuleiro tabuleiro) {
		 Vector<Posicoes> pos = new Vector<Posicoes>();

	        for(int i = 0; i < 8;i++ ){
	            for(int j = 0; j < 8;j++){
	                if( MovimentosPermitidos(i, j,tabuleiro))
	                    pos.add(new Posicoes(i, j));
	            }
	        }
	        return pos;
	}

}
