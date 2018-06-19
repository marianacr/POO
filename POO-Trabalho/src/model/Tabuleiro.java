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

}

