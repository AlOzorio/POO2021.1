// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package View;

import java.util.ArrayList;

import javax.swing.*;

public class GUI {
	static ArrayList<JFrame> WindowsList;
		
	public GUI() {}
	
	// Abre a janela de início de jogo
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Main principal 
		NewJFrameGameStart janela = new NewJFrameGameStart();
		janela.setVisible(true);
		
	}
}



