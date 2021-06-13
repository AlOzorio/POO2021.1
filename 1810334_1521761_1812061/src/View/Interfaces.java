package View;

import java.util.ArrayList;

import Model.GameManager;
import Model.Jogador;

public class Interfaces {
	// Lista da interface de cada mao do jogador. Cada jogador tem um objeto Interfaces, com uma lista InterfacesLista.
	public ArrayList<NewJFramePlayer> InterfacesLista = new ArrayList<NewJFramePlayer>();
	
	public Interfaces(Jogador player, int mao, GameManager gameManager)
	{
		// Construtor da classe, garante que a lista tenha pelo menos uma mão
		NewJFramePlayer playerInterface = new NewJFramePlayer(player, mao, gameManager);
		InterfacesLista.add(playerInterface);
	}

	public NewJFramePlayer getInterface(int i) {
		// Retorna a interface da mao desejada, indicada pelo int recebido
		return InterfacesLista.get(i);
	}

	public void addNewMao(Jogador player, int mao, GameManager gameManager) 
	{
		// Cria uma nova mao para o jogador
		NewJFramePlayer playerInterface = new NewJFramePlayer(player, mao, gameManager);
		InterfacesLista.add(playerInterface);
	}

	public void removeMao(int i) 
	{
		// Retira a mao desejada do jogador, indicada pelo int recebido
		InterfacesLista.remove(i);
	}
	
	public void repaintInterfaces()
	{
		// Atualiza graficamente todas as interfaces das maos do jogador
		for (int i = 0; i < InterfacesLista.size(); i++) 
		{
			InterfacesLista.get(i).repaint();
		}
	}
}
