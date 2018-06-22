package view;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ControladorTabuleiro;
import model.Tabuleiro;
public class SalvarCarregar {
	
static Tabuleiro tabuleiro;

	
public static boolean Salvar(){
        
        JFileChooser arquivo = new JFileChooser();
        arquivo.setDialogTitle("Save As");   
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Saved Games","sav");
        arquivo.setFileFilter(filtro);
 
        int selecionado = arquivo.showSaveDialog(null);
 
        if (selecionado == JFileChooser.APPROVE_OPTION) {
            File salvarArquivo = arquivo.getSelectedFile();
            try{
                FileOutputStream fos = new FileOutputStream(salvarArquivo.getAbsolutePath()+".sav");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                
                tabuleiro = new Tabuleiro();
                tabuleiro.TabuleiroCarregar(ControladorTabuleiro.tabuleiro);
                oos.writeObject(tabuleiro);
                oos.close();
                fos.close();
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
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Saved Games","sav");
        arquivo.setFileFilter(filtro);
 
        int selecionado = arquivo.showOpenDialog(null);
 
        if (selecionado == JFileChooser.APPROVE_OPTION) {
            File salvarArquivo = arquivo.getSelectedFile();
            try{
                FileInputStream fis = new FileInputStream(salvarArquivo.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);

                Tabuleiro boardLoaded=(Tabuleiro)ois.readObject();
                Iniciar.Carregar(boardLoaded);// manda o tabuleiro carregado para a main
                ois.close();
                fis.close();
            }
            catch(Exception e){
              e.printStackTrace();
            }
            System.out.println("Save as file: " + salvarArquivo.getAbsolutePath());
           
        }
       

    }
    
}
