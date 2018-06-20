package controller;

import model.Pecas;
import model.Posicoes;
import model.Bispo;
import model.Rainha;
import model.Rei;
import model.Torre;
import model.Cavalo;
import model.Peao;

import model.Tabuleiro;
import model.TipoPeca;

import view.TabuleiroFrame;
import view.TabuleiroPainel;
import view.ObservaSujeito;
import view.Sujeito;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JOptionPane;


public class ControladorTabuleiro implements MouseListener, Sujeito {
	
	TabuleiroFrame frame;
	private Tabuleiro tabuleiro;
	private int repintar = 1;
	private int promocaoPeao = 2;
	private int alturaFrame, alturaQuadrado,larguraFrame,larguraQuadrado;
	private int posX, posY, velhoX, velhoY;
	private int numClick = 0;
	private boolean ReiXeque;
	private Pecas pecaPrimeiroClick,pecaSegundoClick;
	private Vector<Posicoes> posicoesPossiveis;
	private ArrayList<ObservaSujeito> listaObservadores;

	
	private static ControladorTabuleiro controlador = null;
	// controlador cria o tabuleiro e a frame
	private ControladorTabuleiro() {
		
	tabuleiro = new Tabuleiro();
	tabuleiro.TabuleiroInicializar();
	frame = new TabuleiroFrame(tabuleiro);
	frame.pack();
	frame.setResizable(true);
	frame.setLocationRelativeTo( null );
	frame.setVisible(true);	
	frame.addMouseListener(this);
	listaObservadores = new ArrayList<ObservaSujeito>();
	
	}
	
