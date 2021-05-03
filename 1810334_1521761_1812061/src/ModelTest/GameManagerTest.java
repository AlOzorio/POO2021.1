package ModelTest;

import Model.Jogador;
import Model.Carta;
import Model.Ficha;
import Model.GameManager;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class GameManagerTest 
{
	private GameManager GM = new GameManager();
	@Test
	public void TestGM() 
	{
		GM.NewGame();
		
		assertNotNull(GM.deck);
		assertNotNull(GM.prizePool.get(0));
		assertNotNull(GM.currentPlayer);
		assertNotNull(GM.turn);
		assertNull(GM.winner);
	}
	
	@Test
	public void TestDeal() 
	{
		GM.NewGame();
		
		GM.Deal();
		assertNotSame(0,GM.turn);
		assertNotSame(GM.jogadores.get(0),GM.currentPlayer);
		
	}
	
	@Test
	public void TestStand() 
	{
		GM.NewGame();
		
		GM.Stand();
		assertNotSame(0,GM.turn);
		assertNotSame(GM.jogadores.get(0),GM.currentPlayer);

	}
	
	@Test
	public void TestHIt() 
	{
		GM.NewGame();
		
		ArrayList<Carta> mao = (ArrayList<Carta>) GM.currentPlayer.getMao().clone();
		GM.Hit();
		assertNotSame(mao,GM.currentPlayer.getMao());
	}
	
	@Test
	public void TestPP() 
	{
		GM.NewGame();
		
		ArrayList<Ficha> pp = (ArrayList<Ficha>) GM.prizePool.clone();
		GM.AddToPrizePool(50);
		assertNotSame(pp,GM.prizePool);
	}
	
	@Test
	public void TestClear() 
	{
		GM.NewGame();
		
		Carta c = GM.deck.Draw();
		GM.deck.AddCard(c);
		GM.Clear();
		Carta c2 = GM.deck.Draw();
		assertNotSame(c,c2);
	}

	@Test
	public void TestQuit() 
	{
		GM.NewGame();
		
		ArrayList<Jogador> j = (ArrayList<Jogador>) GM.jogadores.clone();
		GM.Quit();
		assertNotSame(j,GM.jogadores);
	}
}
