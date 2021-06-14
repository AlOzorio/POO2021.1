// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package View;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import Model.Carta;
import Controller.GameManager;
import Model.Jogador;

public class NewJFramePlayer extends JFrame 
{ 
	public NewJPanelPlayer p;
	public GameManager GameManager;
	
	// Inicializa os JFrames referente �s telas dos jogadores
	public NewJFramePlayer(Jogador player, int mao, GameManager gameManager)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000, 690);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLocation(screenSize.width/2 - 500, screenSize.height/2 - 350);
		this.GameManager = gameManager;
		p = new NewJPanelPlayer(this.GameManager, mao);
		//p.add(new NewJPanelPlayer(this.GameManager));
		p.setBorder(null);
		p.setPlayer(player);
		getContentPane().add(p);
	}

}