	public static ControladorTabuleiro getControladorTabuleiro() {
		if (controlador == null) {
			controlador = new ControladorTabuleiro();
		}
		return controlador;
	}

	
	public void mouseClicked(MouseEvent e) {
		
		//transformar a frame nos quadrados
		localizaQuadrado(e.getX(),e.getY());
		
		
		// primeiro click so é validado quando clica numa casa não vazia
		
		if ( numClick == 0 && tabuleiro.LocalizaPeca(posX, posY)!= null) {
			
			// existe peca, logo validamos o click 
			
			numClick++;
			System.out.println("click valido , numero click 0");
			
			// colorir o quadrado selecionado com uma nova cor
			
			frame.painelTabuleiro.QuadradoSelecionado(posX, posY);
			
			// localiza a peca e colore a peca selecionada, verifica suas jogadas e colore as jogadas 
			// permitidas no tabuleiro
			
			pecaPrimeiroClick = tabuleiro.LocalizaPeca(posX, posY);
			// se o rei da peca estiver em xeque, vai obter as posicoes para retira-las
			if ( tabuleiro.XequeRei(pecaPrimeiroClick.getColor()) ) {
				
				System.out.println("REI EM CHEQUE");
				ReiXeque = true;
				
				posicoesPossiveis = pecaPrimeiroClick.VetorMovimentos(tabuleiro,true);
				
			}
			else {
				ReiXeque = false;
				posicoesPossiveis = pecaPrimeiroClick.VetorMovimentos(tabuleiro,false);
			}
			
			frame.painelTabuleiro.posicoesPermitidas(posicoesPossiveis);
			notificaObservers(repintar);
		
			
			// salvando a posicao antiga obter o novo click
			
			velhoX = posX;
			velhoY = posY;
			System.out.println( " velhoX " + velhoX + " velhoY " + velhoY );
		}		
		if (numClick == 1 && tabuleiro.LocalizaPeca(posX, posY)==null ) {
			
			
			// localiza a peca antiga
			pecaPrimeiroClick = tabuleiro.LocalizaPeca(velhoX, velhoY);
		
			
			//verifica se o movimento eh valido
			if( pecaPrimeiroClick.MovimentosPermitidos(posX, posY, tabuleiro) == false) {
				numClick = 0;
				System.out.println("click INVALIDO , numero click "+ numClick);
				notificaObservers(repintar);
			
				
			}
			// movimento eh valido
			else {
				if (ReiXeque) {
					if(!tabuleiro.VerificaJogadaXeque(pecaPrimeiroClick,posX, posY, tabuleiro)) {
				
						Pecas p = tabuleiro.CriaPeca(posX,posY,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
						tabuleiro.addPeca(p);
						tabuleiro.removePeca(velhoX, velhoY);
					}
				}
				else {
					//--	PROMOCAO DO PEAO!
					if (pecaPrimeiroClick.getTipo() == TipoPeca.Peao && pecaPrimeiroClick.getColor() == 1  && posY == 7 ||
							pecaPrimeiroClick.getTipo() == TipoPeca.Peao && pecaPrimeiroClick.getColor() == 0  && posY == 0) {
						
						notificaObservers(promocaoPeao);
					}
					
					Pecas p = tabuleiro.CriaPeca(posX,posY,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
					tabuleiro.addPeca(p);
					tabuleiro.removePeca(velhoX, velhoY);
				}
				
			
				notificaObservers(repintar);
				
				//reseta o clique
				numClick = 0;
				
				
			}
	 }
			// posicao escolhida tem uma peca
		if (numClick == 1 && tabuleiro.LocalizaPeca(posX, posY)!= null ) {
				
				
				// localiza a peca antiga
				pecaPrimeiroClick = tabuleiro.LocalizaPeca(velhoX, velhoY);
				
				//verifica se o movimento eh valido
				if( pecaPrimeiroClick.MovimentosPermitidos(posX, posY, tabuleiro) == false) {
					
					//como se fosse o primeiro click
					
					numClick = 1;
					System.out.println("click VALIDO , numero click "+ numClick);
					
					// colorir o quadrado selecionado com uma nova cor
					
					frame.painelTabuleiro.QuadradoSelecionado(posX, posY);
					
					// localiza a peca e colore a peca selecionada, verifica suas jogadas e colore as jogadas 
					// permitidas no tabuleiro
					
					pecaPrimeiroClick = tabuleiro.LocalizaPeca(posX, posY);
					// verifica se o rei esta em cheque
					if ( tabuleiro.XequeRei(pecaPrimeiroClick.getColor()) ) {
						
						System.out.println("REI EM CHEQUE");
						ReiXeque = true;
						
						posicoesPossiveis = pecaPrimeiroClick.VetorMovimentos(tabuleiro,true);
						
					}
					else {
						ReiXeque = false;
						posicoesPossiveis = pecaPrimeiroClick.VetorMovimentos(tabuleiro,false);
					}
					frame.painelTabuleiro.posicoesPermitidas(posicoesPossiveis);
					notificaObservers(repintar);
				
					
					// salvando a posicao antiga obter o novo click
					
					velhoX = posX;
					velhoY = posY;
					System.out.println( " velhoX " + velhoX + " velhoY " + velhoY );
					
				}
				// movimento eh valido
				else {
					
					pecaSegundoClick = tabuleiro.LocalizaPeca(posX, posY);
					// -- ROQUE
					if( pecaPrimeiroClick.getTipo() == TipoPeca.Rei && pecaSegundoClick.getTipo() == TipoPeca.Torre && pecaPrimeiroClick.getColor() == pecaSegundoClick.getColor() ) {
						Roque();
					}
					
					else {
						
						if(ReiXeque) {
							if(!tabuleiro.VerificaJogadaXeque(pecaPrimeiroClick,posX, posY, tabuleiro)) {
								
								// retiro a peca que foi comida		
								tabuleiro.removePeca(posX, posY);
								// crio a peca antiga no novo local
								Pecas p = tabuleiro.CriaPeca(posX,posY,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
								// add a nova peca a sua nova posicao
								tabuleiro.addPeca(p);
								// removo a peca da sua posicao antiga
								tabuleiro.removePeca(velhoX, velhoY);
								
							}
						}
						else {
							// retiro a peca que foi comida		
							tabuleiro.removePeca(posX, posY);
							// crio a peca antiga no novo local
							Pecas p = tabuleiro.CriaPeca(posX,posY,pecaPrimeiroClick.getTipo(),pecaPrimeiroClick.getColor());
							// add a nova peca a sua nova posicao
							tabuleiro.addPeca(p);
							// removo a peca da sua posicao antiga
							tabuleiro.removePeca(velhoX, velhoY);
							
							//-- PROMOCAO DO PEAO!
							if (pecaPrimeiroClick.getTipo() == TipoPeca.Peao && pecaPrimeiroClick.getColor() == 1  && posY == 7 ||
									pecaPrimeiroClick.getTipo() == TipoPeca.Peao && pecaPrimeiroClick.getColor() == 0  && posY == 0) {
									notificaObservers(promocaoPeao);
							}
						}
						
						}
					
					numClick = 0;
					notificaObservers(repintar);
						
					}
					
					
				
					
					
				}
			}
	
		
		
	
	public void Roque() {
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

	public void localizaQuadrado(int x, int y) {
		
		alturaFrame = frame.getHeight();
		alturaQuadrado = alturaFrame/8;
				
		posX = x/alturaQuadrado;
			
		larguraFrame = frame.getWidth();
		larguraQuadrado = larguraFrame/8;
				
		posY = y/larguraQuadrado;
			
		System.out.println("Clique na posicao [x][y] = ["+posX+"]["+posY+"]");
		System.out.println("Clique na posicao frame = ["+alturaFrame+"]["+larguraFrame+"]");
		System.out.println("Clique na posicao q = ["+alturaQuadrado+"]["+larguraQuadrado+"]");
		System.out.println("Clique na posicao [x][y] = ["+y+"]["+x+"]");
		if(tabuleiro.LocalizaPeca(posX,posY) != null) {
				Pecas peca = tabuleiro.LocalizaPeca(posX,posY);
			if (peca.getColor() == 1) {
				System.out.println("peca apertada = "+peca.getTipo()+ " Cor peca: Branca ["+posX+"]["+posY+"]");
			}
			else {
				System.out.println("peca apertada = "+peca.getTipo()+ " Cor peca: Preta["+posX+"]["+posY+"]");
		}	}
	}
	
	
	public void PromocaoPeao (TipoPeca tipo) {
		
		tabuleiro.removePeca(posX, posY);
		if ( tipo != null) {
			Pecas p3 = tabuleiro.CriaPeca(posX,posY,tipo,pecaPrimeiroClick.getColor());
			tabuleiro.addPeca(p3);
			numClick = 0;
		}
		notificaObservers(repintar);
		
		
			
		
	}

	

	public void mouseEntered(MouseEvent e) {		
	}
	public void mouseExited(MouseEvent e) {		
	}
	public void mousePressed(MouseEvent e) {		
	}
	public void mouseReleased(MouseEvent e) {		
	}
	
/*public boolean XequeRei(int cor){
		
		Pecas rei = tabuleiro.LocalizaRei(cor);
	/*	if ( rei != null) {
        for(int x = 0; x< 8 ; x++){
            for(int y = 0; y < 8; y++){
                if(tabuleiro.LocalizaPeca(x, y) != null){
                    if(tabuleiro.LocalizaPeca(x, y).MovimentosPermitidos(rei.getLin(),rei.getCol(), tabuleiro) &&
                    									tabuleiro.LocalizaPeca(x, y).getColor()!= rei.getColor()){
                        return true;
                    }
                }
            }
        }
		}

        return false;
    }*/
	
	

	

	@Override
	// 1- repaint na tela, 2- promocao do peao, popUp
	public void notificaObservers(int i) {
		for (Iterator<ObservaSujeito> it = listaObservadores.iterator(); it.hasNext();)
	        {
				ObservaSujeito o = it.next();
	            o.update(i);
	        }
		
	}

	@Override
	public void addObserver(ObservaSujeito o) {
		listaObservadores.add(o);
		
	}

	@Override
	public void removeObserver(ObservaSujeito o) {
		listaObservadores.remove(listaObservadores.indexOf(o));
		
	}

	


}