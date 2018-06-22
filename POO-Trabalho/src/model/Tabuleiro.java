package model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import view.ObservaSujeito;

public class Tabuleiro implements Serializable {
	
	public static final int Tam_Tabuleiro = 8;
	public Pecas[][] tabuleiro = new Pecas[Tam_Tabuleiro][Tam_Tabuleiro];
	
	public Tabuleiro()
	{
			for(int i = 0; i < Tam_Tabuleiro; i++)
			{
				for(int j = 0; j <Tam_Tabuleiro; j++){
		                tabuleiro[i][j] = null;
		        }
		     }
			
			
	}
	
	public void TabuleiroInicializar()
	{
			
			
			InicializaPecas();
	}
	public void TabuleiroCarregar(Tabuleiro tabuleiroAux) {
		Pecas p0,p1;
		for(int i = 0; i< 8 ; i++) {
			for(int j = 0; j < 8; j ++) {
					p0 = tabuleiroAux.LocalizaPeca(i,j);
				
					if (p0 != null) {
							p1 = CriaPeca(p0.getLin(), p0.getCol(),p0.getTipo(), p0.getColor());
						    addPeca(p1);
					}
				}
			}
	}

	
	
	 private void InicializaPecas() {
		 // Posiciona as Pecas do xadrez
		 
		    addPeca(new Torre(0,0,Pecas.branco));
		    addPeca(new Torre(7,0,Pecas.branco));
		    addPeca(new Torre(7,7,Pecas.preto));
		    addPeca(new Torre(0,7,Pecas.preto));
		
	        
		    addPeca(new Cavalo(1,0,Pecas.branco));
		    addPeca(new Cavalo(6,0,Pecas.branco));
		    addPeca(new Cavalo(6,7,Pecas.preto));
		    addPeca(new Cavalo(1,7,Pecas.preto));

		    addPeca(new Bispo(2,0,Pecas.branco));
		    addPeca(new Bispo(5,0,Pecas.branco));
		    addPeca(new Bispo(2,7,Pecas.preto));
		    addPeca(new Bispo(5,7,Pecas.preto));

		    addPeca(new Rainha(3,0,Pecas.branco));
		    addPeca(new Rainha(3,7,Pecas.preto));
		    
		    addPeca(new Rei(4,0,Pecas.branco));
		    addPeca(new Rei(4,7,Pecas.preto));         
      
	        for(int i=0; i<8; i++){
	            addPeca(new Peao(i,1,Pecas.branco));
	        }

	       
	        for(int i=0; i<8; i++){
	            addPeca(new Peao(i,6,Pecas.preto));
	        }

	 }
	public Pecas LocalizaPeca(int lin, int col)
	{
		if (tabuleiro[lin][col] == null)
			return null ;
					
		else {
			return tabuleiro[lin][col];
		}
			
	}
	public void addPeca(Pecas peca)
	{
		tabuleiro[peca.lin][peca.col] = peca;
		
	}
	public void removePeca(int lin, int col)
	{
		tabuleiro[lin][col] = null;
		return;
	}
	public boolean posicaoOcupada(int lin, int col) {
		if (tabuleiro[lin][col] == null)
			return false;
		else
			return true;
		
	}
	
	public Pecas CriaPeca(int lin, int col, TipoPeca tipo, int cor)
	{	
		Pecas p = null;
		if(tipo == TipoPeca.Torre) {
			p = new Torre(lin, col,cor);
		}
		if(tipo == TipoPeca.Cavalo) {
			p = new Cavalo(lin, col,cor);
		}
		if(tipo == TipoPeca.Bispo) {
			p = new Bispo(lin, col,cor);
		}
		if(tipo == TipoPeca.Rei) {
			p = new Rei(lin, col,cor);
		}
		if(tipo == TipoPeca.Rainha) {
			p = new Rainha(lin, col,cor);
		}
		if(tipo == TipoPeca.Peao) {
			p = new Peao(lin, col,cor);
		}
		return p;
	
		
	}
	
	// jogada valida , verifico se vai tirar o xeque
	public boolean VerificaJogadaXeque(Pecas p,int posX, int posY, Tabuleiro tabuleiro){
			// Clono o tabuleiro 
			Tabuleiro tabuleiroAux = new Tabuleiro();
			
			for(int i = 0; i< 8 ; i++) {
				for(int j = 0; j < 8; j ++) {
					Pecas p0 = LocalizaPeca(i,j);
					if (p0 != null) {
						Pecas p1 = CriaPeca(p0.getLin(), p0.getCol(),p0.getTipo(), p0.getColor());
						tabuleiroAux.addPeca(p1);
					}
				}
			}
			
			
			//faco o movimento da peca
			tabuleiroAux.removePeca(p.getLin(),p.getCol());
			// crio a peca e add no novo tabuleiro
			Pecas p2 = CriaPeca(posX,posY,p.getTipo(),p.getColor());
			tabuleiroAux.addPeca(p2);
			// verifico se com essa jogada o rei ainda esta em xeque 
			if (!tabuleiroAux.XequeRei(p2.getColor())) {
				return false;
			}
			else
				return true;
			
			
		}
		
	
	public boolean XequeRei(int cor){
		
		Pecas rei = this.LocalizaRei(cor);
		if ( rei != null) {
        for(int x = 0; x< 8 ; x++){
            for(int y = 0; y < 8; y++){
                if(this.LocalizaPeca(x, y) != null){
                    if(this.LocalizaPeca(x, y).MovimentosPermitidos(rei.getLin(),rei.getCol(), this) &&
                    									this.LocalizaPeca(x, y).getColor()!= rei.getColor()){
                        return true;
                    }
                }
            }
        }
		}

        return false;
    }
	
