package View;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.GameManager;
import Model.Dealer;

public class NewJFrameDealer extends JFrame {

	public GameManager GameManager;
	NewJPanelDealer p;
	
	// Inicializa o JFrame referente ao delar
	public NewJFrameDealer(Dealer dealer, GameManager gameManager)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(1000, 690);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width/2 - 500, screenSize.height/2 - 350);
		this.GameManager = gameManager;
		p = new NewJPanelDealer(this.GameManager);
		p.setBorder(null);
		p.setDealer(dealer);
		getContentPane().add(p);
		
	}

	// Wrappers para manipular a carta virada para baixo do dealer
	public void showHiddenCard() 
	{
		p.showHidenCard();	
	}
	
	public void hideHiddenCard() 
	{
		p.hideHidenCard();	
	}
	
	public void setIsHiiden(Boolean value) 
	{
		p.setIsHidden(value);	
	}
	
	public Boolean getIsHidden() 
	{
		return p.getIsHidden();	
	}
	
	// Métodos para obter e manipular os JLabels da tela do dealer
	public void resetPrizePoolLabel()
	{
		p.getPrizePoolLabel().setText("Aposta total = " + String.valueOf(this.GameManager.prizePool));
	}
	
	public JLabel getPrizePoolLabel()
	{
		return p.getPrizePoolLabel();
	}
	
	public JLabel getSumLabel()
	{
		return p.getSumLabel();
	}
}
