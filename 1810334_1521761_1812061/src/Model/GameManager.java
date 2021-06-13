package Model;

import javax.swing.JOptionPane;

import View.Interfaces;
import View.NewJFrameDealer;
import java.util.ArrayList;

public class GameManager 
{
	//Para realização dos testes, as variáveis abaixo foram postas como públicas
	public static ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	public static ArrayList<Interfaces> jogadoresInterface = new ArrayList<Interfaces>();
	public NewJFrameDealer windowDealer;
	public float prizePool = 0;
	public Deck deck = new Deck();
	public Jogador currentPlayer;
	public Dealer dealer; 
	public int turn = 0;
	public int n_mao = 0;
	public ArrayList<Jogador> winner;
	
	public void NewGame(int playerCount)
	{
		for (int i = 0; i < playerCount; i++) 
		{
			Jogador player = new Jogador();
			jogadores.add(player);
			Interfaces playerInterface = new Interfaces(player, 0, this);
			playerInterface.getInterface(0).setVisible(true);
			playerInterface.getInterface(0).setTitle("Jogador " + String.valueOf(i+1));
			jogadoresInterface.add(playerInterface);
			//jogadoresInterface.get(i).getInterface(0).setVisible(true);
			//jogadoresInterface.get(i).getInterface(0).setTitle("Jogador " + String.valueOf(i+1));
		}

		System.out.println(jogadoresInterface.size());
		System.out.println(jogadoresInterface.get(0).Interfaces.size());
		
		
		dealer = new Dealer();
		windowDealer = new NewJFrameDealer(dealer, this);
		windowDealer.setTitle("Dealer");
		windowDealer.setVisible(true);
		
		// Reseta o prizepool
		prizePool = 0;
		
		currentPlayer = jogadores.get(turn);
		deck.IniciaBaralho();
		deck.Embaralhar();
		deck.AddCard(new Carta("h_Copas", 10, "t_dez"));
		deck.AddCard(new Carta("h_Copas", 10, "j_valete"));
		System.out.println("jogo com " + playerCount + " jogadores!");
	}
	
	//region Player 
	
	public void AddToPrizePool(int value)
	{		
		if (currentPlayer.getTotalBet(n_mao) + value <= 100 && currentPlayer.getDealt() == false)
		{
			prizePool += value;
			currentPlayer.setTotalBet(currentPlayer.getTotalBet(n_mao) + value, n_mao);
			currentPlayer.setCreditos(currentPlayer.getCreditos() - value);
		}
		
		if(currentPlayer.getTotalBet(0) >= 20 && currentPlayer.getDealt() == false)
		{
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDeal.setEnabled(true);
		}
		else 
		{
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDeal.setEnabled(false);
		}
	}

