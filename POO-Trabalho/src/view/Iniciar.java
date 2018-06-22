package view;

import javax.swing.JFrame;

import javax.swing.WindowConstants;

import controller.ControladorTabuleiro;
import model.Tabuleiro;



public class Iniciar{

	private static TelaInicial frameIniciar;
	private static ControladorTabuleiro controlador;
	private JFrame telaHome = new JFrame("Xadrex");
    public final int LARG_DEFAULT=800;
    public final int ALT_DEFAULT=800;
	
	public static void main(String[] args) {
		
	 
		frameIniciar = new TelaInicial();
	}
	

	public static void Iniciar() {
		controlador = ControladorTabuleiro.getControladorTabuleiro(null);	
	 	controlador.addObserver(controlador.frame.painelTabuleiro);
		
	}
	
	public static void ReIniciar() {
		
		controlador.EncerraControladorTabuleiro();
		controlador = controlador.getControladorTabuleiro(null);	
		controlador.addObserver(controlador.frame.painelTabuleiro);
		
	}


	public static void Carregar(Tabuleiro boardLoaded) {
		controlador = controlador.getControladorTabuleiro(boardLoaded);
		controlador.addObserver(controlador.frame.painelTabuleiro);
		
		
		
	}
	
	public static void Salvar() {
		controlador.EncerraControladorTabuleiro();
		frameIniciar = new TelaInicial();
	
		
		
		
	}


	
	


	
		
	
}
