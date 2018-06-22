package view;



import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.xml.internal.ws.util.ReadAllStream;

import controller.ControladorTabuleiro;
import model.Bispo;
import model.Cavalo;
import model.Peao;
import model.Pecas;
import model.Rainha;
import model.Rei;
import model.Tabuleiro;
import model.TipoPeca;
import model.Torre;
public class SalvarCarregar {
	
private static int cor;
public static Tabuleiro tabuleiroAux;
	
public static boolean Salvar(){
        
        JFileChooser arquivo = new JFileChooser();
        arquivo.setDialogTitle("Save As");   
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Jogos Salvos","txt");
        arquivo.setFileFilter(filtro);
 
        int selecionado = arquivo.showSaveDialog(null);
 
        if (selecionado == JFileChooser.APPROVE_OPTION) {
            File salvarArquivo = arquivo.getSelectedFile();
            try{
                FileOutputStream Fout = new FileOutputStream(salvarArquivo.getAbsolutePath()+".txt");
                PrintStream ArquivoEscrever = new PrintStream(Fout);
                EscreveTabuleiro(ControladorTabuleiro.tabuleiro,ArquivoEscrever);
                ArquivoEscrever.close();
                Fout.close();
            }
            catch(IOException e){
              e.printStackTrace();
            }
            System.out.println("Save as file: " + salvarArquivo.getAbsolutePath());
            return true;
        }
        return false;
    }

    public static void Carregar(){
    	
        JFileChooser arquivo = new JFileChooser();
        arquivo.setDialogTitle("Abrir Jogo Carregado");   
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Jogos Salvos","txt");
        arquivo.setFileFilter(filtro);
 
        int selecionado = arquivo.showOpenDialog(null);
 
        if (selecionado == JFileChooser.APPROVE_OPTION) {
            File salvarArquivo = arquivo.getSelectedFile();
            try{
                FileInputStream FIn = new FileInputStream(salvarArquivo.getAbsolutePath());
                BufferedReader ArquivoLer = new BufferedReader(new InputStreamReader(FIn));
                tabuleiroAux =  MontaTabuleiro(ArquivoLer);
                Iniciar.Carregar(tabuleiroAux);
                FIn.close();
               
            }
            catch(Exception e){
              e.printStackTrace();
            }
            System.out.println("Save as file: " + salvarArquivo.getAbsolutePath());
           
        }
       

    }
    
    private static void EscreveTabuleiro(Tabuleiro tabuleiro, PrintStream file) {
    	Pecas p;
    	for (int i = 0; i<8 ;i++) {
    		for (int j=0; j<8 ; j++) {
    			p = tabuleiro.LocalizaPeca(i, j);
    			if (p != null) {
    				
    				try {
    					file.print(VerificaPeca(p)); 
    					file.println();
    					file.print(p.getLin());
    					file.println();
    					file.print(p.getCol());
    					file.println();
    					
    				}catch(Exception e){
    		              e.printStackTrace();
    		              
    	            }
    			}
    		}
    		
    	}
    	
    	
    }
    
    private static String VerificaPeca(Pecas p) {
    	
    	if(p.getTipo() == TipoPeca.Torre) {
    		if(p.getColor() == 1)
    			return "TorreBranca";
    		else
    			return "TorrePreta";
			
		}
    	else if(p.getTipo() == TipoPeca.Cavalo) {
    		if(p.getColor() == 1)
    			return "CavaloBranco";
    		else
    			return "CavaloPreto";
			
		}
		else if(p.getTipo() == TipoPeca.Bispo) {
			if(p.getColor() == 1)
    			return "BispoBranco";
    		else
    			return "BispoPreto";
			
		}
		else if(p.getTipo() == TipoPeca.Rei) {
			if(p.getColor() == 1)
    			return "ReiBranco";
    		else
    			return "ReiPreto";
			
		}
		else if(p.getTipo() == TipoPeca.Rainha) {
			if(p.getColor() == 1)
    			return "RainhaBranca";
    		else
    			return "RainhaPreta";
			
		}
		else {
			if(p.getColor() == 1)
    			return "PeaoBranco";
    		else
    			return "PeaoPreto";
			
		}
    }
    
    private static Tabuleiro MontaTabuleiro(BufferedReader b) {
    	
    	Tabuleiro tabuleiro = new Tabuleiro();
    	Pecas p;
    	String proximaLinha;
    	String s ;
    	TipoPeca tipo;
    	int lin,col;
    	try {
	    	while ((proximaLinha = b.readLine()) != null) {
	    		s = (String)proximaLinha;
	    		lin =  Integer.parseInt(b.readLine());
	    		col = Integer.parseInt(b.readLine());
	    		tipo = VerificaPeca(s);
	    		if ( tipo != null) {
		    		if (cor != 3 ) {
		    			p = tabuleiro.CriaPeca(lin, col, tipo, cor);
		    			cor = 3;
		    			tabuleiro.addPeca(p);
		    		}
	    		}    
	        }
	    	return tabuleiro;
	    	
    	}catch(Exception e){
            e.printStackTrace();
            return null;
          }
    	
    }
    
    private static TipoPeca VerificaPeca ( String s) {
    	System.out.println("VERIFICA S   " + s );
    	if( Objects.equals(s,"TorreBranca") ){
    		cor = 1;
    		return TipoPeca.Torre;
    	}
    	else if ( Objects.equals(s, "TorrePreta" )){
    		cor = 0;
    		return TipoPeca.Torre;
    	}
    	else if ( Objects.equals(s, "PeaoBranco")) {
    		cor = 1;
    		return TipoPeca.Peao;
    		
    	}
    	else if ( Objects.equals(s,"PeaoPreto")) {
    		cor = 0;
    		return TipoPeca.Peao;
    	}
    	else if ( Objects.equals(s,"CavaloBranco")) {
    		cor = 1;
    		return TipoPeca.Cavalo;
    		
    	}
    	else if ( Objects.equals(s, "CavaloPreto")) {
    		cor = 0;
    		return TipoPeca.Cavalo;
    		
    	}
    	else if ( Objects.equals(s,"BispoBranco")) {
    		cor = 1;
    		return TipoPeca.Bispo;
    	}
    	else if ( Objects.equals(s,"BispoPreto")) {
    		cor = 0;
    		return TipoPeca.Bispo;
    	}
    	else if ( Objects.equals(s, "RainhaBranca" )) {
    		cor = 1;
    		return TipoPeca.Rainha;
    		
    	}
    	else if ( Objects.equals(s, "RainhaPreta")) {
    		cor = 0;
    		return TipoPeca.Rainha;
    	}
    	else if ( Objects.equals(s, "ReiBranco")) {
    		cor = 1;
    		return TipoPeca.Rei;
    	}
    	// rei preto
    	else {
    		if (  Objects.equals(s,"ReiPreto")) {
	    		cor = 0;
	    		return TipoPeca.Rei;
    		}else
    			return null;
    		}
    	}
}
