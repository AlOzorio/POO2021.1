package View;

import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Carta;
import Model.Jogador;

public class NewJPanelPlayer extends JPanel 
{
	private Image imgBkg;
	Jogador player = new Jogador();
	int aux = 0;
	
	public NewJPanelPlayer() 
	{
		imgBkg = readImage("Resources/blackjack.png");
		this.setLayout(null);
		player.criarFichas();
		
	}
	
	public void paintComponent(Graphics G)
	{
		super.paintComponent(G);
		G.drawImage(this.imgBkg, 0, 0, 1000, 650, getFocusCycleRootAncestor());
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
	public void addCard(Carta carta) 
	{
		this.player.addCarta(carta);
	}
	
	

}
