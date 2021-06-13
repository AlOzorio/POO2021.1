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
		GM.NewGame(0);
		
		assertNotNull(GM.deck);

		assertNotNull(GM.currentPlayer);
		assertNotNull(GM.turn);
		assertNull(GM.winner);
	}
	
	@Test
	public void TestDeal() 
	{
		GM.NewGame(0);
		
		GM.Deal();
		assertNotSame(0,GM.turn);
		assertNotSame(GM.jogadores.get(0),GM.currentPlayer);
		
	}
	
	@Test
	public void TestStand() 
	{
		GM.NewGame(0);
		
		GM.Stand();
		assertNotSame(0,GM.turn);
		assertNotSame(GM.jogadores.get(0),GM.currentPlayer);

	}
	
	@Test
	public void TestHIt() 
	{
		GM.NewGame(0);
		
		ArrayList<Carta> mao = (ArrayList<Carta>) GM.currentPlayer.getMao().clone();
		GM.Hit();
		assertNotSame(mao,GM.currentPlayer.getMao());
	}
	
	@Test
	public void TestClear() 
	{
		GM.NewGame(0);
		
		Carta c = GM.deck.Draw();
		GM.deck.AddCard(c);
		GM.Clear();
		Carta c2 = GM.deck.Draw();
		assertNotSame(c,c2);
	}

	@Test
	public void TestQuit() 
	{
		GM.NewGame(0);
		
		ArrayList<Jogador> j = (ArrayList<Jogador>) GM.jogadores.clone();
		GM.Quit();
		assertNotSame(j,GM.jogadores);
	}
}
