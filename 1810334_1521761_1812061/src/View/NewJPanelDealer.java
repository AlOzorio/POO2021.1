package View;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Jogador;
import Model.Dealer;
import Model.GameManager;

public class NewJPanelDealer extends JPanel {

	private Image imgBkg;
	private Image deck;
	private Image back;
	private Image imgCoin1;
	private Image imgCoin5;
	private Image imgCoin10;
	private Image imgCoin20;
	private Image imgCoin50;
	private Image imgCoin100;
	Dealer dealer;
	private JButton JButtonQuit = new JButton("Quit");
	private JButton JButtonClear = new JButton("Clear");
	private JButton JButtonSave = new JButton("Save");
	private JLabel JLabelPrizePool = new JLabel("Aposta total = 0");
	private JLabel JLabelSum = new JLabel("Soma das cartas = 0");
	GameManager GameManager;
	private Boolean isHidden;
	
	public NewJPanelDealer(GameManager gameManager) 
	{
		GameManager = gameManager;
		isHidden = false;
		imgBkg = readImage("Resources/blackjackBKG.png");
		deck = readImage("Resources/deck1.gif");
		back = readImage("Resources/deck2.gif");
		imgCoin1 = readImage("Resources/ficha 1$.png");
		imgCoin5 = readImage("Resources/ficha 5$.png");
		imgCoin10 = readImage("Resources/ficha 10$.png");
		imgCoin20 = readImage("Resources/ficha 20$.png");
		imgCoin50 = readImage("Resources/ficha 50$.png");
		imgCoin100 = readImage("Resources/ficha 100$.png");
		this.setLayout(null);
		
		addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					mouseClick(e.getX(), e.getY());
				}
			}
		);
		
		JButtonQuit.addActionListener(e -> ButtonClickQuit());
		JButtonClear.addActionListener(e -> ButtonClickClear());
		JButtonSave.addActionListener(e -> ButtonClickSave());
		
		ArrayList<JButton> JButtonList = new ArrayList<JButton>();
		JButtonList.add(JButtonQuit);
		JButtonList.add(JButtonClear);
		JButtonList.add(JButtonSave);
		
		for (int i = 0; i < JButtonList.size(); i++) {
			JButtonList.get(i).setVisible(true);
			JButtonList.get(i).setBounds(310+(i*130), 550, 100, 50);
			this.add(JButtonList.get(i));
		}
	
		this.add(JLabelPrizePool);
		JLabelPrizePool.setVisible(true);
		JLabelPrizePool.setBounds(850, 500, 120, 50);
		this.add(JLabelSum);
		JLabelSum.setVisible(true);
		JLabelSum.setBounds(825, 475, 150, 50);
		
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
		//drawImage(img,xpos,ypos,width,height)
		G.drawImage(this.imgBkg, 0, 0, 1000, 650, getFocusCycleRootAncestor());
		G.drawImage(this.deck, 40, 200, 73, 97, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin1, 725, 350, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin5, 625, 350, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin10, 525, 350, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin20, 425, 350, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin50, 325, 350, 50, 50, getFocusCycleRootAncestor());
		G.drawImage(this.imgCoin100, 225, 350, 50, 50, getFocusCycleRootAncestor());
		
		for (int i = 0; i < dealer.getMao(0).getCartas().size(); i++)
		{
			G.drawImage(readImage("Resources/" + dealer.getMao(0).getCartas().get(i).GetIndex() + ".gif"), i*120 + 300, 110, 73, 97, getFocusCycleRootAncestor());				
		}
		JLabelSum.setText("Soma das cartas = " + String.valueOf(dealer.getPontos(0)));
		
		if(isHidden == true)
		{
			G.drawImage(this.back, 300, 110, 73, 97, getFocusCycleRootAncestor());
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
	
	public void setDealer(Dealer dealer) {
		// TODO Auto-generated method stub
		this.dealer = dealer;
	}
	
	private void mouseClick(int x, int y)
	{
		if ((x >= 725 && x <= 725 + 50) && (y >= 350 && y <= 350 + 50)) 
		{
			GameManager.AddToPrizePool(1);
		}
		if ((x >= 625 && x <= 625 + 50) && (y >= 350 && y <= 350 + 50)) 
		{
			GameManager.AddToPrizePool(5);
		}
		if ((x >= 525 && x <= 525 + 50) && (y >= 350 && y <= 350 + 50)) 
		{
			GameManager.AddToPrizePool(10);
		}
		if ((x >= 425 && x <= 425 + 50) && (y >= 350 && y <= 350 + 50)) 
		{
			GameManager.AddToPrizePool(20);
		}
		if ((x >= 325 && x <= 325 + 50) && (y >= 350 && y <= 350 + 50)) 
		{
			GameManager.AddToPrizePool(50);
		}
		if ((x >= 225 && x <= 225 + 50) && (y >= 350 && y <= 350 + 50)) 
		{
			GameManager.AddToPrizePool(100);
		}

		JLabelPrizePool.setText("Aposta total = " + String.valueOf(GameManager.prizePool));
		GameManager.jogadoresInterface.get(GameManager.turn).getInterface(0).p.JLabelBet.setText("Aposta = " + String.valueOf(GameManager.currentPlayer.getTotalBet(0)));
		GameManager.jogadoresInterface.get(GameManager.turn).getInterface(0).p.JLabelCredits.setText("Creditos = " + String.valueOf(GameManager.currentPlayer.getCreditos()));
	}

	public Boolean getIsHidden()
	{
		return isHidden;
	}

	public void setIsHidden(Boolean value)
	{
		isHidden = value;
	}
	
	public void showHidenCard() 
	{
		if(isHidden == true)
		{
			this.setIsHidden(false);
			dealer.setPontos(dealer.getPontos(0) + dealer.getMao(0).getCartas().get(0).GetValue(), 0);
			repaint();
		}
	}
	
	public void hideHidenCard() 
	{
		if(isHidden == false)
		{
			this.setIsHidden(true);
			repaint();
		}
	}
	
	public JLabel getPrizePoolLabel()
	{
		return JLabelPrizePool;
	}

}
