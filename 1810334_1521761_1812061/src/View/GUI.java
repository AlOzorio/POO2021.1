package View;

import java.util.ArrayList;

import javax.swing.*;

public class GUI {
	static ArrayList<JFrame> WindowsList;
		
	public GUI() {}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Main principal 
		NewJFrameGameStart janela = new NewJFrameGameStart();
		janela.setVisible(true);
		
	}
}



