package controller;


public class Iniciar {

	public static void main(String[] args) {
	
		
	   ControladorTabuleiro controlador = ControladorTabuleiro.getControladorTabuleiro();	
	   controlador.addObserver(controlador.frame.painelTabuleiro);
		// se a partidar terminar, salva e coloca ControladorTabuleiro.getControladorTabuleiro().encerraControladorTabuleiro()
	}

}
