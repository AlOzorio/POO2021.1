// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package View;

import java.awt.*;
import javax.swing.*;

public class NewJFrameGameStart extends JFrame 
{ 
	NewJPanelGameStart p = new NewJPanelGameStart();
	
	// Inicializa o JFrame referente a tela inicial
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
	
	// Obtem o JPanel referente a tela inicial
	public NewJPanelGameStart GetP()
	{
		return p;
	}
	
}