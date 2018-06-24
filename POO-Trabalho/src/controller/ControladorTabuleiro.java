package controller;

import model.Pecas;
import model.Posicoes;


import model.Tabuleiro;
import model.TipoPeca;

import view.TabuleiroFrame;

import view.ObservaSujeito;
import view.Sujeito;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class ControladorTabuleiro implements MouseListener, Sujeito {
	
	public static TabuleiroFrame frame;
	public static Tabuleiro tabuleiro;
	private int repintar = 1;
	private int promocaoPeao = 2;
	private int XequeMateBranco = 3, XequeMatePreto = 4, congelamento = 5, salvar = 6, vezErrada = 7;
	private int alturaFrame, alturaQuadrado,larguraFrame,larguraQuadrado;
	private int posX, posY, velhoX, velhoY;
	private int numClick = 0;
	private final static int preto = 0;
	private final static int branco = 1;
	public static int jogadorVez = branco;
	private boolean ReiXeque;
	private boolean PrimeiroClick = true;
	private Pecas pecaPrimeiroClick,pecaSegundoClick;
	private Vector<Posicoes> posicoesPossiveis;
	private ArrayList<ObservaSujeito> listaObservadores;

	
	private static ControladorTabuleiro controlador = null;
	// controlador cria o tabuleiro e a frame
	private ControladorTabuleiro(Tabuleiro tabuleiroCarregado) {
		
	tabuleiro = new Tabuleiro();
	if (tabuleiroCarregado == null)
		tabuleiro.TabuleiroInicializar();
	else
		tabuleiro.TabuleiroCarregar(tabuleiroCarregado);
	frame = new TabuleiroFrame(tabuleiro);
	frame.pack();
	frame.setResizable(true);
	frame.setLocationRelativeTo( null );
	frame.setVisible(true);	
	frame.addMouseListener(this);
	listaObservadores = new ArrayList<ObservaSujeito>();
	
	}
	
	public static ControladorTabuleiro getControladorTabuleiro(Tabuleiro tabuleiroCarregado) {
		if (controlador == null) {
			controlador = new ControladorTabuleiro(tabuleiroCarregado);
		}
		return controlador;
	}
	 
	public static void EncerraControladorTabuleiro() {
		controlador = null;
	}
	
	 public static void fecharFrame() {
	    	frame.dispose();
	    }
	
		
	public void mouseClicked(MouseEvent e) {
		notificaObservers(repintar);
		// verifica se foi com o botao direito
		if(SwingUtilities.isRightMouseButton(e)) {
			//gera popUp e tabuleiro deve ser salvo
			notificaObservers(salvar);
		}
		//transformar a frame nos quadrados
		localizaQuadrado(e.getX(),e.getY());
		
	
		// primeiro click so é validado quando clica numa casa não vazia
		
		if ( numClick == 0 && tabuleiro.LocalizaPeca(posX, posY)!= null) {
			
			
			// colorir o quadrado selecionado com uma nova cor
			
			frame.painelTabuleiro.QuadradoSelecionado(posX, posY);
			
			// localiza a peca e colore a peca selecionada, verifica suas jogadas e colore as jogadas 
			// permitidas no tabuleiro
			
			pecaPrimeiroClick = tabuleiro.LocalizaPeca(posX, posY);
			//verifica se eh o primeiro clique e se a peca eh branca
			if (PrimeiroClick) {
				System.out.println("PrimeiroClick VALIDO" + PrimeiroClick );
				if(pecaPrimeiroClick.getColor() == 1) {
					// existe peca, logo validamos o click 
					
					numClick++;
					System.out.println("click valido , numero click 0");
				
					// se o rei da peca estiver em xeque, vai obter as posicoes para retira-las
					if ( tabuleiro.XequeRei(pecaPrimeiroClick.getColor()) ) {
						
						System.out.println("REI EM CHEQUE");
						ReiXeque = true;
						
						posicoesPossiveis = pecaPrimeiroClick.VetorMovimentos(tabuleiro,true);
						
					}
					else {
						// verifico congelamento
						if(tabuleiro.Congelamento(pecaPrimeiroClick.getColor(), tabuleiro)) {
							
							notificaObservers(congelamento);
						}
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
				//peca do primeiro clique nao branca, a vez fica com branco e o clique nao eh valido
				else {
					
					jogadorVez = branco;
					numClick = 0;
					notificaObservers(vezErrada);
					
				}
			}
			// Se nao for primeiro clique, verifica se eh o jogador da vez
			else {
				System.out.println("PrimeiroClick INVALIDO" + PrimeiroClick );
				// jogada eh valida
				if( jogadorVez == pecaPrimeiroClick.getColor() ) {
					System.out.println("jogadorVez" + jogadorVez + " pecaPrimeiroClick " + pecaPrimeiroClick);
					// validamos o click 
					
					numClick++;
					System.out.println("click valido , numero click 0");
				
					// se o rei da peca estiver em xeque, vai obter as posicoes para retira-las
					if ( tabuleiro.XequeRei(pecaPrimeiroClick.getColor()) ) {
						
						System.out.println("REI EM CHEQUE");
						ReiXeque = true;
						
						posicoesPossiveis = pecaPrimeiroClick.VetorMovimentos(tabuleiro,true);
						
					}
					else {
						// verifico congelamento
						if(tabuleiro.Congelamento(pecaPrimeiroClick.getColor(), tabuleiro)) {
							
							notificaObservers(congelamento);
						}
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
				else {
					notificaObservers(vezErrada);
				}
				
			}
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
				//invalida primeiro clique e nao valida mais
				PrimeiroClick = false;
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
				//verifica a vez do jogador
				if (pecaPrimeiroClick.getColor() == branco)
					jogadorVez = preto;
				else
					jogadorVez = branco;
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
					//nao precisa verificar se eh o primeiro clique, chegou aqui ja nao eh
					
					
					// colorir o quadrado selecionado com uma nova cor
					
					frame.painelTabuleiro.QuadradoSelecionado(posX, posY);
					
					// localiza a peca e colore a peca selecionada, verifica suas jogadas e colore as jogadas 
					// permitidas no tabuleiro
					
					pecaPrimeiroClick = tabuleiro.LocalizaPeca(posX, posY);
					// verifica se clicou na cor do jogador da vez
					if ( pecaPrimeiroClick.getColor() == jogadorVez ) {
						
						//como se fosse o primeiro click e valida o clique
						numClick = 1;
						System.out.println("click VALIDO , numero click "+ numClick);
						
						// verifica se o rei esta em cheque
						if ( tabuleiro.XequeRei(pecaPrimeiroClick.getColor()) ) {
							
							System.out.println("REI EM CHEQUE");
							ReiXeque = true;
							
							posicoesPossiveis = pecaPrimeiroClick.VetorMovimentos(tabuleiro,true);
							
						}
						else {
							// verifico congelamento
							if(tabuleiro.Congelamento(pecaPrimeiroClick.getColor(), tabuleiro)) {
								
								notificaObservers(congelamento);
							}
							
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
					// nao eh o jogador da vez, recomecar
					else {
						
						numClick = 0;
						System.out.println("click INVALIDO POR NAO SER A VEZ , numero click "+ numClick);
						notificaObservers(vezErrada);
						
					}
					
				}
				// movimento eh valido
				else {
					//invalida primeiro clique e nao valida mais
					PrimeiroClick = false;
					pecaSegundoClick = tabuleiro.LocalizaPeca(posX, posY);
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
							
							// -- ROQUE
							if( pecaPrimeiroClick.getTipo() == TipoPeca.Rei && pecaSegundoClick.getTipo() == TipoPeca.Torre && pecaPrimeiroClick.getColor() == pecaSegundoClick.getColor()) {
								 tabuleiro.Roque(pecaPrimeiroClick, velhoX, velhoY, pecaSegundoClick, posX, posY, tabuleiro);
								
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
								
							//XEQUE MATE	
							if(pecaSegundoClick.getTipo() == TipoPeca.Rei && pecaPrimeiroClick.getColor() != pecaSegundoClick.getColor()) {
								if (pecaPrimeiroClick.getColor() == 1)
									notificaObservers(XequeMateBranco);
								else {
									notificaObservers(XequeMatePreto);
								}
							}
							
							//-- PROMOCAO DO PEAO!
							if (pecaPrimeiroClick.getTipo() == TipoPeca.Peao && pecaPrimeiroClick.getColor() == 1  && posY == 7 ||
									pecaPrimeiroClick.getTipo() == TipoPeca.Peao && pecaPrimeiroClick.getColor() == 0  && posY == 0) {
									notificaObservers(promocaoPeao);
							}
						}
					}
						
						
					//atualizar aqui quem joga na proxima
					if (pecaPrimeiroClick.getColor() == branco)
						jogadorVez = preto;
					else
						jogadorVez = branco;
					numClick = 0;
					notificaObservers(repintar);
						
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
			//verifica a vez do jogador
			if (pecaPrimeiroClick.getColor() == branco)
				jogadorVez = preto;
			else
				jogadorVez = branco;
			
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