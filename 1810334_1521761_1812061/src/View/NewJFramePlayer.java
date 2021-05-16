package View;

import java.awt.*;
import javax.swing.*;

import Model.Jogador;

public class NewJFramePlayer extends JFrame 
{ 
	NewJPanelPlayer p = new NewJPanelPlayer();
	
	public NewJFramePlayer(Jogador player)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000, 690);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width/2 - 500, screenSize.height/2 - 350);
		p.setBorder(null);
		p.setPlayer(player);
		getContentPane().add(p);
		
	}
	
	
}