package Model;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import View.NewJPanelPlayer;

public class NewJFrameDealer extends JFrame {

	NewJPanelDealer p = new NewJPanelDealer();
	
	public NewJFrameDealer()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000, 690);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width/2 - 500, screenSize.height/2 - 350);
		p.setBorder(null);
		getContentPane().add(p);
		
	}
	
}
