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
	public static ArrayList<JFrame> openWindows(int number)
	{
		ArrayList<JFrame> WindowsListLocal = new ArrayList<JFrame>();
		for (int i = 0; i < number; i++) {
			JFrame window = new NewJFramePlayer();
			window.setTitle("Minha primeira GUI");
			window.setVisible(true);
			WindowsListLocal.add(window);
		}
		return WindowsListLocal;
	}
}



