package model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Cavalo extends Pecas{
	
	 public Cavalo(int PecaLin, int PecaCol, int PecaCor)
	 {
		lin = PecaLin;
		col = PecaCol;
		cor = PecaCor;
		try {
			
			if (cor == 1) {
				img = ImageIO.read(new File("Imagens/CyanN.png"));
			}
			else {
				img = ImageIO.read(new File("Imagens/PurpleN.png"));
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
		 	else
		 		adversario = preto;
		 	
		 	Posicoes inicio = new Posicoes(this.lin-1, this.col-2);
	        Posicoes fim = new Posicoes(PecaLin, PecaCol);
	       
	        
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;

	        inicio.setPosicoes(this.lin+1, this.col-2);
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;

	        inicio.setPosicoes(this.lin+2, this.col-1);
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;

	        inicio.setPosicoes(this.lin+2, this.col+1);
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;

	        inicio.setPosicoes(this.lin+1, this.col+2);
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;

	        inicio.setPosicoes(this.lin-1, this.col+2);
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;

	        inicio.setPosicoes(this.lin-2, this.col-1);
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;

	        inicio.setPosicoes(this.lin-2, this.col+1);
	        if( helperMovimento(inicio, fim, adversario, tabuleiro))
	            return true;
	        else
	            return false;
		}

	 public TipoPeca getTipo() {
		 
		return TipoPeca.Cavalo;
	 }
	 
	 private boolean helperMovimento(Posicoes inicio,Posicoes fim, int adversario, Tabuleiro tabuleiro)
	 {
	 	//se o movimento esta correto e a posicao final nao esta ocupada
        if( inicio.getX() == fim.getX() && inicio.getY() == fim.getY() && !tabuleiro.posicaoOcupada(fim.getX(), fim.getY()))
            return true;
      //se o movimento esta correto e a posicao final esta ocupada
        else if( inicio.getX() == fim.getX() && inicio.getY() == fim.getY() && tabuleiro.posicaoOcupada(fim.getX(), fim.getY()) && (tabuleiro.LocalizaPeca(fim.getX(), fim.getY()).getColor() == adversario))
        	return true;
        else
            return false;
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
