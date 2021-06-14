// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package Controller;

import View.Interfaces;
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
import java.util.ArrayList;

public class GameManager 
{
	//Para realizacao dos testes, as variaveis abaixo foram postas como publicas
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
		for (int i = 0; i < playerCount; i++) // Inicializacao dos jogadores e suas interfaces
		{
			Jogador player = new Jogador();
			jogadores.add(player);
			Interfaces playerInterface = new Interfaces(player, 0, this);
			playerInterface.getInterface(0).setVisible(true);
			playerInterface.getInterface(0).setTitle("Jogador " + String.valueOf(i+1));
			jogadoresInterface.add(playerInterface);
		}
		// Inicializacao do dealer
		dealer = new Dealer();
		windowDealer = new NewJFrameDealer(dealer, this);
		windowDealer.setTitle("Dealer");
		windowDealer.setVisible(true);
		
		// Reseta o prizepool
		prizePool = 0;
		
		//define o primeiro jogador a agir
		currentPlayer = jogadores.get(turn);
		
		//Inicializacao do baralho
		deck.IniciaBaralho();
		deck.Embaralhar();
	}
	
	//region Player 
	
	public void AddToPrizePool(int value)
	{
		// Impede que o jogador aposte mais de 100 creditos
		if (currentPlayer.getTotalBet(n_mao) + value <= 100 && currentPlayer.getDealt() == false)
		{
			prizePool += value;
			currentPlayer.setTotalBet(currentPlayer.getTotalBet(n_mao) + value, n_mao);
			currentPlayer.setCreditos(currentPlayer.getCreditos() - value);
		}
		
		// Impede que o jogador de deal sem ter apostado pelo menos 20 creditos
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
			// Concede as primeiras cartas do jogador e habilita os outros botoes
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
			
			// Realiza a passagem do turno para o dealer
			if (turn == jogadores.size() - 1) 
			{
				turn = 0;
				Dealer_Deal();
			}
			else // avanca o turno para o proximo jogador
			{
				turn++;
			}
			//atualiza o jogador atual
			currentPlayer = jogadores.get(turn); 
			
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " deu Deal","Turno",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void Split()
	{
		// avisa para o jogador fazer o slit e criar a nova mao
		currentPlayer.split();
		
		//cria uma nova interface para a nova mao
		jogadoresInterface.get(turn).addNewMao(currentPlayer, 1, this);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSplit.setEnabled(false);	
		
		// atualiza os titulos das janelas
		jogadoresInterface.get(turn).getInterface(n_mao).setTitle("Jogador " + String.valueOf(turn+1) + " Mao " + String.valueOf(n_mao+1));
		jogadoresInterface.get(turn).getInterface(n_mao+1).setTitle("Jogador " + String.valueOf(turn+1) + " Mao " + String.valueOf(n_mao+2));

		// liga e desliga os botoes necessarios
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
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " deu Hit","Turno",JOptionPane.INFORMATION_MESSAGE);
			// Desabilita os botoes de double e surrender apos receber a terceira carta
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
			Carta topDeck = deck.Draw();
			currentPlayer.addCarta(topDeck, n_mao);
		}
		
		// Desabilita os botoes caso o jogador ultrapasse 21 pontos
		if(jogadores.get(turn).getPontos(n_mao) > 21)
		{
			jogadores.get(turn).setOut(true, n_mao);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(false);
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " estï¿½ fora da rodada","Turno",JOptionPane.INFORMATION_MESSAGE);			
		}
		
	}
	
	public void Stand()
	{
		// Desabilita os botoes¿½es do jogador e realiza a passagem de turno
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
			// Dobra a aposta do jogador e concede a proxima carta
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " deu Double","Turno",JOptionPane.INFORMATION_MESSAGE);
			currentPlayer.setCreditos(currentPlayer.getCreditos() - currentPlayer.getTotalBet(n_mao));
			currentPlayer.addToBet(currentPlayer.getTotalBet(n_mao), n_mao);
			currentPlayer.addCarta(deck.Draw(), n_mao);
			jogadoresInterface.get(turn).repaintInterfaces();
			// Verifica se o jogador ultrapassou 21
			if(currentPlayer.getPontos(n_mao) > 21)
			{
				currentPlayer.setOut(true, n_mao);
				JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " estï¿½ fora da rodada","Turno",JOptionPane.INFORMATION_MESSAGE);
			}
			// Desabilita os outros botoes¿½es	
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(false);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(false);
			jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
		}
	}
	
	private void RepaintAll()
	{	
		// Atualiza as interfaces
		for(int i = 0; i < jogadoresInterface.size(); i++)
		{
			jogadoresInterface.get(i).repaintInterfaces();	
		}
		
		windowDealer.repaint();
	}
	
	public void Clear()
	{
		JOptionPane.showMessageDialog(null,"Iniciando uma nova rodada","Jogo",JOptionPane.INFORMATION_MESSAGE);
		for(int i = 0; i < jogadores.size(); i++)
		{
			// Retorna as cartas dos jogadores para o baralho
			for(int k = 0; k < jogadores.get(i).getMaoQtd(); k++)
			{
				for(int j = 0; j < jogadores.get(i).getMao(k).getCartas().size(); j++)
				{
					Carta c = jogadores.get(i).getMao(k).getCartas().get(j);
					deck.AddCard(c);
				}
			}
			
			// Reinicia os jogadores
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
			// Retorna as cartas do dealer para o baralho
			Carta c = dealer.getMao(0).getCartas().get(i);
			deck.AddCard(c);
		}
		
		// Reinicia o dealer
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
		// Retorna parte da aposta do jogador e o remove da rodada
		JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " desistiu da rodada","Turno",JOptionPane.INFORMATION_MESSAGE);
		currentPlayer.setOut(true, n_mao);
		currentPlayer.setCreditos(currentPlayer.getTotalBet(n_mao)/2 + currentPlayer.getCreditos());
		currentPlayer.setTotalBet(currentPlayer.getTotalBet(n_mao)/2, n_mao);
		prizePool -= currentPlayer.getTotalBet(n_mao)/2;
		
		// atualiza a interace sobre a desistencia
		jogadoresInterface.get(turn).repaintInterfaces();
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonHit.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonDouble.setEnabled(false);
		jogadoresInterface.get(turn).getInterface(n_mao).p.JButtonSurrender.setEnabled(false);
	}
	
	public void Quit()
	{
		JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(turn + 1) + " abandonou a partida","Jogo",JOptionPane.INFORMATION_MESSAGE);
		
		if (currentPlayer.getMao(n_mao).getCartas().size() > 0)
		{
			// Retorna as cartas do jogador para o baralho
			for (int i = 0; i < currentPlayer.getMao(n_mao).getCartas().size() - 1; i++) 
			{
				Carta c = currentPlayer.getMao(n_mao).getCartas().get(i);
				deck.AddCard(c);				
			}
			jogadores.get(turn).setMao(new ArrayList<Carta>(), n_mao);			
		}
		
		// Fecha a janela do jogador que saiu
		jogadoresInterface.get(turn).getInterface(n_mao).setVisible(false);
		jogadores.remove(turn);
		jogadoresInterface.remove(turn);
		
		// Se nao houver mais jogadores, encerra o programa
		if(jogadores.size() == 0)
		{
			JOptionPane.showMessageDialog(null,"Todos os jogadores sairam. Encerrando partida","Jogo",JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		// Passa o turno para o proximo jogador
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
			
			// Se algum jogadir poissui blackjack o dealer deve mostrar q tem ou nao o blackjack escondido
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
								JOptionPane.showMessageDialog(null,"Push para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
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
							// Caso 2: Jogador possui blackjack, mas dealer nao
							if(jogadores.get(i).getBlackjack(j) == true)
							{
								JOptionPane.showMessageDialog(null,"Blackjack para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
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
						JOptionPane.showMessageDialog(null,"Vitï¿½ria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
						rewardWinner(0, i, j);
					}
				}
				prizePool = 0;
				windowDealer.resetPrizePoolLabel();
				return;
			}
		}
		
		windowDealer.showHiddenCard();
		
		// Caso 4: Jogador tem blackjack e dealer nao
		for(int i = 0; i < jogadores.size(); i++)
		{
			for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
			{
				if(jogadores.get(i).getBlackjack(j) == true)
				{
					JOptionPane.showMessageDialog(null,"Blackjack para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
					rewardWinner(2.5f, i, j);
				}
			}
		}
		
		// Dealar saca ate ter n minimo 18 pontos
		while(dealer.getPontos(0) < 17)
		{
			Dealer_Hit();
		}
		
		// busca em cada jogador
		for(int i = 0; i < jogadores.size(); i++)
		{
			// busca em cada mao
			for(int j = 0; j < jogadores.get(i).getMaoQtd(); j++)
			{
				// busca uma mao que nao tenha saido e que nao possui blackjack
				if(jogadores.get(i).getOut(j) == false && jogadores.get(i).getBlackjack(j) == false)
				{
					// Se tiver mais pontos que o dealer ou o dealer estourou, a mao ganha
					if (jogadores.get(i).getPontos(j) > dealer.getPontos(0) || dealer.getPontos(0) > 21)
					{
						JOptionPane.showMessageDialog(null,"Vitoria ordinaria para o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
						rewardWinner(2, i, j);
					}
					// Se nao ela perde
					else
					{
						JOptionPane.showMessageDialog(null,"Vitoria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
						rewardWinner(0, i, j);
					}
				}
				else if(jogadores.get(i).getOut(j) == true)
				{
					// Caso tiver estourado, ele perde
					JOptionPane.showMessageDialog(null,"Vitoria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
					rewardWinner(0, i, j);
				}
			}
		}
		
		// Como todos os vencedores ja foram recompensados, zera a prizepool
		prizePool = 0;
		windowDealer.resetPrizePoolLabel();
		JOptionPane.showMessageDialog(null,"Fim da rodada","Jogo",JOptionPane.INFORMATION_MESSAGE);
	}
	
	//endregion
	
	//region Dealer
	
	private void DealerTurn()
	{
		// Verifica se existe algum jogador cuja pontuacao nao ultrapassou 21
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
		
		// Verifica a condicao de vitoria de cada jogador
		if (theresPossibleWinner == true)
		{
			DeclareWinner();
		}
		// Se nao houver possivel vendedor, passa por todas as maos de todos os jogadores e declara a derrota de todos
		else
		{
			for(int i = 0; i < jogadores.size(); i++)
			{
				for (int j = 0; j < jogadores.get(i).getMaoQtd(); j++) 
				{
					JOptionPane.showMessageDialog(null,"Vitoria do dealer contra o jogador " + String.valueOf(i + 1),"Jogo",JOptionPane.INFORMATION_MESSAGE);
					rewardWinner(0, i, j);
				}
				
			}
			// Reseta o prizepool no caso de nao haver vencedor
			prizePool = 0;
			windowDealer.resetPrizePoolLabel();
			JOptionPane.showMessageDialog(null,"Fim da rodada","Jogo",JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	private void rewardWinner(float modifier, int i, int mao_num) 
	{
		// Distribui a premiacao para a mao de cada jogador, sendo "i" o modificador do valor a receber
		// i = 0 caso seja derrota
		// i = 1 caso seja empate
		// i = 2 caso seja vitoria ordinaria do jogador
		// i = 2.5 caso seja vitoria do jogador com blackjack
		
		jogadores.get(i).setCreditos(modifier * jogadores.get(i).getTotalBet(mao_num) + jogadores.get(i).getCreditos());
		jogadores.get(i).setTotalBet(0, mao_num);
		jogadoresInterface.get(i).repaintInterfaces();
		
		// Se o jogador nao tiver creditos restando, ele sai da partida.
		if(jogadores.get(i).getCreditos() <= 0)
		{
			JOptionPane.showMessageDialog(null,"Jogador " + String.valueOf(i) + " estï¿½ fora da partida","Jogo",JOptionPane.INFORMATION_MESSAGE);
			for (int j = 0; j < jogadores.get(i).getMaoQtd(); j++) 
			{
				jogadoresInterface.get(i).getInterface(j).setVisible(false);
			}
			jogadores.remove(i);
			jogadoresInterface.remove(i);
		}
	}

	public void Dealer_Deal()
	{
		// Distribui as duas primeiras cartas para o dealer
		dealer.addCarta(deck.Draw(), 0);
		// Deixa a primeira carta escondida
		windowDealer.hideHiddenCard();
		dealer.addCarta(deck.Draw(), 0);
		dealer.setDealt(true);
		
		// Tira temporariamente o valor da carta escondida da pontuacao do dealer
		dealer.setPontos(dealer.getPontos(0) - dealer.getMao(0).getCartas().get(0).GetValue(), 0);
	}
	
	public void Dealer_Hit()
	{	
		// Compra uma nova carta para o dealer
		Carta topDeck = deck.Draw();
		dealer.addCarta(topDeck, 0);
		
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
		writer.write(String.valueOf(jogadores.size()) + "\n"); // Salva o nï¿½mero de jogadores
		
		// Salva as informacoes do dealer
		writer.write(String.valueOf(dealer.getMao(0).getCartas().size()) + "\n");
		writer.write(String.valueOf(windowDealer.getIsHidden()) + "\n");
		writer.write(String.valueOf(dealer.getPontos(0)) + "\n");
		for(int i = 0; i < dealer.getMao(0).getCartas().size(); i++)
		{
			writer.write(dealer.getMao(0).getCartas().get(i).GetSuite() + "\n");
			writer.write(String.valueOf(dealer.getMao(0).getCartas().get(i).GetValue()) + "\n");
			writer.write(dealer.getMao(0).getCartas().get(i).GetName() + "\n");
		}
		
		// Salva informacoes globais
		writer.write(String.valueOf(prizePool) + "\n");
		writer.write(String.valueOf(turn) + "\n");
		writer.write(String.valueOf(n_mao) + "\n");
		
		// Salva as informacoes dos jogadores
		for(int i = 0; i < jogadores.size(); i++)
		{
			// Salva a qtd de maos que o jogador tem, e a qtd de creditos
			writer.write(String.valueOf(jogadores.get(i).getMaoQtd()) + "\n");
			writer.write(String.valueOf(jogadores.get(i).getCreditos()) + "\n");
			
			// for para cada uma das maos dos jogadores
			for (int j = 0; j < jogadores.get(i).getMaoQtd(); j++) 
			{
				// Salva o tamanho de cada mao e quanto esta apostado nela
				writer.write(String.valueOf(jogadores.get(i).getMao(j).getCartas().size()) + "\n");
				writer.write(String.valueOf(jogadores.get(i).getTotalBet(j)) + "\n");
				
				// for que guarda individualmente cada carta
				for(int k = 0; k < jogadores.get(i).getMao(j).getCartas().size(); k++)
				{
					writer.write(jogadores.get(i).getMao(j).getCartas().get(k).GetSuite() + "\n");
					writer.write(String.valueOf(jogadores.get(i).getMao(j).getCartas().get(k).GetValue()) + "\n");
					writer.write(jogadores.get(i).getMao(j).getCartas().get(k).GetName() + "\n");
				}
				
				// Salva o estado dos botoes da interface da mao
				writer.write(String.valueOf(jogadoresInterface.get(i).getInterface(j).p.JButtonDeal.isEnabled()) + "\n");
				writer.write(String.valueOf(jogadoresInterface.get(i).getInterface(j).p.JButtonHit.isEnabled()) + "\n");
				writer.write(String.valueOf(jogadoresInterface.get(i).getInterface(j).p.JButtonSplit.isEnabled()) + "\n");
				writer.write(String.valueOf(jogadoresInterface.get(i).getInterface(j).p.JButtonSurrender.isEnabled()) + "\n");
				writer.write(String.valueOf(jogadoresInterface.get(i).getInterface(j).p.JButtonStand.isEnabled()) + "\n");
				writer.write(String.valueOf(jogadoresInterface.get(i).getInterface(j).p.JButtonDouble.isEnabled()) + "\n");
			}
		}

		
		writer.close();
	}
	
	public void LoadGame() throws FileNotFoundException
	{
		File loadFile = new File("partida.txt");
		Scanner reader = new Scanner(loadFile);
		
		int numPlayers = Integer.parseInt(reader.nextLine()); // Recupera o numero de jogadores e cria uma nova partida
		NewGame(numPlayers);
		
		// Recupera as informacoes do dealer e atualiza o jogo com elas
		int dealerHand = Integer.parseInt(reader.nextLine());
		windowDealer.setIsHiiden(Boolean.parseBoolean(reader.nextLine()));
		windowDealer.getSumLabel().setText("Soma das cartas = " + Integer.parseInt(reader.nextLine()));
		for(int i = 0; i < dealerHand; i++)
		{
			dealer.setDealt(true);
			Carta newCard = new Carta(reader.nextLine(), Integer.parseInt(reader.nextLine()), reader.nextLine());
			dealer.addCarta(newCard, 0);
		}
		
		prizePool = Float.parseFloat(reader.nextLine());
		windowDealer.getPrizePoolLabel().setText("Aposta total = " + String.valueOf(prizePool));
		turn = Integer.parseInt(reader.nextLine());
		n_mao = Integer.parseInt(reader.nextLine());
		
		// Recupera as informacoes dos jogadores e atualiza o jogo com elas
		// Loop de load dos jogadores
		for(int i = 0; i < numPlayers; i++)
		{
			int numMaos = Integer.parseInt(reader.nextLine());
			jogadores.get(i).setCreditos(Float.parseFloat(reader.nextLine()));

			// Loop de load das maos
			for (int j = 0; j < numMaos; j++) 
			{
				if (j > 0) 
				{
					jogadores.get(i).createNewHand();
				}
				
				// carrega informacoes geral de cada mao
				int handSize = Integer.parseInt(reader.nextLine());
				System.out.println(jogadores.get(i).getMaoQtd());
				jogadores.get(i).setTotalBet(Integer.parseInt(reader.nextLine()), j);
				
				// Loop de load das cartas
				for(int k = 0; k < handSize; k++)
				{
					jogadores.get(i).setDealt(true);
					Carta newCard = new Carta(reader.nextLine(), Integer.parseInt(reader.nextLine()), reader.nextLine());
					jogadores.get(i).getMao(j).addCarta(newCard);
				}
				
				// Cria uma nova interface para as maos geradas pelo split 
				if (j > 0) 
				{
					jogadoresInterface.get(i).addNewMao(jogadores.get(i), j, this);
					jogadoresInterface.get(i).getInterface(j).setVisible(true);
					jogadoresInterface.get(i).getInterface(j).setTitle("Jogador " + String.valueOf(i+1) + " Mao " + String.valueOf(j+1));
				}
				
				// e atualiza seus labels
				jogadoresInterface.get(i).getInterface(j).p.JLabelBet.setText("Aposta = " + String.valueOf(currentPlayer.getTotalBet(j)));
				jogadoresInterface.get(i).getInterface(j).p.JLabelCredits.setText("Creditos = " + String.valueOf(currentPlayer.getCreditos()));

				// Carrega o estado dos botoes da interface da mao
				jogadoresInterface.get(i).getInterface(j).p.JButtonDeal.setEnabled(Boolean.parseBoolean(reader.nextLine()));
				jogadoresInterface.get(i).getInterface(j).p.JButtonHit.setEnabled(Boolean.parseBoolean(reader.nextLine()));
				jogadoresInterface.get(i).getInterface(j).p.JButtonSplit.setEnabled(Boolean.parseBoolean(reader.nextLine()));
				jogadoresInterface.get(i).getInterface(j).p.JButtonSurrender.setEnabled(Boolean.parseBoolean(reader.nextLine()));
				jogadoresInterface.get(i).getInterface(j).p.JButtonStand.setEnabled(Boolean.parseBoolean(reader.nextLine()));
				jogadoresInterface.get(i).getInterface(j).p.JButtonDouble.setEnabled(Boolean.parseBoolean(reader.nextLine()));
			}
			
			
			
		}
		
		// Atualiza as janelas do jogo
		RepaintAll();
		JOptionPane.showMessageDialog(null,"Partida carregada","Jogo",JOptionPane.INFORMATION_MESSAGE);
	}
	
	//endregion
}
