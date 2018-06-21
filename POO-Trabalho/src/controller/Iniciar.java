package controller;

import view.ObservaSujeito;

public class Iniciar{

	
	public static void main(String[] args) {
	
		ControladorTabuleiro controlador = ControladorTabuleiro.getControladorTabuleiro();	
	 	controlador.addObserver(controlador.frame.painelTabuleiro);
	   
	}
	


	
		
	
}
