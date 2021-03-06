// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;

import Controller.GameManager;

public class NewJPanelGameStart extends JPanel {

	// Bot?es da tela inicial
	private JButton Player1 = new JButton("1 Jogador");
	private JButton Player2 = new JButton("2 Jogadores");
	private JButton Player3 = new JButton("3 Jogadores");
	private JButton Player4 = new JButton("4 Jogadores");
	private JButton CarregarJogo = new JButton("Carregar Jogo");
	
	public GameManager gm; // Refer?ncia ao GameManager
	
	public int numero = 0; // N?mero de jogadores
	
	ArrayList<JButton> JButtonList = new ArrayList<JButton>();
	
	public NewJPanelGameStart() 
	{
		gm = new GameManager();
		
		Player1.addActionListener(e -> ButtonClick(1));
		Player2.addActionListener(e -> ButtonClick(2));
		Player3.addActionListener(e -> ButtonClick(3));
		Player4.addActionListener(e -> ButtonClick(4));
		CarregarJogo.addActionListener(e -> LoadGame());
		
		JButtonList.add(this.Player1);
		JButtonList.add(this.Player2);
		JButtonList.add(this.Player3);
		JButtonList.add(this.Player4);
		JButtonList.add(this.CarregarJogo);
		
		// Define a posi??o dos botoes na janela
		for (int i = 0; i < 5; i++) {
			this.add(JButtonList.get(i));
			JButtonList.get(i).setVisible(true);
			JButtonList.get(i).setBounds(350, 110*i+50, 300, 100);
		}
		this.setLayout(null);
		
	}
	
	// Carrega um jogo salvo
	private void LoadGame() 
	{
		try
		{
			gm.LoadGame();
			javax.swing.SwingUtilities.getWindowAncestor(NewJPanelGameStart.this).setVisible(false);
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	// Fun??es para iniciar o jogo de acordo com o botao selecionado
	private void ButtonClick(int i) {
		this.numero = i;
		createGame();
		javax.swing.SwingUtilities.getWindowAncestor(NewJPanelGameStart.this).setVisible(false);
	}

	public int getNumber()
	{
		return this.numero;
	}
	
	public void createGame()
	{
		gm.NewGame(this.numero);
	}
}
