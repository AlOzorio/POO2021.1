package View;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Carta;
import Model.Jogador;

public class NewJPanelPlayer extends JPanel 
{
	private Image imgBkg;
	Jogador player;
	private JButton JButtonDeal = new JButton("Deal");
	private JButton JButtonHit = new JButton("Hit");
	private JButton JButtonSplit = new JButton("Split");
	private JButton JButtonSurrender = new JButton("Surrender");
	private JButton JButtonStand = new JButton("Stand");
	private JButton JButtonDouble = new JButton("Double");
	private JLabel JLabelBet = new JLabel("Aposta = 0");
	
	public NewJPanelPlayer() 
	{
		imgBkg = readImage("Resources/blackjackBKG.png");
		this.setLayout(null);
		
		ArrayList<JButton> JButtonList = new ArrayList<JButton>();
		JButtonList.add(JButtonDeal);
		JButtonList.add(JButtonHit);
		JButtonList.add(JButtonSplit);
		JButtonList.add(JButtonSurrender);
		JButtonList.add(JButtonStand);
		JButtonList.add(JButtonDouble);
		
		for (int i = 0; i < JButtonList.size(); i++) {
			JButtonList.get(i).setVisible(true);
			JButtonList.get(i).setBounds(122+(i*130), 550, 80, 50);
			this.add(JButtonList.get(i));
		}
		
		this.add(JLabelBet);
		JLabelBet.setVisible(true);
		JLabelBet.setBounds(870, 450, 100, 50);
	}
	
	public void paintComponent(Graphics G)
	{
		super.paintComponent(G);
		G.drawImage(this.imgBkg, 0, 0, 1000, 650, getFocusCycleRootAncestor());
		for (int i = 0; i < player.getMao().size(); i++)
		{
			//G.drawImage(player.getMao().get(i).getSprite(), 0, 0, 1000, 650, getFocusCycleRootAncestor());	
		}
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

	public void setPlayer(Jogador player) {
		// TODO Auto-generated method stub
		this.player = player;
	}
	

}
