package View;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Model.GameManager;

public class NewJFrameDealer extends JFrame {

	public GameManager GameManager;
	NewJPanelDealer p;
	
	public NewJFrameDealer(GameManager gameManager)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000, 690);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width/2 - 500, screenSize.height/2 - 350);
		this.GameManager = gameManager;
		p = new NewJPanelDealer(this.GameManager);
		p.setBorder(null);
		getContentPane().add(p);
		
	}
	
}
