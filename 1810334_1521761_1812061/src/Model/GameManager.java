package Model;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import View.NewJFrameDealer;
import View.NewJFramePlayer;

import java.util.ArrayList;

public class GameManager 
{
	//Para realização dos testes, as variáveis abaixo foram postas como públicas
	public static ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	public static ArrayList<NewJFramePlayer> jogadoresInterface = new ArrayList<NewJFramePlayer>();
	public NewJFrameDealer windowDealer;
	public float prizePool = 0;
	public Deck deck = new Deck();
	public Jogador currentPlayer;
	public Dealer dealer; 
	public int turn = 0;
	public ArrayList<Jogador> winner;
	
	public void NewGame(int playerCount)
	{
		Ficha FichasPilha;
		for (int i = 0; i < playerCount; i++) 
		{
			Jogador player = new Jogador();
			jogadores.add(player);
			NewJFramePlayer playerInterface = new NewJFramePlayer(player, this);
			playerInterface.setVisible(true);
			playerInterface.setTitle("Jogador " + String.valueOf(i+1));
			jogadoresInterface.add(playerInterface);
		}
		dealer = new Dealer();
		windowDealer = new NewJFrameDealer(dealer, this);
		windowDealer.setTitle("Dealer");
		windowDealer.setVisible(true);
		
		// Reseta o prizepool
		prizePool = 0;
		
		currentPlayer = jogadores.get(turn);
		deck.IniciaBaralho();
		deck.Embaralhar();
		System.out.println("jogo com " + playerCount + " jogadores!");
	}
	
	//region Player 
	
	public void AddToPrizePool(int value)
	{		
		if (currentPlayer.getTotalBet() + value <= 100 && currentPlayer.getDealt() == false)
		{
			prizePool += value;
			currentPlayer.setTotalBet(currentPlayer.getTotalBet() + value);
			currentPlayer.setCreditos(currentPlayer.getCreditos() - value);
		}
		
		if(currentPlayer.getTotalBet() >= 20 && currentPlayer.getDealt() == false)
		{
			jogadoresInterface.get(turn).p.JButtonDeal.setEnabled(true);
		}
		else 
		{
			jogadoresInterface.get(turn).p.JButtonDeal.setEnabled(false);
		}
	}

