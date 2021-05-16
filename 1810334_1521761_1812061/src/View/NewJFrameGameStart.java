package View;

import java.awt.*;
import javax.swing.*;

public class NewJFrameGameStart extends JFrame 
{ 
	NewJPanelGameStart p = new NewJPanelGameStart();
	
	public NewJFrameGameStart()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width/2 - 500, screenSize.height/2 - 350);
		p.setBorder(null);
		getContentPane().add(p);
		
	}
	
	public NewJPanelGameStart GetP()
	{
		return p;
	}
	
}