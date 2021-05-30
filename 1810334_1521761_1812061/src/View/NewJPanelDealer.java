package View;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Jogador;

import Model.GameManager;

public class NewJPanelDealer extends JPanel {

	private Image imgBkg;
	private Image imgCoin1;
	private Image imgCoin5;
	private Image imgCoin10;
	private Image imgCoin20;
	private Image imgCoin50;
	private Image imgCoin100;
	Jogador player;
	private JButton JButtonQuit = new JButton("Quit");
	private JButton JButtonClear = new JButton("Clear");
	private JButton JButtonSave = new JButton("Save");
	private JLabel JLabelPrizePool = new JLabel("Aposta total = 0");
	GameManager GameManager;
	
	public NewJPanelDealer(GameManager gameManager) 
	{
		GameManager = gameManager;
		
		imgBkg = readImage("Resources/blackjackBKG.png");
		imgCoin1 = readImage("Resources/ficha 1$.png");
		imgCoin5 = readImage("Resources/ficha 5$.png");
		imgCoin10 = readImage("Resources/ficha 10$.png");
		imgCoin20 = readImage("Resources/ficha 20$.png");
		imgCoin50 = readImage("Resources/ficha 50$.png");
		imgCoin100 = readImage("Resources/ficha 100$.png");
		this.setLayout(null);
		
		JButtonQuit.addActionListener(e -> ButtonClickQuit());
		JButtonClear.addActionListener(e -> ButtonClickClear());
		JButtonSave.addActionListener(e -> ButtonClickSave());
		
		ArrayList<JButton> JButtonList = new ArrayList<JButton>();
		JButtonList.add(JButtonQuit);
		JButtonList.add(JButtonClear);
		JButtonList.add(JButtonSave);
		
		for (int i = 0; i < JButtonList.size(); i++) {
			JButtonList.get(i).setVisible(true);
			JButtonList.get(i).setBounds(110+(i*130), 550, 100, 50);
			this.add(JButtonList.get(i));
		}
	
		this.add(JLabelPrizePool);
		JLabelPrizePool.setVisible(true);
		JLabelPrizePool.setBounds(870, 500, 100, 50);
		
	}
	
	private void ButtonClickSave() 
	{
		//Prox Iteracao
	}
	
	private void ButtonClickClear() 
	{
		GameManager.Clear();		
	}
	
	private void ButtonClickQuit() 
	{
		GameManager.Quit();
	}
	
	public void paintComponent(Graphics G)
	{
		super.paintComponent(G);
		G.drawImage(this.imgBkg, 0, 0, 1000, 650, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin1, 900, 600, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin5, 800, 600, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin10, 700, 600, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin20, 600, 600, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin50, 500, 600, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin100, 400, 600, 50, 50, getFocusCycleRootAncestor());
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

}
