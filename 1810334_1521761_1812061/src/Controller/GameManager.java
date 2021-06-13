package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Carta;
import Model.Dealer;
import Model.Deck;
import Model.Ficha;
import Model.Jogador;
import View.NewJFrameDealer;
import View.NewJFramePlayer;

import java.util.ArrayList;

public class GameManager 
{
	//Para realiza��o dos testes, as vari�veis abaixo foram postas como p�blicas
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
		for (int i = 0; i < playerCount; i++) // Inicializa�� dos jogadores e suas interfaces
		{
			Jogador player = new Jogador();
			jogadores.add(player);
			NewJFramePlayer playerInterface = new NewJFramePlayer(player, this);
			playerInterface.setVisible(true);
			playerInterface.setTitle("Jogador " + String.valueOf(i+1));
			jogadoresInterface.add(playerInterface);
		}
		// Inicializa��o do dealer
		dealer = new Dealer();
		windowDealer = new NewJFrameDealer(dealer, this);
		windowDealer.setTitle("Dealer");
		windowDealer.setVisible(true);
		
		// Reseta o prizepool
		prizePool = 0;
		
		//define o primeiro jogador a agir
		currentPlayer = jogadores.get(turn);
		
		//Inicializa��o do baralho
		deck.IniciaBaralho();
		deck.Embaralhar();
		System.out.println("jogo com " + playerCount + " jogadores!");
	}
	
	//region Player 
	
	public void AddToPrizePool(int value)
	{
		// Impede que o jogador aposte mais de 100 cr�ditos
		if (currentPlayer.getTotalBet() + value <= 100 && currentPlayer.getDealt() == false)
		{
			prizePool += value;
			currentPlayer.setTotalBet(currentPlayer.getTotalBet() + value);
			currentPlayer.setCreditos(currentPlayer.getCreditos() - value);
		}
		
		// Impede que o jogador de deal sem ter apostado pelo menos 20 cr�ditos
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
			// Concede as primeiras cartas do jogador e habilita os outros bot�es
			currentPlayer.addCarta(deck.Draw());
			currentPlayer.addCarta(deck.Draw());
			currentPlayer.setDealt(true);
			jogadoresInterface.get(turn).p.JButtonDeal.setEnabled(false);			
			jogadoresInterface.get(turn).p.JButtonHit.setEnabled(true);
			jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(true);
			jogadoresInterface.get(turn).p.JButtonStand.setEnabled(true);
			jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(true);
			
			// Realiza a passagem do turno
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
			
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " deu Deal","Turno",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void Hit()
	{
		if(jogadores.get(turn).getOut() == false && currentPlayer != null)
		{
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " deu Hit","Turno",JOptionPane.INFORMATION_MESSAGE);
			// Desabilita os bot�es de double e surrender ap�s receber a terceira carta
			jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(false);
			Carta topDeck = deck.Draw();
			currentPlayer.addCarta(topDeck);
		}
		
		// Desabilita os bot�es caso o jogador ultrapasse 21 pontos
		if(jogadores.get(turn).getPontos() > 21)
		{
			jogadores.get(turn).setOut(true);
			jogadoresInterface.get(turn).p.JButtonHit.setEnabled(false);
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " est� fora da rodada","Turno",JOptionPane.INFORMATION_MESSAGE);			
		}
		
	}
	
	public void Stand()
	{
		// Desabilita os bot�es do jogador e realiza a passagem de turno
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
			// Dobra a aposta do jogador e concede a pr�xima carta
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " deu Double","Turno",JOptionPane.INFORMATION_MESSAGE);
			currentPlayer.setCreditos(currentPlayer.getCreditos() - currentPlayer.getTotalBet());
			currentPlayer.addToBet(currentPlayer.getTotalBet());
			currentPlayer.addCarta(deck.Draw());
			jogadoresInterface.get(turn).p.JLabelBet.setText("Aposta = " + String.valueOf(currentPlayer.getTotalBet()));
			jogadoresInterface.get(turn).p.JLabelCredits.setText("Creditos = " + String.valueOf(currentPlayer.getCreditos()));
			// Verifica se o jogador ultrapassou 21
			if(currentPlayer.getPontos() > 21)
			{
				currentPlayer.setOut(true);
				JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " est� fora da rodada","Turno",JOptionPane.INFORMATION_MESSAGE);
			}
			// Desabilita os outros bot�es	
			jogadoresInterface.get(turn).p.JButtonHit.setEnabled(false);
			jogadoresInterface.get(turn).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).p.JButtonSurrender.setEnabled(false);
		}
	}
	
	private void RepaintAll()
	{	
		// Atualiza as interfaces
		for(int i = 0; i < jogadoresInterface.size(); i++)
		{
			jogadoresInterface.get(i).repaint();	
		}
		
		windowDealer.repaint();
	}
	
	public void Clear()
	{
		JOptionPane.showMessageDialog(null,"Iniciando uma nova rodada","Jogo",JOptionPane.INFORMATION_MESSAGE);
		for(int i = 0; i < jogadores.size(); i++)
		{
			// Retorna as cartas dos jogadores para o baralho
			for(int j = 0; j < jogadores.get(i).getMao().size(); j++)
			{
				Carta c = jogadores.get(i).getMao().get(j);
				deck.AddCard(c);
			}
			
			// Reinicia os jogadores
			jogadores.get(i).setMao(new ArrayList<Carta>());
			jogadores.get(i).setDealt(false);
			jogadores.get(i).setOut(false);
			jogadores.get(i).setPontos(0);
		}
		
		for(int i = 0; i < dealer.getMao().size(); i++)
		{
			// Retorna as cartas do dealer para o baralho
			Carta c = dealer.getMao().get(i);
			deck.AddCard(c);
		}
		
		// Reinicia o dealer
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
		// Retorna parte da aposta do jogador e o remove da rodada
		JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " desistiu da rodada","Turno",JOptionPane.INFORMATION_MESSAGE);
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
		JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " abandonou a partida","Jogo",JOptionPane.INFORMATION_MESSAGE);
		if (currentPlayer.getMao().size() > 0)
		{
			// Retorna as cartas do jogador para o baralho
			for (int i = 0; i < currentPlayer.getMao().size() - 1; i++) 
			{
				Carta c = currentPlayer.getMao().get(i);
				deck.AddCard(c);				
			}
			jogadores.get(turn).setMao(new ArrayList<Carta>());			
		}
		
		// Fecha a janela do jogador que saiu
		jogadoresInterface.get(turn).setVisible(false);
		jogadores.remove(turn);
		jogadoresInterface.remove(turn);
		
		// Se n�o houver mais jogadores, encerra o programa
		if(jogadores.size() == 0)
		{
			JOptionPane.showMessageDialog(null,"Todos os jogadores sa�ram. Encerrando partida","Jogo",JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		// Passa o turno para o pr�ximo jogador
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
							JOptionPane.showMessageDialog(null,"Push para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
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
						// Caso 2: Jogador possui blackjack, mas dealer n�o
						if(jogadores.get(i).getBlackjack() == true)
						{
							JOptionPane.showMessageDialog(null,"Blackjack para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
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
					JOptionPane.showMessageDialog(null,"Vit�ria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
					rewardWinner(0, i);
				}
				prizePool = 0;
				windowDealer.resetPrizePoolLabel();
				return;
			}
		}
		
		windowDealer.showHiddenCard();
		
		// Caso 4: Jogador tem blackjack e dealer n�o
		for(int i = 0; i < jogadores.size(); i++)
		{
			if(jogadores.get(i).getBlackjack() == true)
			{
				JOptionPane.showMessageDialog(null,"Blackjack para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
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
					JOptionPane.showMessageDialog(null,"Vit�ria ordin�ria para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
					rewardWinner(2, i);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Vit�ria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
					rewardWinner(0, i);
				}
			}
			else if(jogadores.get(i).getOut() == true)
			{
				JOptionPane.showMessageDialog(null,"Vit�ria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
				rewardWinner(0, i);
			}
		}
		
		prizePool = 0;
		windowDealer.resetPrizePoolLabel();
		JOptionPane.showMessageDialog(null,"Fim da rodada","Jogo",JOptionPane.INFORMATION_MESSAGE);
	}
	
	//endregion
	
	//region Dealer
	
	private void DealerTurn()
	{
		// Verifica se existe algum jogador cuja pontua��o n�o ultrapassou 21
		Boolean theresPossibleWinner = false;		
		for(int i = 0; i < jogadores.size(); i++)
		{
			if(jogadores.get(i).getOut()  == false)
			{
				theresPossibleWinner = true;
				break;
			}
		}
		
		// Verifica a condi��o de vit�ria de cada jogador
		if (theresPossibleWinner == true)
		{
			DeclareWinner();
		}
		else
		{
			for(int i = 0; i < jogadores.size(); i++)
			{
				JOptionPane.showMessageDialog(null,"Vit�ria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
				rewardWinner(0, i);
				
			}
			prizePool = 0;
			windowDealer.resetPrizePoolLabel();
			JOptionPane.showMessageDialog(null,"Fim da rodada","Jogo",JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	private void rewardWinner(float modifier, int i) 
	{
		// Distribui a premia��o para os jogadores
		jogadores.get(i).setCreditos(modifier * jogadores.get(i).getTotalBet() + jogadores.get(i).getCreditos());
		jogadores.get(i).setTotalBet(0);
		jogadoresInterface.get(i).p.JLabelBet.setText("Aposta = " + String.valueOf(jogadores.get(i).getTotalBet()));
		jogadoresInterface.get(i).p.JLabelCredits.setText("Creditos = " + String.valueOf(jogadores.get(i).getCreditos()));
		jogadoresInterface.get(i).repaint();
		
		// Se o jogador n�o tiver cr�ditos restando, ele sai da partida.
		if(jogadores.get(i).getCreditos() <= 0)
		{
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(i) + " est� fora da partida","Jogo",JOptionPane.INFORMATION_MESSAGE);
			jogadoresInterface.get(i).setVisible(false);
			jogadores.remove(i);
			jogadoresInterface.remove(i);
		}
	}

	public void Dealer_Deal()
	{
		// Distribui as duas primeiras cartas para o dealer
		dealer.addCarta(deck.Draw());
		windowDealer.hideHiddenCard();
		dealer.addCarta(deck.Draw());
		dealer.setDealt(true);
		
		dealer.setPontos(dealer.getPontos() - dealer.getMao().get(0).GetValue());
	}
	
	public void Dealer_Hit()
	{	
		// Compra uma nova carta para o dealer
		Carta topDeck = deck.Draw();
		dealer.addCarta(topDeck);
		
		windowDealer.repaint();
	}
	
	//endregion
	
	//region save
		
	public void SaveGame() throws IOException
	{
		File saveFile = new File("partida.txt");
		if(saveFile.createNewFile())
		{
			// Cria um novo arquivo salvo
			JOptionPane.showMessageDialog(null,"Jogo Salvo","Jogo",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			// Substitui o arquivo salvo
			saveFile.delete();
			saveFile.createNewFile();
			JOptionPane.showMessageDialog(null,"Arquivo salvo substituido","Jogo",JOptionPane.INFORMATION_MESSAGE);
		}
		
		FileWriter writer = new FileWriter("partida.txt");
		writer.write(String.valueOf(jogadores.size()) + "\n"); // Salva o n�mero de jogadores
		
		// Salva as informa��es do dealer
		writer.write(String.valueOf(dealer.getMao().size()) + "\n");
		writer.write(String.valueOf(windowDealer.getIsHidden()) + "\n");
		writer.write(String.valueOf(dealer.getPontos()) + "\n");
		for(int i = 0; i < dealer.getMao().size(); i++)
		{
			writer.write(dealer.getMao().get(i).GetSuite() + "\n");
			writer.write(String.valueOf(dealer.getMao().get(i).GetValue()) + "\n");
			writer.write(dealer.getMao().get(i).GetName() + "\n");
		}
		
		// Salva informa��es globais
		writer.write(String.valueOf(prizePool) + "\n");
		writer.write(String.valueOf(turn) + "\n");
		
		// Salva as informa��es dos jogadores
		for(int i = 0; i < jogadores.size(); i++)
		{
			writer.write(String.valueOf(jogadores.get(i).getMao().size()) + "\n");
			writer.write(String.valueOf(jogadores.get(i).getTotalBet()) + "\n");
			writer.write(String.valueOf(jogadores.get(i).getCreditos()) + "\n");
			
			for(int j = 0; j < jogadores.get(i).getMao().size(); j++)
			{
				writer.write(jogadores.get(i).getMao().get(j).GetSuite() + "\n");
				writer.write(String.valueOf(jogadores.get(i).getMao().get(j).GetValue()) + "\n");
				writer.write(jogadores.get(i).getMao().get(j).GetName() + "\n");
			}
			
			writer.write(String.valueOf(jogadoresInterface.get(i).p.JButtonDeal.isEnabled()) + "\n");
			writer.write(String.valueOf(jogadoresInterface.get(i).p.JButtonHit.isEnabled()) + "\n");
			writer.write(String.valueOf(jogadoresInterface.get(i).p.JButtonSplit.isEnabled()) + "\n");
			writer.write(String.valueOf(jogadoresInterface.get(i).p.JButtonSurrender.isEnabled()) + "\n");
			writer.write(String.valueOf(jogadoresInterface.get(i).p.JButtonStand.isEnabled()) + "\n");
			writer.write(String.valueOf(jogadoresInterface.get(i).p.JButtonDouble.isEnabled()) + "\n");
		}

		
		writer.close();
	}
	
	public void LoadGame() throws FileNotFoundException
	{
		File loadFile = new File("partida.txt");
		Scanner reader = new Scanner(loadFile);
		
		int numPlayers = Integer.parseInt(reader.nextLine()); // Recupera o n�mero de jogadores e cria uma nova partida
		NewGame(numPlayers);
		
		// Recupera as informa��es do dealer e atualiza o jogo com elas
		int dealerHand = Integer.parseInt(reader.nextLine());
		windowDealer.setIsHiiden(Boolean.parseBoolean(reader.nextLine()));
		windowDealer.getSumLabel().setText("Soma das cartas = " + Integer.parseInt(reader.nextLine()));
		for(int i = 0; i < dealerHand; i++)
		{
			dealer.setDealt(true);
			Carta newCard = new Carta(reader.nextLine(), Integer.parseInt(reader.nextLine()), reader.nextLine());
			dealer.addCarta(newCard);
		}
		
		prizePool = Float.parseFloat(reader.nextLine());
		windowDealer.getPrizePoolLabel().setText("Aposta total = " + String.valueOf(prizePool));
		turn = Integer.parseInt(reader.nextLine());
		
		// Recupera as informa��es dos jogadores e atualiza o jogo com elas
		for(int i = 0; i < numPlayers; i++)
		{
			int handSize = Integer.parseInt(reader.nextLine());
			jogadores.get(i).setTotalBet(Integer.parseInt(reader.nextLine()));
			jogadores.get(i).setCreditos(Float.parseFloat(reader.nextLine()));
			jogadoresInterface.get(i).p.JLabelBet.setText("Aposta = " + String.valueOf(currentPlayer.getTotalBet()));
			jogadoresInterface.get(i).p.JLabelCredits.setText("Creditos = " + String.valueOf(currentPlayer.getCreditos()));
			
			for(int j = 0; j < handSize; j++)
			{
				jogadores.get(i).setDealt(true);
				Carta newCard = new Carta(reader.nextLine(), Integer.parseInt(reader.nextLine()), reader.nextLine());
				jogadores.get(i).addCarta(newCard);
			}
			
			jogadoresInterface.get(i).p.JButtonDeal.setEnabled(Boolean.parseBoolean(reader.nextLine()));
			jogadoresInterface.get(i).p.JButtonHit.setEnabled(Boolean.parseBoolean(reader.nextLine()));
			jogadoresInterface.get(i).p.JButtonSplit.setEnabled(Boolean.parseBoolean(reader.nextLine()));
			jogadoresInterface.get(i).p.JButtonSurrender.setEnabled(Boolean.parseBoolean(reader.nextLine()));
			jogadoresInterface.get(i).p.JButtonStand.setEnabled(Boolean.parseBoolean(reader.nextLine()));
			jogadoresInterface.get(i).p.JButtonDouble.setEnabled(Boolean.parseBoolean(reader.nextLine()));
		}
		
		// Atualiza as janelas do jogo
		RepaintAll();
		JOptionPane.showMessageDialog(null,"Partida carregada","Jogo",JOptionPane.INFORMATION_MESSAGE);
	}
	
	//endregion
}