	public Pecas LocalizaRei(int cor) {
		
		
		for(int i =0; i<8; i++) {
			for (int j = 0;j <8; j++) {
				if ( tabuleiro[i][j] != null) {
					if( tabuleiro[i][j].getTipo() == TipoPeca.Rei && tabuleiro[i][j].getColor() == cor ) {
						return tabuleiro[i][j];
						
					}
				}
			}
		}
		return null;
		
	}
	
	public void Roque(Pecas pecaPrimeiroClick, int velhoX, int velhoY, Pecas pecaSegundoClick, int posX, int posY, Tabuleiro tabuleiro) {
		 // rei branco 
		if (pecaPrimeiroClick.getColor() == 1) {
			
			// torre da esquerda
			if (pecaSegundoClick.getLin() == 0) {
				
				//remove torre
				tabuleiro.removePeca(posX, posY);
				// crio a peca antiga no novo local
				Pecas p = tabuleiro.CriaPeca(2,0,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
				// add a nova peca a sua nova posicao
				tabuleiro.addPeca(p);
				// removo a peca da sua posicao antiga
				tabuleiro.removePeca(velhoX, velhoY);
				
				Pecas p2 = tabuleiro.CriaPeca(3,0,pecaSegundoClick.getTipo(),pecaSegundoClick.getColor());
				tabuleiro.addPeca(p2);
			}
			// torre a direita
			else {
				
				tabuleiro.removePeca(posX, posY);
				Pecas p = tabuleiro.CriaPeca(6,0,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
				tabuleiro.addPeca(p);
				tabuleiro.removePeca(velhoX, velhoY);
				Pecas p2 = tabuleiro.CriaPeca(5,0,pecaSegundoClick.getTipo(),pecaSegundoClick.getColor());
				tabuleiro.addPeca(p2);
				
			}
				
		}
		//peca preta
		else {
			
			// torre da esquerda
			if (pecaSegundoClick.getLin() == 0) {
				
				//remove torre
				tabuleiro.removePeca(posX, posY);
				// crio a peca antiga no novo local
				Pecas p = tabuleiro.CriaPeca(2,7,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
				// add a nova peca a sua nova posicao
				tabuleiro.addPeca(p);
				// removo a peca da sua posicao antiga
				tabuleiro.removePeca(velhoX, velhoY);
				
				Pecas p2 = tabuleiro.CriaPeca(3,7,pecaSegundoClick.getTipo(),pecaSegundoClick.getColor());
				tabuleiro.addPeca(p2);
			}
			// torre a direita
			else {
				
				tabuleiro.removePeca(posX, posY);
				Pecas p = tabuleiro.CriaPeca(6,7,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
				tabuleiro.addPeca(p);
				tabuleiro.removePeca(velhoX, velhoY);
				Pecas p2 = tabuleiro.CriaPeca(5,7,pecaSegundoClick.getTipo(),pecaSegundoClick.getColor());
				tabuleiro.addPeca(p2);
				
			}
			
		}
	}

	// nao pode estar em cheque
	public boolean Congelamento(int cor, Tabuleiro tabuleiro) {
		Pecas p0,p1;
		Vector<Posicoes> pos;
		
		Tabuleiro tabuleiroAux = ReconstroiTabuleiro(tabuleiro);
		Tabuleiro tabuleiroAux2 = null;
		
		// encontro todas as pecas no tabuleiro que sao da cor selecionada
		for(int i = 0; i< 8 ; i++) {
			for(int j = 0; j < 8; j ++) {
				     p0 = tabuleiroAux.LocalizaPeca(i,j);
				     
				    // se a peca nao for nula e a cor dela for a cor da peca selecionada, obtem todos os movimentos posiveis pra ela
					if (p0 != null && p0.getColor() == cor) {
						
							pos = p0.VetorMovimentos(tabuleiroAux,false);
							if (pos != null) {
								// faz todos os movimentos possiveis e verifica 
								for (int k = 0; k < pos.size(); k++) {
									
									// Reconstruo o tabuleiro de onde estava e faco a proxima jogada da peca
									tabuleiroAux2 = ReconstroiTabuleiro(tabuleiroAux);
									//faco o movimento da peca
									tabuleiroAux2.removePeca(p0.getLin(),p0.getCol());
									// crio a peca e add no novo tabuleiro
									p1 = CriaPeca( pos.get(k).getX(),pos.get(k).getY(),p0.getTipo(),p0.getColor());
									tabuleiroAux2.addPeca(p1);
									// verifico se com essa jogada o rei ainda esta em xeque 
									
									if (!tabuleiroAux2.XequeRei(p1.getColor())) {
										return false; // existe uma jogada que eh valida e nao cause xeque
									}
											
								}
							}
					}
				}
			}
		return true; //nao existe nenhuma jogada valida da cor selecionada que nao cause xeque ou que possa mover
		
		
		
		
				
	}
	
	private Tabuleiro ReconstroiTabuleiro(Tabuleiro tabuleiro) {
		
		
		
		Tabuleiro tabuleiroAux = new Tabuleiro();
		tabuleiroAux.TabuleiroCarregar(tabuleiro);
		
		return tabuleiroAux;
		
	}

}

