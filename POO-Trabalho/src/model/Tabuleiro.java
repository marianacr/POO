package model;


public class Tabuleiro {
	
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
	

}

