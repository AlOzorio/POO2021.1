// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package ModelTest;

import Model.Carta;
import static org.junit.Assert.*;

import org.junit.Test;

public class CartaTeste {

	@Test
	public void CriaCartaTeste() 
	{
		Carta c = new Carta("copas",10,"dez");
		
		assertNotNull("Valor vazio", c.GetValue());
		assertNotNull("Valor vazio", c.GetSuite());
		assertNotNull("Valor vazio", c.GetName());
	}
	
	@Test
	public void TestMudaValor()
	{
		Carta c = new Carta("copas",10,"dez");
		
		c.SetValue(1);
		
		assertSame("Valor errado", 1, c.GetValue());
	}

}
