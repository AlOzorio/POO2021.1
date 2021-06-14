// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
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

import Controller.GameManager;
import Model.Jogador;
import Model.Dealer;

public class NewJPanelDealer extends JPanel {

	// Imagens referentes aos elementos do jogo, como fichas e versos de carta
	private Image imgBkg;
	private Image deck;
	private Image back;
	private Image imgCoin1;
	private Image imgCoin5;
	private Image imgCoin10;
	private Image imgCoin20;
	private Image imgCoin50;
	private Image imgCoin100;
	// Objeto dealer para possuir acesso as suas funcoes
	Dealer dealer;
	// Botoes e labels da tela do dealer 
	private JButton JButtonQuit = new JButton("Quit");
	private JButton JButtonClear = new JButton("Clear");
	private JButton JButtonSave = new JButton("Save");
	private JLabel JLabelPrizePool = new JLabel("Aposta total = 0");
	private JLabel JLabelSum = new JLabel("Soma das cartas = 0");
	// Referencia ao GameManager
	GameManager GameManager;
	// Booleana que indica se a carta esta de cabeca para baixo
	private Boolean isHidden;
	
	// Inicializa o pinel do dealer
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
		
		// Acrescenta o mouse listener a janela
		addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					mouseClick(e.getX(), e.getY());
				}
			}
		);
		
		// Adiciona as funcoes dos botoes
		JButtonQuit.addActionListener(e -> ButtonClickQuit());
		JButtonClear.addActionListener(e -> ButtonClickClear());
		JButtonSave.addActionListener(e -> ButtonClickSave());
		
		// Define a posicao dos botoes e labels na janela
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
	
	// Funcoes para os botoes do dealer
	private void ButtonClickSave()
	{		
		try 
		{
			GameManager.SaveGame();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void ButtonClickClear() 
	{
		GameManager.Clear();		
	}
	
	private void ButtonClickQuit() 
	{
		GameManager.Quit();
	}
	
	// Desenha os elementos da tela do dealer
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
	
	// Obtem a imagem das cartas 
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
	
	// Inicializa o dealer
	public void setDealer(Dealer dealer) {
		// TODO Auto-generated method stub
		this.dealer = dealer;
	}
	
	// Reconhece os cliques em cima das fichas para a realizao de apostas 
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
		
		// Atualiza os labels da tela do dealer e dos jogadores
		JLabelPrizePool.setText("Aposta total = " + String.valueOf(GameManager.prizePool));
		GameManager.jogadoresInterface.get(GameManager.turn).getInterface(0).p.JLabelBet.setText("Aposta = " + String.valueOf(GameManager.currentPlayer.getTotalBet(0)));
		GameManager.jogadoresInterface.get(GameManager.turn).getInterface(0).p.JLabelCredits.setText("Creditos = " + String.valueOf(GameManager.currentPlayer.getCreditos()));
	}

	// Funcoes para manipular a carta virada para baixo
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
	
	// Funcoes para manipular os labels da tela do dealer
	public JLabel getPrizePoolLabel()
	{
		return JLabelPrizePool;
	}
	
	public JLabel getSumLabel()
	{
		return JLabelSum;
	}

}
