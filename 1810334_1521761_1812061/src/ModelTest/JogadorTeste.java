// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
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
		
		assertNull(j.getNome());
		//assertNotNull(j.getMao());
		assertNotNull(j.getCreditos());
		//assertEquals(0, j.getPontos());
		//assertEquals(0, j.getTotalBet());
		assertFalse(j.getDealt());
	}

	public void TestaMAnipula() 
	{
		Jogador j = new Jogador();
		
		j.setNome("Joao");
		j.setDealt(true);
		j.setCreditos(0);
		//j.setMao(null);
		//j.setPontos(1000);
		//j.setTotalBet(1);
		
		assertEquals("João",j.getNome());
		//assertNull(j.getMao());
		//assertEquals(0, j.getCreditos());
		//assertEquals(1000, j.getPontos());
		//assertEquals(1, j.getTotalBet());
		assertTrue(j.getDealt());
	}
}