	public void Deal()
	{
		if(currentPlayer.getDealt() == false) 
		{
			currentPlayer.addCarta(deck.Draw(), 0);
			currentPlayer.addCarta(deck.Draw(), 0);
			currentPlayer.setDealt(true);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDeal.setEnabled(false);			
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(true);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(true);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonStand.setEnabled(true);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(true);
			
			if(currentPlayer.getMao(0).getCartas().get(0).GetValue() == currentPlayer.getMao(0).getCartas().get(1).GetValue() && currentPlayer.getTotalBet(0) <= currentPlayer.getCreditos())
			{
				jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSplit.setEnabled(true);	
			}
			
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
	
	public void Split()
	{
		currentPlayer.split();
		
		jogadoresInterface.get(turn).addNewMao(currentPlayer, 1, this);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSplit.setEnabled(false);	
		
		jogadoresInterface.get(turn).getInterface(n_mao).setTitle("Jogador " + String.valueOf(turn+1) + " Mao " + String.valueOf(n_mao+1));
		jogadoresInterface.get(turn).getInterface(n_mao+1).setTitle("Jogador " + String.valueOf(turn+1) + " Mao " + String.valueOf(n_mao+2));

		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao + 1).p.JButtonDeal.setEnabled(false);			
		jogadoresInterface.get(turn).getInterface(n_mao + 1).p.JButtonHit.setEnabled(true);
		jogadoresInterface.get(turn).getInterface(n_mao + 1).p.JButtonDouble.setEnabled(true);
		jogadoresInterface.get(turn).getInterface(n_mao + 1).p.JButtonStand.setEnabled(true);
		jogadoresInterface.get(turn).getInterface(n_mao + 1).p.JButtonSurrender.setEnabled(false);
		
		jogadoresInterface.get(turn).repaintInterfaces();

		jogadoresInterface.get(turn).getInterface(n_mao+1).setVisible(true);
		JOptionPane.showMessageDialog(null,"Jogador deu Split","turno",JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void Hit()
	{
		if(jogadores.get(turn).getOut(n_mao) == false && currentPlayer != null)
		{
			JOptionPane.showMessageDialog(null,"Jogador deu Hit","turno",JOptionPane.INFORMATION_MESSAGE);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
			Carta topDeck = deck.Draw();
			currentPlayer.addCarta(topDeck, n_mao);
		}
		
		if(jogadores.get(turn).getPontos(n_mao) > 21)
		{
			jogadores.get(turn).setOut(true, n_mao);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(false);
			
		}
		
	}
	
	public void Stand()
	{
		JOptionPane.showMessageDialog(null,"Jogador deu Stand","turno",JOptionPane.INFORMATION_MESSAGE);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonStand.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
		
		if(n_mao == currentPlayer.getMaoQtd() - 1)
		{
			n_mao = 0;
			
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
		else
		{
			n_mao++;
		}

	}
	
	public void Double()
	{
		if (currentPlayer.getCreditos() >= currentPlayer.getTotalBet(n_mao) && currentPlayer.getMao(n_mao).getCartas().size() <= 2)
		{
			currentPlayer.setCreditos(currentPlayer.getCreditos() - currentPlayer.getTotalBet(n_mao));
			currentPlayer.addToBet(currentPlayer.getTotalBet(n_mao), n_mao);
			currentPlayer.addCarta(deck.Draw(), n_mao);
			//jogadoresInterface.get(turn).getInterface(n_mao).p.JLabelBet.setText("Aposta = " + String.valueOf(currentPlayer.getTotalBet(n_mao)));
			//jogadoresInterface.get(turn).getInterface(n_mao).p.JLabelCredits.setText("Creditos = " + String.valueOf(currentPlayer.getCreditos()));
			jogadoresInterface.get(turn).repaintInterfaces();
			if(currentPlayer.getPontos(n_mao) > 21)
			{
				currentPlayer.setOut(true, n_mao);
			}
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(false);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
		}
	}
	
	private void RepaintAll()
	{	
		for(int i = 0; i < jogadoresInterface.size(); i++)
		{
			jogadoresInterface.get(i).repaintInterfaces();	
		}
		
		windowDealer.repaint();
	}
	
	public void Clear()
	{
		for(int i = 0; i < jogadores.size(); i++)
		{
			for(int k = 0; k < jogadores.get(i).getMaoQtd(); k++)
			{
				for(int j = 0; j < jogadores.get(i).getMao(k).getCartas().size(); j++)
				{
					Carta c = jogadores.get(i).getMao(k).getCartas().get(j);
					deck.AddCard(c);
				}
			}
			jogadores.get(i).setMao(new ArrayList<Carta>(), 0);
			jogadores.get(i).setDealt(false);
			jogadores.get(i).setOut(false, 0);
			jogadores.get(i).setPontos(0, 0);
			if (jogadores.get(i).getMaoQtd() > 1) 
			{
				jogadores.get(i).removeMao(1);
				jogadoresInterface.get(i).getInterface(1).setVisible(false);
				jogadoresInterface.get(i).removeMao(1);
			}
		}
		
		for(int i = 0; i < dealer.getMao(0).getCartas().size(); i++)
		{
			Carta c = dealer.getMao(0).getCartas().get(i);
			deck.AddCard(c);
		}
		
		dealer.setMao(new ArrayList<Carta>(), 0);
		dealer.setDealt(false);
		dealer.setPontos(0, 0);
		
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
		currentPlayer.setOut(true, n_mao);
		currentPlayer.setCreditos(currentPlayer.getTotalBet(n_mao)/2 + currentPlayer.getCreditos());
		currentPlayer.setTotalBet(currentPlayer.getTotalBet(n_mao)/2, n_mao);
		prizePool -= currentPlayer.getTotalBet(n_mao)/2;
		//jogadoresInterface.get(turn).getInterface(n_mao).p.JLabelBet.setText("Aposta = " + String.valueOf(currentPlayer.getTotalBet(n_mao)));
		//jogadoresInterface.get(turn).getInterface(n_mao).p.JLabelCredits.setText("Creditos = " + String.valueOf(currentPlayer.getCreditos()));
		jogadoresInterface.get(turn).repaintInterfaces();
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
	}
	
	public void Quit()
	{
		if (currentPlayer.getMao(n_mao).getCartas().size() > 0)
		{
			for (int i = 0; i < currentPlayer.getMao(n_mao).getCartas().size() - 1; i++) 
			{
				Carta c = currentPlayer.getMao(n_mao).getCartas().get(i);
				deck.AddCard(c);				
			}
			jogadores.get(turn).setMao(new ArrayList<Carta>(), n_mao);			
		}
		
		jogadoresInterface.get(turn).getInterface(n_mao).setVisible(false);
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
		
		if(dealer.getPontos(0) == 10)
		{
			// Busca algum jogador com blackjack
			for(int i = 0; i < jogadores.size(); i++)
			{
				for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
				{
					if(jogadores.get(i).getBlackjack(j) == true)
					{
						dealerNeedToShowCard = true;
					}
				}
			}
			
			if (dealerNeedToShowCard == true)
			{
				windowDealer.showHiddenCard();
				if (dealer.getPontos(0) == 21) 
				{
					// Caso 1: Jogador e dealer possuem blackjack (empate)
					for(int i = 0; i < jogadores.size(); i++)
					{
						for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
						{
							if(jogadores.get(i).getBlackjack(j) == true)
							{
								rewardWinner(1, i, j);
							}
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
						for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
						{
							// Caso 2: Jogador possui blackjack, mas dealer não
							if(jogadores.get(i).getBlackjack(j) == true)
							{
								rewardWinner(2.5f, i, j);
							}
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
					for (int j = 0; j < jogadores.get(i).getMaoQtd(); j++) 
					{
						rewardWinner(0, i, j);
					}
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
			for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
			{
				if(jogadores.get(i).getBlackjack(j) == true)
				{
					rewardWinner(2.5f, i, j);
				}
			}
		}
		
		while(dealer.getPontos(0) < 17)
		{
			Dealer_Hit();
		}
			
		for(int i = 0; i < jogadores.size(); i++)
		{
			for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
			{
				if(jogadores.get(i).getOut(j) == false && jogadores.get(i).getBlackjack(j) == false)
				{
					if (jogadores.get(i).getPontos(j) > dealer.getPontos(0) || dealer.getPontos(0) > 21)
					{
						rewardWinner(2, i, j);
					}
					else
					{
						rewardWinner(0, i, j);
					}
				}
				else if(jogadores.get(i).getOut(j) == true)
				{
					rewardWinner(0, i, j);
				}
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
			for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
			{
				if(jogadores.get(i).getOut(j)  == false)
				{
					theresPossibleWinner = true;
					break;
				}
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
				for (int j = 0; j < jogadores.size(); j++) 
				{
					rewardWinner(0, i, j);
				}
				
			}
			prizePool = 0;
			windowDealer.resetPrizePoolLabel();
		}
	}
	
	private void rewardWinner(float modifier, int i, int mao_num) 
	{
		System.out.println("dealer wins");
		jogadores.get(i).setCreditos(modifier * jogadores.get(i).getTotalBet(mao_num) + jogadores.get(i).getCreditos());
		jogadores.get(i).setTotalBet(0, mao_num);
		//jogadoresInterface.get(i).getInterface(mao_num).p.JLabelBet.setText("Aposta = " + String.valueOf(jogadores.get(i).getTotalBet(mao_num)));
		//jogadoresInterface.get(i).getInterface(mao_num).p.JLabelCredits.setText("Creditos = " + String.valueOf(jogadores.get(i).getCreditos()));
		jogadoresInterface.get(i).repaintInterfaces();
	}

	public void Dealer_Deal()
	{
		dealer.addCarta(deck.Draw(), 0);
		windowDealer.hideHiddenCard();
		dealer.addCarta(deck.Draw(), 0);
		dealer.setDealt(true);
		
		dealer.setPontos(dealer.getPontos(0) - dealer.getMao(0).getCartas().get(0).GetValue(), 0);
	}
	
	public void Dealer_Hit()
	{	
		Carta topDeck = deck.Draw();
		dealer.addCarta(topDeck, 0);
		
		windowDealer.repaint();
	}
	
	//endregion
}
