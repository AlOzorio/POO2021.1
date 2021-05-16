package View;

import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class NewJPanelPlayer extends JPanel {
	private Image imgBkg;
	private Image card;
	private Image card2;
	private JButton Player1 = new JButton("1 Jogador");
	int aux = 0;
	
	public NewJPanelPlayer() {
		imgBkg = readImage("Resources/blackjack.png");
		this.card = readImage("Resources/4h.gif");
		this.card2 = readImage("Resources/9d.gif");
		
		Player1.setVisible(false);
		Player1.setBounds(0, 0, 100, 30);
		this.add(Player1);
		this.setLayout(null);
		
	}
	
	public void paintComponent(Graphics G)
	{
		super.paintComponent(G);
		G.drawImage(this.imgBkg, 0, 0, 1000, 650, getFocusCycleRootAncestor());
		G.drawImage(this.card, 400, 400, getFocusCycleRootAncestor());
		G.drawImage(this.card2, 500, 400, getFocusCycleRootAncestor());
	}
	
	private Image readImage(String ImgName)
	{
		try 
		{
			return ImageIO.read(new File(ImgName));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	private void newRepaint()
	{
		this.repaint();
	}

}
