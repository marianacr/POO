package view;

import controller.ControladorTabuleiro;
import model.Tabuleiro;



public class Iniciar{

	private static TelaInicial frameIniciar;
	private static ControladorTabuleiro controlador;
    public final int LARG_DEFAULT=800;
    public final int ALT_DEFAULT=800;
	
	public static void main(String[] args) {
		
	 
		frameIniciar = new TelaInicial();
	}
	

	public static void Comecar() {
		controlador = ControladorTabuleiro.getControladorTabuleiro(null);	
	 	controlador.addObserver(controlador.frame.painelTabuleiro);
		
	}
	
	public static void ReIniciar() {
		
		controlador.EncerraControladorTabuleiro();
		frameIniciar = new TelaInicial();

		
	}


	public static void Carregar(Tabuleiro tabuleiroCarregado) {
		controlador = controlador.getControladorTabuleiro(tabuleiroCarregado);
		controlador.addObserver(controlador.frame.painelTabuleiro);
		
		
		
	}
	
	public static void Salvar() {
		controlador.EncerraControladorTabuleiro();
		frameIniciar = new TelaInicial();
	
		
		
		
	}


	
	


	
		
	
}
