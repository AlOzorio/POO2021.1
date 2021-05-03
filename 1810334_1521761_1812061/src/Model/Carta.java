package Model;

public class Carta 
{
	private String naipe;
	private int valor;
	private String nome;
	
	public Carta(String cardSuite, int cardValue, String cardName)
	{
		this.naipe = cardSuite;
		this.valor = cardValue;
		this.nome = cardName;
	}
	
	public String GetName()
	{
		return this.nome;
	}

	public String GetSuite()
	{
		return this.naipe;
	}

	
	//As duas funções abaixo servem para alterar o valor do as, quando necessário
	public int GetValue()
	{
		return this.valor;
	}
	
	public void SetValue(int newValue)
	{
		this.valor = newValue;
	}
}
