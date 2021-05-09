package Model;

import java.util.ArrayList;

public class Jogador 
{

	private int id;
	private String nome;
	private ArrayList<Carta> mao;
	private ArrayList<Ficha> ficha;
	private int pontos;
	private int totalBet;
	private boolean dealt;
	
	public Jogador()
	{
		this.pontos = 0;
		this.id = (int) System.currentTimeMillis() % 1000;
		this.mao = new ArrayList<Carta>();
		this.ficha = new ArrayList<Ficha>();
		this.totalBet = 0;
		this.dealt = false;
	}
	
	public void addCarta(Carta carta)
	{
		if(carta.GetValue() == 11 && carta.GetValue() + this.pontos > 21)
		{
			carta.SetValue(1);
		}
		this.pontos += carta.GetValue();
		this.mao.add(carta);
	}
	
	public void criarFichas()
	{
		Ficha f;
		f = new Ficha(100, 2);
		ficha.add(f);
		f = new Ficha(50, 2);
		ficha.add(f);
		f = new Ficha(20, 5);
		ficha.add(f);
		f = new Ficha(10, 5);
		ficha.add(f);
		f = new Ficha(5, 8);
		ficha.add(f);
		f = new Ficha(1, 10);
		ficha.add(f);
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	public ArrayList<Carta> getMao()
	{
		return mao;
	}
	
	public void setMao(ArrayList<Carta> mao) 
	{
		this.mao = mao;
	}
	
	public int getPontos() 
	{
		return pontos;
	}
	
	public void setPontos(int pontos)
	{
		this.pontos = pontos;
	}
	
	public ArrayList<Ficha> getFicha()
	{
		return ficha;
	}
	
	public void setFicha(ArrayList<Ficha> ficha)
	{
		this.ficha = ficha;
	}
	
	public int getTotalBet()
	{
		return this.totalBet;
	}
	
	public void setTotalBet(int valor)
	{
		this.totalBet = valor;
	}
	
	public boolean getDealt()
	{
		return this.dealt;
	}
	
	public void setDealt(Boolean valor)
	{
		this.dealt = valor;
	}
}
