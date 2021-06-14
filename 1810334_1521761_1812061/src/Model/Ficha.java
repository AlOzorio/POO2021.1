package Model;

public class Ficha {

	private int valor;
	private int quantidade;
	
	// Inicializa o objeto Ficha
	public Ficha(int valor, int quantidade)
	{
		this.valor = valor;
		this.quantidade = quantidade;
	}
	
	// Funções para manipular o valor das fichas
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

