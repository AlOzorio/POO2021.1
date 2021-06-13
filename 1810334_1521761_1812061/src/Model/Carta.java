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

	public void SetName(String nome)
	{
		this.nome = nome;
	}
	
	public String GetSuite()
	{
		return this.naipe;
	}

	public void SetSuite(String naipe)
	{
		this.naipe = naipe;
	}
	
	public String GetIndex()
	{
		if (this.valor < 10 && this.valor > 1) {
			return String.valueOf(this.valor) + this.naipe.charAt(0);
		}
		return String.valueOf(this.nome.charAt(0)) + this.naipe.charAt(0) ;
	}

	public int GetValue()
	{
		return this.valor;
	}
	
	public void SetValue(int newValue)
	{
		this.valor = newValue;
	}

}
