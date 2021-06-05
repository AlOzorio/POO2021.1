package View;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Model.GameManager;
import Model.Jogador;

public class NewJFramePlayer extends JFrame 
{ 
	public NewJPanelPlayer p;
	public GameManager GameManager;
	
	public NewJFramePlayer(Jogador player, GameManager gameManager)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000, 690);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLocation(screenSize.width/2 - 500, screenSize.height/2 - 350);
		this.GameManager = gameManager;
		p = new NewJPanelPlayer(this.GameManager);
		p.setBorder(null);
		p.setPlayer(player);
		getContentPane().add(p);
		
	}
	
}