package ModelTest;

import Model.Deck;
import Model.Carta;
import static org.junit.Assert.*;

import org.junit.Test;

public class DeckTeste {

	@Test
	public void TesteInica() 
	{
		Deck d = new Deck();
		
		assertNotNull("Memoria vazia", d);
		
		d.IniciaBaralho();
		
		int numCartas = d.NumCartas();
		assertEquals(208,numCartas);		
		
	}

	@Test
	public void TesteManuipula()
	{
		Deck d = new Deck();
		d.IniciaBaralho();
		
		Carta c = d.Draw();
		Carta teste = new Carta("Espadas", 9, "nove");
		
		assertEquals(teste.GetValue(),c.GetValue());
		assertEquals(teste.GetSuite(),c.GetSuite());
		assertEquals(teste.GetName(),c.GetName());
		
		/*--------------------------------------*/
		
		d.AddCard(c);
		d.Embaralhar();
		Carta newCard = d.Draw();
		assertNotEquals(c.GetValue(),newCard.GetValue());
	}
}
