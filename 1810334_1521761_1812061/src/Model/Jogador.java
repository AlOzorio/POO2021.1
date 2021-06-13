package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Jogador 
{

	private String nome;
	private ArrayList<Carta> mao;
	private float creditos;
	private int pontos;
	private int totalBet;
	private boolean dealt;
	private boolean out;
	
	public Jogador()
	{
		this.nome = null;
		this.pontos = 0;
		this.mao = new ArrayList<Carta>();
		this.creditos = 500;
		this.totalBet = 0;
		this.dealt = false;
		this.out = false;
	}
	
	public void addCarta(Carta carta)
	{

		this.mao.add(carta);
		this.pontos += carta.GetValue();
		if(this.pontos > 21)
		{
			for (int i = 0; i < this.mao.size(); i++) {
				if (mao.get(i).GetValue() == 11 ) {
					mao.get(i).SetValue(1);
					this.pontos -= 10;
					break;
				}
			}
		}
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
	
	public float getCreditos()
	{
		return creditos;
	}
	
	public void setCreditos(float credito)
	{
		this.creditos = credito;
	}
	
	public int getTotalBet()
	{
		return this.totalBet;
	}
	
	public void setTotalBet(int valor)
	{
		this.totalBet = valor;
	}
	
	public void addToBet(int valor)
	{
		this.totalBet += valor;
	}
	
	public boolean getDealt()
	{
		return this.dealt;
	}
	
	public void setDealt(Boolean valor)
	{
		this.dealt = valor;
	}
	
	public boolean getOut()
	{
		return this.out;
	}
	
	public void setOut(Boolean valor)
	{
		this.out = valor;
	}

	public boolean getBlackjack() 
	{
		if(this.mao.size() == 2 && this.getPontos() == 21)
		{
			return true;
		}
		
		return false;
	}
}

