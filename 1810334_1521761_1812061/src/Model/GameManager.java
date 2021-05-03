package Model;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class GameManager 
{
	private Scanner s = new Scanner(System.in);
	private static ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Ficha> prizePool = new ArrayList<Ficha>();
	private Deck deck = new Deck();
	private Jogador currentPlayer;
	private int turn = 0;
	private Jogador winner;
	
	public void NewGame()
	{
		String nome;
		Ficha FichasPilha;
		
		
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

		// Reseta o prizepool
		FichasPilha = new Ficha(1,0);
		prizePool.add(FichasPilha);
		FichasPilha = new Ficha(5,0);
		prizePool.add(FichasPilha);
		FichasPilha = new Ficha(10,0);
		prizePool.add(FichasPilha);
		FichasPilha = new Ficha(20,0);
		prizePool.add(FichasPilha);
		FichasPilha = new Ficha(50,0);
		prizePool.add(FichasPilha);
		FichasPilha = new Ficha(100,0);
		
		currentPlayer = jogadores.get(turn);
		deck.IniciaBaralho();
		deck.Embaralhar();
	}
	public void AddToPrizePool(int value)
	{
		if (currentPlayer.getTotalBet() + value < 100)
		{
			currentPlayer.setTotalBet(currentPlayer.getTotalBet() + value);
			
			if(value == 1)
			{
				prizePool.get(0).setQuantidade(prizePool.get(0).getQuantidade() + 1);
			}
			else if (value == 5) 
			{
				prizePool.get(1).setQuantidade(prizePool.get(1).getQuantidade() + 1);
			}
			else if (value == 10) 
			{
				prizePool.get(2).setQuantidade(prizePool.get(2).getQuantidade() + 1);
			}
			else if (value == 20) 
			{
				prizePool.get(3).setQuantidade(prizePool.get(3).getQuantidade() + 1);
			}
			else if (value == 50) 
			{
				prizePool.get(4).setQuantidade(prizePool.get(4).getQuantidade() + 1);
			}
			else if (value == 100) 
			{
				prizePool.get(5).setQuantidade(prizePool.get(5).getQuantidade() + 1);
			}
		}
		
		if(currentPlayer.getTotalBet() + value >= 20 && currentPlayer.getDealt() == false)
		{
			//habilita botao de deal
		}
		else 
		{
			//desabilita botao de deal
		}
	}

	public void Deal()
	{
		currentPlayer.addCarta(deck.Draw());
		currentPlayer.addCarta(deck.Draw());
		currentPlayer.setDealt(true);
		if (turn == jogadores.size() - 1) 
		{
			turn = 0;
		}
		else 
		{
			turn++;
		}
		currentPlayer = jogadores.get(turn); 
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
		
		if(currentPlayer.getPontos() > 21)
		{
			Stand();
		}
	}
	
	public void Stand()
	{
		if(turn == jogadores.size() - 1)
		{
			winner = jogadores.get(0);
			
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
		Ficha FichasPilha;
		int betToReach = currentPlayer.getTotalBet();
		
		currentPlayer.addCarta(deck.Draw());
		ArrayList<Ficha> newPool = new ArrayList<Ficha>();
		
		FichasPilha = new Ficha(1,0);
		newPool.add(FichasPilha);
		FichasPilha = new Ficha(5,0);
		newPool.add(FichasPilha);
		FichasPilha = new Ficha(10,0);
		newPool.add(FichasPilha);
		FichasPilha = new Ficha(20,0);
		newPool.add(FichasPilha);
		FichasPilha = new Ficha(50,0);
		newPool.add(FichasPilha);
		FichasPilha = new Ficha(100,0);
		
		
		if(betToReach == 100)
		{
			if (currentPlayer.getFicha().get(5).getQuantidade() > 0) 
			{
				currentPlayer.getFicha().get(5).setQuantidade(currentPlayer.getFicha().get(5).getQuantidade() - 1);
				betToReach -= 100;
				newPool.get(5).setQuantidade(newPool.get(5).getQuantidade() + 1);
			}
		}
		if(betToReach >= 50)
		{
			while(betToReach >= 50)
			{
				if (currentPlayer.getFicha().get(4).getQuantidade() > 0) 
				{
					currentPlayer.getFicha().get(4).setQuantidade(currentPlayer.getFicha().get(4).getQuantidade() - 1);
					betToReach -= 50;
					newPool.get(4).setQuantidade(newPool.get(4).getQuantidade() + 1);
				}
				
			}
		}
		if(betToReach >= 20)
		{
			while(betToReach >= 20)
			{
				if (currentPlayer.getFicha().get(3).getQuantidade() > 0) 
				{
					currentPlayer.getFicha().get(3).setQuantidade(currentPlayer.getFicha().get(3).getQuantidade() - 1);
					betToReach -= 20;
					newPool.get(3).setQuantidade(newPool.get(3).getQuantidade() + 1);
				}
				
			}
		}
		if(betToReach >= 10)
		{
			while(betToReach >= 10)
			{
				if (currentPlayer.getFicha().get(2).getQuantidade() > 0) 
				{
					currentPlayer.getFicha().get(2).setQuantidade(currentPlayer.getFicha().get(2).getQuantidade() - 1);
					betToReach -= 10;
					newPool.get(2).setQuantidade(newPool.get(2).getQuantidade() + 1);
				}
				
			}
		}
		if(betToReach >= 5)
		{
			while(betToReach >= 5)
			{
				if (currentPlayer.getFicha().get(1).getQuantidade() > 0) 
				{
					currentPlayer.getFicha().get(1).setQuantidade(currentPlayer.getFicha().get(1).getQuantidade() - 1);
					betToReach -= 5;
					newPool.get(1).setQuantidade(newPool.get(1).getQuantidade() + 1);
				}
				
			}
		}
		if(betToReach >= 1)
		{
			while(betToReach >= 1)
			{
				if (currentPlayer.getFicha().get(0).getQuantidade() > 0) 
				{
					currentPlayer.getFicha().get(0).setQuantidade(currentPlayer.getFicha().get(0).getQuantidade() - 1);
					betToReach -= 1;
					newPool.get(0).setQuantidade(newPool.get(0).getQuantidade() + 1);
				}
				
			}
		}
		
		if (betToReach == 0)
		{
			for (int i = 0; i < newPool.size(); i++)
			{
				prizePool.get(i).setQuantidade(prizePool.get(i).getQuantidade() + newPool.get(i).getQuantidade());
			}
		}
		else 
		{
			// Não pode dobrar a aposta, devolver as fichas ao jogador
			for (int i = 0; i < newPool.size(); i++)
			{
				currentPlayer.getFicha().get(i).setQuantidade(currentPlayer.getFicha().get(i).getQuantidade() + newPool.get(i).getQuantidade());
			}
		}
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
		if (currentPlayer.getMao().size() > 0)
		{
			for (int i = 0; i < currentPlayer.getMao().size() - 1; i++) 
			{
				Carta c = currentPlayer.getMao().get(i);
				deck.AddCard(c);
			}
		}
		jogadores.remove(turn);
		deck.Embaralhar();
	}
	
	public void RewardWinner()
	{	
		// Recompompensa pela vitoria ordinaria
		for (int i = 0; i < 6; i++)
		{
			this.winner.getFicha().get(i).setQuantidade(this.winner.getFicha().get(i).getQuantidade() + prizePool.get(i).getQuantidade());
		}
	}
	
}
