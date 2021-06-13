package View;

import java.util.ArrayList;

import Model.GameManager;
import Model.Jogador;

public class Interfaces {
	public ArrayList<NewJFramePlayer> Interfaces = new ArrayList<NewJFramePlayer>();
	
	public Interfaces(Jogador player, int mao, GameManager gameManager)
	{
		NewJFramePlayer playerInterface = new NewJFramePlayer(player, mao, gameManager);
		Interfaces.add(playerInterface);
	}

	public NewJFramePlayer getInterface(int i) {
		// TODO Auto-generated method stub
		return Interfaces.get(i);
	}

	public void addNewMao(Jogador player, int mao, GameManager gameManager) 
	{
		NewJFramePlayer playerInterface = new NewJFramePlayer(player, mao, gameManager);
		Interfaces.add(playerInterface);
	}

	public void removeMao(int i) {
		Interfaces.remove(i);
		
	}
	
	public void repaintInterfaces()
	{
		for (int i = 0; i < Interfaces.size(); i++) 
		{
			Interfaces.get(i).repaint();
		}
	}
}
