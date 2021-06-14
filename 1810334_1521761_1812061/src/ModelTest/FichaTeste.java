// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package ModelTest;

import Model.Ficha;
import static org.junit.Assert.*;

import org.junit.Test;

public class FichaTeste {

	@Test
	public void TestaCria() 
	{
		Ficha f = new Ficha(100, 2);
		
		assertSame(2, f.getQuantidade());
		assertSame(100, f.getValor());
	}

	@Test
	public void TestaManipula() 
	{
		Ficha f = new Ficha(100, 2);
		
		int qtd = f.getQuantidade();
		f.setQuantidade(10);
		assertNotSame(qtd, f.getQuantidade());
		
		int val = f.getValor();
		f.setValor(100);
		assertSame(val, f.getValor());
	}
}
