package model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Rainha extends Pecas{
	
	public Rainha(int PecaLin, int PecaCol, int PecaCor)
	{
		lin = PecaLin;
		col = PecaCol;
		cor = PecaCor;
		try {
			
			if (cor == 1) {
				img = ImageIO.read(new File("Imagens/CyanQ.png"));
			}
			else {
				img = ImageIO.read(new File("Imagens/PurpleQ.png"));
			}
			
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public TipoPeca getTipo() {
		 
		return TipoPeca.Rainha;
	 }
	
	// seus movimentos sao o do bispo + o da torre
	 public boolean MovimentosPermitidos(int PecaLin, int PecaCol, Tabuleiro tabuleiro){
		 
		    
	        boolean bispo = MovimentosPermitidosBispo(PecaLin, PecaCol, tabuleiro);
	        //jogadas do bispo nao foram aceitas, tentaremos as jogadas da torre
	        if(!bispo)
	        {
	            
	            boolean torre = MovimentosPermitidosTorre(PecaLin, PecaCol ,tabuleiro);
	            return torre;
	        }
	        else
	            return true;

	}
	 
	 private boolean MovimentosPermitidosBispo(int PecaLin, int PecaCol, Tabuleiro tabuleiro){

		 if(this.lin == PecaLin || this.col == PecaCol )
				return false;

		 int direcao = obtemDirecaoBispo(PecaLin, PecaCol);
		 Posicoes inicio = new Posicoes(this.lin, this.col);
		 Posicoes fim = new Posicoes(PecaLin, PecaCol);
		 
		
		
		if(this.cor == branco ) { 
			return VerificaPosicoesBispo(inicio, fim,preto, direcao, tabuleiro, 0);
		}
		else if (this.cor == preto){
			return VerificaPosicoesBispo(inicio, fim, branco, direcao, tabuleiro, 0);
		}
		
		return true;
	 }
	 
	 private int obtemDirecaoBispo(int PecaLin, int PecaCol) {
			
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
	 private boolean VerificaPosicoesBispo(Posicoes inicio, Posicoes fim, int adversario, int direcao, Tabuleiro tabuleiro, int qtdPeca){
				
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
					
					return VerificaPosicoesBispo(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
				//desce para direita
				else if(direcao == 2)
				{
					inicio.setPosicoes(inicio.getX()+1, inicio.getY()+1);
					if(inicio.getX() > fim.getX() || inicio.getY() > fim.getY())
						return false;
					
					return VerificaPosicoesBispo(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
				//desce para esquerda
				else if(direcao == 3)
				{
					inicio.setPosicoes(inicio.getX()-1, inicio.getY()+1);
					if(inicio.getX() < fim.getX() || inicio.getY() > fim.getY())
						return false;
					
					return VerificaPosicoesBispo(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
				//sobe para esquerda
				else
				{
					inicio.setPosicoes(inicio.getX()-1, inicio.getY()-1);
					if(inicio.getX() < fim.getX() || inicio.getY() < fim.getY())
						return false;
					
					return VerificaPosicoesBispo(inicio, fim, adversario, direcao, tabuleiro, 1);
				}
			}
		}

	 private boolean MovimentosPermitidosTorre(int PecaLin, int PecaCol, Tabuleiro tabuleiro) {
		 
		 if(this.lin != PecaLin && this.col != PecaCol) 
		    	return false;
	            
	        else if(this.lin == PecaLin && this.col == PecaCol)
	        	return false;
	        
	           

	        int direcao = obtemDirecaoTorre(PecaLin, PecaCol);
	        Posicoes inicio = new Posicoes(this.lin, this.col);
	        Posicoes fim = new Posicoes(PecaLin, PecaCol);
	        

	        
	        if(this.cor == branco)
	        {
	            return VerificaPosicoesTorre(inicio, fim,preto, direcao, tabuleiro, 0);
	        }
	        else if(this.cor == preto)
	        {
	            return VerificaPosicoesTorre(inicio, fim,branco, direcao, tabuleiro, 0);
	        }
	        
	        return true;
	 }
	 
	 private int obtemDirecaoTorre(int PecaLin, int PecaCol){	
		 //direita
	        if(PecaLin > this.lin)
	            return 1; 
	        //esquerda
	        else if(PecaLin < this.lin)
	            return 3; 
	        //mesma linha
	        else
	        {  
	        	//para baixo
	            if(PecaCol > this.col)
	                return 2; 
	            // para cima
	            else
	                return 4; 
	        }
	 }
	 
	 private boolean VerificaPosicoesTorre(Posicoes inicio, Posicoes fim, int adversario, int direcao, Tabuleiro tabuleiro, int qtdPeca){

		 	if( inicio.Igual(fim) && !tabuleiro.posicaoOcupada(fim.getX(), fim.getY()))
	            return true;
	        else if(inicio.Igual(fim) && tabuleiro.posicaoOcupada(fim.getX(), fim.getY()) && (tabuleiro.LocalizaPeca(fim.getX(), fim.getY()).getColor() == adversario))
	            return true;
	        else if(tabuleiro.posicaoOcupada(inicio.getX(), inicio.getY()) && qtdPeca > 0)
	            return false;
	        else
	        {	
	        	//direita
	            if(direcao == 1)
	            {
	                inicio.setPosicoes(inicio.getX()+1, inicio.getY());
	                if(inicio.getX() > fim.getX())
	                    return false;
	                return VerificaPosicoesTorre(inicio, fim, adversario, direcao, tabuleiro, 1);
	            }
	            //para baixo
	            else if(direcao == 2)
	            {
	                inicio.setPosicoes(inicio.getX(), inicio.getY()+1);
	                if(inicio.getY() > fim.getY())
	                    return false;
	                return VerificaPosicoesTorre(inicio, fim, adversario, direcao, tabuleiro, 1);
	            }
	            //esquerda
	            else if(direcao == 3)
	            {
	                inicio.setPosicoes(inicio.getX()-1, inicio.getY());
	                if(inicio.getX() < fim.getX())
	                    return false;
	                return VerificaPosicoesTorre(inicio, fim, adversario, direcao, tabuleiro, 1);
	            }
	            //para cima
	            else
	            {
	                inicio.setPosicoes(inicio.getX(), inicio.getY()-1);
	                if(inicio.getY() < fim.getY())
	                    return false;
	               return VerificaPosicoesTorre(inicio, fim, adversario, direcao, tabuleiro, 1);
	            }
	        }
			
	    }

	 public Vector<Posicoes> VetorMovimentos(Tabuleiro tabuleiro, boolean Xeque) {
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