	public void Deal()
	{
		if(currentPlayer.getDealt() == false) 
		{
			currentPlayer.addCarta(deck.Draw());
			currentPlayer.addCarta(deck.Draw());
			currentPlayer.setDealt(true);
			jogadoresInterface.get(turn).p.JButtonDeal.setEnabled(false);			
			jogadoresInterface.get(turn).p.JButtonHit.setEnabled(true);
			jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(true);
			jogadoresInterface.get(turn).p.JButtonStand.setEnabled(true);
			jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(true);
			if (turn == jogadores.size() - 1) 
			{
				turn = 0;
				Dealer_Deal();
			}
			else 
			{
				turn++;
			}
			currentPlayer = jogadores.get(turn); 
			
			JOptionPane.showMessageDialog(null,"Jogador deu Deal","turno",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void Hit()
	{
		if(jogadores.get(turn).getOut() == false && currentPlayer != null)
		{
			JOptionPane.showMessageDialog(null,"Jogador deu Hit","turno",JOptionPane.INFORMATION_MESSAGE);
			jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(false);
			Carta topDeck = deck.Draw();
			currentPlayer.addCarta(topDeck);
		}
		
		if(jogadores.get(turn).getPontos() > 21)
		{
			jogadores.get(turn).setOut(true);
			jogadoresInterface.get(turn).p.JButtonHit.setEnabled(false);
			
		}
		
	}
	
	public void Stand()
	{
		JOptionPane.showMessageDialog(null,"Jogador deu Stand","turno",JOptionPane.INFORMATION_MESSAGE);
		jogadoresInterface.get(turn).p.JButtonHit.setEnabled(false);
		jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(false);
		jogadoresInterface.get(turn).p.JButtonStand.setEnabled(false);
		jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(false);
		if(turn == jogadores.size() - 1) //Se for o ultimo jogador, acabou a rodada
		{
			DealerTurn();
		}
		else
		{
			turn++;
			currentPlayer = jogadores.get(turn); 
		}
	}
	
	public void Double()
	{
		if (currentPlayer.getCreditos() >= currentPlayer.getTotalBet() && currentPlayer.getMao().size() == 2)
		{
			currentPlayer.setCreditos(currentPlayer.getCreditos() - currentPlayer.getTotalBet());
			currentPlayer.addToBet(currentPlayer.getTotalBet());
			currentPlayer.addCarta(deck.Draw());
			jogadoresInterface.get(turn).p.JLabelBet.setText("Aposta = " + String.valueOf(currentPlayer.getTotalBet()));
			jogadoresInterface.get(turn).p.JLabelCredits.setText("Creditos = " + String.valueOf(currentPlayer.getCreditos()));
			if(currentPlayer.getPontos() > 21)
			{
				currentPlayer.setOut(true);
			}
			jogadoresInterface.get(turn).p.JButtonHit.setEnabled(false);
			jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(false);
		}
	}
	
	private void RepaintAll()
	{	
		for(int i = 0; i < jogadoresInterface.size(); i++)
		{
			jogadoresInterface.get(i).repaint();	
		}
		
		windowDealer.repaint();
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
			jogadores.get(i).setOut(false);
			jogadores.get(i).setPontos(0);
		}
		
		for(int i = 0; i < dealer.getMao().size(); i++)
		{
			Carta c = dealer.getMao().get(i);
			deck.AddCard(c);
		}
		
		dealer.setMao(new ArrayList<Carta>());
		dealer.setDealt(false);
		dealer.setPontos(0);
		
		if(windowDealer.getIsHidden() == true)
		{
			windowDealer.setIsHiiden(false);
		}
		
		deck.Embaralhar();
		RepaintAll();
		turn = 0;
		currentPlayer = jogadores.get(turn);
	}
	
	
	public void Surrender()
	{
		currentPlayer.setOut(true);
		currentPlayer.setCreditos(currentPlayer.getTotalBet()/2 + currentPlayer.getCreditos());
		currentPlayer.setTotalBet(currentPlayer.getTotalBet()/2);
		prizePool -= currentPlayer.getTotalBet()/2;
		jogadoresInterface.get(turn).p.JLabelBet.setText("Aposta = " + String.valueOf(currentPlayer.getTotalBet()));
		jogadoresInterface.get(turn).p.JLabelCredits.setText("Creditos = " + String.valueOf(currentPlayer.getCreditos()));
		jogadoresInterface.get(turn).p.JButtonHit.setEnabled(false);
		jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(false);
		jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(false);
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
	
	private void DeclareWinner()
	{
		Boolean dealerNeedToShowCard = false;
		
		if(dealer.getPontos() == 10)
		{
			// Busca algum jogador com blackjack
			for(int i = 0; i < jogadores.size(); i++)
			{
				if(jogadores.get(i).getBlackjack() == true)
				{
					dealerNeedToShowCard = true;
				}
			}
			
			if (dealerNeedToShowCard == true)
			{
				windowDealer.showHiddenCard();
				if (dealer.getPontos() == 21) 
				{
					// Caso 1: Jogador e dealer possuem blackjack (empate)
					for(int i = 0; i < jogadores.size(); i++)
					{
						if(jogadores.get(i).getBlackjack() == true)
						{
							rewardWinner(1, i);
						}
					}
					prizePool = 0;
					windowDealer.resetPrizePoolLabel();
					return;
				}
				else
				{					
					for(int i = 0; i < jogadores.size(); i++)
					{
						// Caso 2: Jogador possui blackjack, mas dealer não
						if(jogadores.get(i).getBlackjack() == true)
						{
							rewardWinner(2.5f, i);
						}
					}
					prizePool = 0;
					windowDealer.resetPrizePoolLabel();
					return;
				}
			}
			else // Caso 3: Apenas o dealer tem blackjack
			{
				for(int i = 0; i < jogadores.size(); i++)
				{
					rewardWinner(0, i);
				}
				prizePool = 0;
				windowDealer.resetPrizePoolLabel();
				return;
			}
		}
		
		windowDealer.showHiddenCard();
		
		// Caso 4: Jogador tem blackjack e dealer não
		for(int i = 0; i < jogadores.size(); i++)
		{
			if(jogadores.get(i).getBlackjack() == true)
			{
				rewardWinner(2.5f, i);
			}
		}
		
		while(dealer.getPontos() < 17)
		{
			Dealer_Hit();
		}
			
		for(int i = 0; i < jogadores.size(); i++)
		{
			if(jogadores.get(i).getOut() == false && jogadores.get(i).getBlackjack() == false)
			{
				if (jogadores.get(i).getPontos() > dealer.getPontos() || dealer.getPontos() > 21)
				{
					rewardWinner(2, i);
				}
				else
				{
					rewardWinner(0, i);
				}
			}
			else if(jogadores.get(i).getOut() == true)
			{
				rewardWinner(0, i);
			}
		}
		
		prizePool = 0;
		windowDealer.resetPrizePoolLabel();
	}
	
	//endregion
	
	//region Dealer
	
	private void DealerTurn()
	{
		Boolean theresPossibleWinner = false;		
		for(int i = 0; i < jogadores.size(); i++)
		{
			if(jogadores.get(i).getOut()  == false)
			{
				theresPossibleWinner = true;
				break;
			}
		}
		
		if (theresPossibleWinner == true)
		{
			DeclareWinner();
		}
		else
		{
			for(int i = 0; i < jogadores.size(); i++)
			{
				rewardWinner(0, i);
				
			}
			prizePool = 0;
			windowDealer.resetPrizePoolLabel();
		}
	}
	
	private void rewardWinner(float modifier, int i) 
	{
		System.out.println("dealer wins");
		jogadores.get(i).setCreditos(modifier * jogadores.get(i).getTotalBet() + jogadores.get(i).getCreditos());
		jogadores.get(i).setTotalBet(0);
		jogadoresInterface.get(i).p.JLabelBet.setText("Aposta = " + String.valueOf(jogadores.get(i).getTotalBet()));
		jogadoresInterface.get(i).p.JLabelCredits.setText("Creditos = " + String.valueOf(jogadores.get(i).getCreditos()));
		jogadoresInterface.get(i).repaint();
	}

	public void Dealer_Deal()
	{
		dealer.addCarta(deck.Draw());
		windowDealer.hideHiddenCard();
		dealer.addCarta(deck.Draw());
		dealer.setDealt(true);
		
		dealer.setPontos(dealer.getPontos() - dealer.getMao().get(0).GetValue());
	}
	
	public void Dealer_Hit()
	{	
		Carta topDeck = deck.Draw();
		dealer.addCarta(topDeck);
		
		windowDealer.repaint();
	}
	
	//endregion
}
