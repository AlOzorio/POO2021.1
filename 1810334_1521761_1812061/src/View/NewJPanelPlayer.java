package View;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Carta;
import Model.GameManager;
import Model.Jogador;

public class NewJPanelPlayer extends JPanel 
{
	private Image imgBkg;
	private Image cardTest;
	Jogador player;
	public JButton JButtonDeal = new JButton("Deal");
	public JButton JButtonHit = new JButton("Hit");
	public JButton JButtonSplit = new JButton("Split");
	public JButton JButtonSurrender = new JButton("Surrender");
	public JButton JButtonStand = new JButton("Stand");
	public JButton JButtonDouble = new JButton("Double");
	public JLabel JLabelBet = new JLabel("Aposta = 0");
	public JLabel JLabelCredits = new JLabel("Creditos = 500");
	private JLabel JLabelSum = new JLabel("Soma das cartas = 0");
	GameManager GameManager;
	
	public NewJPanelPlayer(GameManager gameManager) 
	{
		imgBkg = readImage("Resources/blackjackBKG.png");
		cardTest = readImage("Resources/2c.gif");
		this.setLayout(null);
		
		GameManager = gameManager;

		JButtonDeal.addActionListener(e -> ButtonClickDeal());
		JButtonHit.addActionListener(e -> ButtonClickHit());
		JButtonSplit.addActionListener(e -> ButtonClickSplit());
		JButtonSurrender.addActionListener(e -> ButtonClickSurrender());
		JButtonStand.addActionListener(e -> ButtonClickStand());
		JButtonDouble.addActionListener(e -> ButtonClickDouble());
		
		ArrayList<JButton> JButtonList = new ArrayList<JButton>();
		JButtonList.add(JButtonDeal);
		JButtonList.add(JButtonHit);
		JButtonList.add(JButtonSplit);
		JButtonList.add(JButtonSurrender);
		JButtonList.add(JButtonStand);
		JButtonList.add(JButtonDouble);

		JButtonDeal.setEnabled(false);
		JButtonHit.setEnabled(false);
		JButtonSplit.setEnabled(false);
		JButtonSurrender.setEnabled(false);
		JButtonStand.setEnabled(false);
		JButtonDouble.setEnabled(false);
		
		for (int i = 0; i < JButtonList.size(); i++) {
			JButtonList.get(i).setVisible(true);
			JButtonList.get(i).setBounds(110+(i*130), 550, 100, 50);
			this.add(JButtonList.get(i));
		}

		this.add(JLabelBet);
		JLabelBet.setVisible(true);
		JLabelBet.setBounds(870, 500, 100, 50);
		this.add(JLabelCredits);
		JLabelCredits.setVisible(true);
		JLabelCredits.setBounds(870, 450, 100, 50);
		this.add(JLabelSum);
		JLabelSum.setVisible(true);
		JLabelSum.setBounds(820, 475, 150, 50);
		
	}
	
	private void ButtonClickDouble() {
		// TODO Auto-generated method stub
		if (this == GameManager.jogadoresInterface.get(GameManager.turn).p) {
			GameManager.Double();
			this.repaint();
			
		}
	}

	private void ButtonClickStand() {
		// TODO Auto-generated method stub
		if (this == GameManager.jogadoresInterface.get(GameManager.turn).p) 
		{
			if (GameManager.jogadores.get(GameManager.turn).getDealt() == true) 
			{
				GameManager.Stand();				
			}
			
		}
	}

	private void ButtonClickSurrender() {
		// TODO Auto-generated method stub
		if (this == GameManager.jogadoresInterface.get(GameManager.turn).p) {
			GameManager.Surrender();
			this.repaint();
			
		}
	}

	private void ButtonClickSplit() 
	{
		if (this == GameManager.jogadoresInterface.get(GameManager.turn).p) 
		{
			GameManager.Split();
			this.repaint();
		}
	}

	private void ButtonClickHit() {
		// TODO Auto-generated method stub
		if (this == GameManager.jogadoresInterface.get(GameManager.turn).p) {
			if (GameManager.jogadores.get(GameManager.turn).getDealt() == true) {
				GameManager.Hit();
				this.repaint();
			}
		}
	}

	private void ButtonClickDeal() {
		// TODO Auto-generated method stub
		if (this == GameManager.jogadoresInterface.get(GameManager.turn).p) {
			GameManager.Deal();
			this.JButtonHit.setEnabled(true);
			this.repaint();
			
		}
		
	}

	public void paintComponent(Graphics G)
	{
		super.paintComponent(G);
		G.drawImage(this.imgBkg, 0, 0, 1000, 650, getFocusCycleRootAncestor());
		for (int i = 0; i < player.getMao().size(); i++)
		{
			System.out.println(player.getMao().get(i).GetIndex());
			G.drawImage(readImage("Resources/" + player.getMao().get(i).GetIndex() + ".gif"), i*120 + 300, 400, 73, 97, getFocusCycleRootAncestor());	
		}
		JLabelSum.setText("Soma das cartas = " + String.valueOf(player.getPontos()));
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

	public void setPlayer(Jogador player) 
	{
		// TODO Auto-generated method stub
		this.player = player;
	}

}
