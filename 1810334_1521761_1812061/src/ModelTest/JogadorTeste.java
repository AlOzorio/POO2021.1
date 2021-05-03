package ModelTest;

import Model.Jogador;
import static org.junit.Assert.*;

import org.junit.Test;

public class JogadorTeste 
{

	@Test
	public void TestaCria() 
	{
		Jogador j = new Jogador();
		j.criarFichas();
		
		assertNull(j.getNome());
		assertNotNull(j.getMao());
		assertNotNull(j.getFicha());
		assertEquals(0, j.getPontos());
		assertEquals(0, j.getTotalBet());
		assertFalse(j.getDealt());
	}

	public void TestaMAnipula() 
	{
		Jogador j = new Jogador();
		j.criarFichas();
		
		j.setNome("Joao");
		j.setDealt(true);
		j.setFicha(null);
		j.setMao(null);
		j.setPontos(1000);
		j.setTotalBet(1);
		
		assertEquals("João",j.getNome());
		assertNull(j.getMao());
		assertNull(j.getFicha());
		assertEquals(1000, j.getPontos());
		assertEquals(1, j.getTotalBet());
		assertTrue(j.getDealt());
	}
}
