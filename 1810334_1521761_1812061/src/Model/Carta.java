// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package Model;

public class Carta 
{
	private String naipe;
	private int valor;
	private String nome;
	
	// Inicaliza��o do objeto carta
	public Carta(String cardSuite, int cardValue, String cardName)
	{
		this.naipe = cardSuite;
		this.valor = cardValue;
		this.nome = cardName;
	}
	
	
	// Fun��es para obter e mudar o nome das cartas
	public String GetName()
	{
		return this.nome;
	}

	public void SetName(String nome)
	{
		this.nome = nome;
	}
	
	
	// Fun��es para obter e mudar o naipe das cartas
	public String GetSuite()
	{
		return this.naipe;
	}

	public void SetSuite(String naipe)
	{
		this.naipe = naipe;
	}
	
	// Obtem o nome da carta para encontrar a imagem referente
	public String GetIndex()
	{
		if (this.valor < 10 && this.valor > 1) {
			return String.valueOf(this.valor) + this.naipe.charAt(0);
		}
		return String.valueOf(this.nome.charAt(0)) + this.naipe.charAt(0) ;
	}

	// Fun��es para manipular o valor da carta
	public int GetValue()
	{
		return this.valor;
	}
	
	public void SetValue(int newValue)
	{
		this.valor = newValue;
	}

}
