package Model;

import java.util.Scanner;

import javax.swing.JFrame;

import View.NewJFrameDealer;
import View.NewJFramePlayer;

import java.util.ArrayList;

public class GameManager 
{
	//Para realização dos testes, as variáveis abaixo foram postas como públicas
	public static ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	public static ArrayList<NewJFramePlayer> jogadoresInterface = new ArrayList<NewJFramePlayer>();
	public ArrayList<Ficha> prizePool = new ArrayList<Ficha>();
	public Deck deck = new Deck();
	public Jogador currentPlayer;
	public int turn = 0;
	public Jogador winner;
	
	public void NewGame(int playerCount)
	{
		Ficha FichasPilha;
		for (int i = 0; i < playerCount; i++) 
		{
			Jogador player = new Jogador();
			player.criarFichas();
			jogadores.add(player);
			NewJFramePlayer playerInterface = new NewJFramePlayer(player, this);
			playerInterface.setVisible(true);
			playerInterface.setTitle("Jogador " + String.valueOf(i+1));
			jogadoresInterface.add(playerInterface);
		}
		NewJFrameDealer windowDealer = new NewJFrameDealer(this);
		windowDealer.setTitle("Dealer");
		windowDealer.setVisible(true);
		
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
		System.out.println("jogo com " + playerCount + " jogadores!");
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
		if(jogadores.get(turn).getPontos() < 21 && currentPlayer != null)
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
		}
	}
	
	public void Stand()
	{
		if(turn == jogadores.size() - 1)
		{
			winner = jogadores.get(0);
			
			for(int i = 0; i < jogadores.size() - 1; i++)
			{
				if(jogadores.get(i).getPontos() > winner.getPontos())
				{
					winner = jogadores.get(i);
				}
			}
			
			currentPlayer = null;
			RewardWinner();
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
	
	private void RepaintAll()
	{	
		for(int i = 0; i < GameManager.jogadoresInterface.size(); i++)
		{
			GameManager.jogadoresInterface.get(i).repaint();	
		}
	}
	
	public void Clear()
	{
		for(int i = 0; i < jogadores.size(); i++)
		{
			for(int j = 0; j < jogadores.get(i).getMao().size(); j++)
			{
				Carta c = jogadores.get(i).getMao().get(j);
				deck.AddCard(c);
			}
			
			jogadores.get(i).setMao(new ArrayList<Carta>());
			jogadores.get(i).setDealt(false);
			jogadores.get(i).setPontos(0);
		}
		
		RepaintAll();
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
			jogadores.get(turn).setMao(new ArrayList<Carta>());			
		}
		
		jogadoresInterface.get(turn).setVisible(false);
		jogadores.remove(turn);
		jogadoresInterface.remove(turn);
		
		if(jogadores.size() == 0)
		{
			System.exit(0);
		}
		
		if(turn == jogadores.size())
		{
			turn = turn - 1;
			currentPlayer = jogadores.get(turn);
			Stand();
		}
		else
		{
			currentPlayer = jogadores.get(turn);
		}
				
		deck.Embaralhar();
	}
	
	public void RewardWinner()
	{	
		// Recompompensa pela vitoria ordinaria
		/*for (int i = 0; i < 6; i++)
		{
			this.winner.getFicha().get(i).setQuantidade(this.winner.getFicha().get(i).getQuantidade() + prizePool.get(i).getQuantidade());
		}*/
	}
	
}
