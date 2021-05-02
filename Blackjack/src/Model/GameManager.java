package Model;

import java.util.Scanner;
import java.util.ArrayList;

public class GameManager 
{
	private int numPlayers;
	private Scanner s = new Scanner(System.in);
	private static ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Ficha> prizePool = new ArrayList<Ficha>();
	private Deck deck = new Deck();
	private Jogador currentPlayer;
	private int turn = 0;
	
	public void SecondShuffle(Deck d)
	{
		int NumCartas = d.NumCartas();
		
		if(NumCartas <= 187)
		{
			d.Embaralhar();
		}
	}
	
	public void NewGame()
	{
		String nome;
		
		for(int i = 0; i < 4; i++)
		{
			System.out.println("Insira o nome do jogador:\n");
			nome = new String(s.next());
			
			if(i == 0 && nome.length() == 0)
			{
				while(nome.length() == 0)
				{
					System.out.println("Você precisa de ao menos um jogador para iniciar.\n");
					nome = new String(s.next());
				}
			}
			
			if(nome.length() == 0)
			{
				break;
			}
			
			Jogador player = new Jogador();
			player.setNome(nome);
			player.criarFichas();
			jogadores.add(player);
		}
		
		numPlayers = jogadores.size();
		currentPlayer = jogadores.get(turn);
		deck.IniciaBaralho();
		deck.Embaralhar();
	}
	
	public void Deal()
	{
		//prize pool
	}
	
	public void Hit()
	{
		for(int i = 0; i < jogadores.size() - 1; i++)
		{
			if(jogadores.get(i).getPontos() == 21)
			{
				//fim de rodada
			}
		}
		
		Carta topDeck = deck.Draw();
		currentPlayer.addCarta(topDeck);
		SecondShuffle(deck);
		
		if(currentPlayer.getPontos() > 21)
		{
			Stand();
		}
	}
	
	public void Stand()
	{
		if(turn == jogadores.size() - 1)
		{
			Jogador winner = jogadores.get(0);
			
			for(int i = 0; i < jogadores.size(); i++)
			{
				if(jogadores.get(i).getPontos() > winner.getPontos())
				{
					winner = jogadores.get(i);
				}
			}
			
			//Distribuir prize pool para o vencedor
		}
		else
		{
			turn++;
			currentPlayer = jogadores.get(turn); 
		}
	}
	
	public void Double()
	{
		
	}
	
	public void Clear()
	{
		for(int i = 0; i < jogadores.size() - 1; i++)
		{
			for(int j = 0; j < jogadores.get(i).getMao().size(); j++)
			{
				Carta c = jogadores.get(i).getMao().get(j);
				deck.AddCard(c);
			}
			
			jogadores.get(i).setMao(new ArrayList<Carta>());
		}
		
		deck.Embaralhar();
		turn = 0;
		currentPlayer = jogadores.get(turn);
	}
	
	
	public void Surrender()
	{
		
	}
	
	public void Quit()
	{
		
	}
}
