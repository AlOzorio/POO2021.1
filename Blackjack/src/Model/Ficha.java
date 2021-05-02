package Model;

public class Ficha {

	private int valor;
	private int quantidade;
	
	public Ficha(int valor, int quantidade)
	{
		this.valor = valor;
		this.quantidade = quantidade;
	}
	
	public int getValor()
	{
		return valor;
	}
	
	public void setValor(int valor) 
	{
		this.valor = valor;
	}
	
	
	public int getQuantidade() 
	{
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) 
	{
		this.quantidade = quantidade;
	}
}

