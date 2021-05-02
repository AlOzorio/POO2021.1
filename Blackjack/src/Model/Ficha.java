package Model;

public class Ficha {

	private String nome;
	private int valor;
	private int quantidade;
	
	public Ficha(String nome, int valor, int quantidade)
	{
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
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

