package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class NewJPanelGameStart extends JPanel {

	private JButton Player1 = new JButton("1 Jogador");
	private JButton Player2 = new JButton("2 Jogadores");
	private JButton Player3 = new JButton("3 Jogadores");
	private JButton Player4 = new JButton("4 Jogadores");
	
	public int numero = 0;
	
	ArrayList<JButton> JButtonList = new ArrayList<JButton>();
	
	public NewJPanelGameStart() 
	{
		Player1.addActionListener(e -> ButtonClick(1));
		Player2.addActionListener(e -> ButtonClick(2));
		Player3.addActionListener(e -> ButtonClick(3));
		Player4.addActionListener(e -> ButtonClick(4));
		
		JButtonList.add(this.Player1);
		JButtonList.add(this.Player2);
		JButtonList.add(this.Player3);
		JButtonList.add(this.Player4);
		
		for (int i = 0; i < 4; i++) {
			this.add(JButtonList.get(i));
			JButtonList.get(i).setVisible(true);
			JButtonList.get(i).setBounds(350, 150*i+50, 300, 100);
		}
		this.setLayout(null);
		
	}
	
	private void ButtonClick(int i) {
		numero = i;
		javax.swing.SwingUtilities.getWindowAncestor(NewJPanelGameStart.this).setVisible(false);
		createGame();
	}

	public int getNumber()
	{
		return this.numero;
	}
	
	public void createGame()
	{
		openWindows(this.numero);
	}
	public static ArrayList<JFrame> openWindows(int number)
	{
		ArrayList<JFrame> WindowsListLocal = new ArrayList<JFrame>();
		for (int i = 0; i < number; i++) {
			JFrame window = new NewJFramePlayer();
			window.setTitle("Minha primeira GUI");
			window.setVisible(true);
			WindowsListLocal.add(window);
		}
		return WindowsListLocal;
	}

}
