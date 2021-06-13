package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Jogador 
{

	private String nome;
	private float creditos;
	private boolean dealt;
	private ArrayList<Mao> maos;
	
	public Jogador()
	{
		this.nome = null;
		this.creditos = 500;
		this.dealt = false;
		this.maos = new ArrayList<Mao>();
	}
	
	public int getMaoQtd()
	{
		return maos.size();
	}
	
	public void addCarta(Carta carta, int i)
	{
		this.maos.get(i).addCarta(carta);
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	public ArrayList<Carta> getMao(int i)
	{
		return this.maos.get(i).getMao();
	}
	
	public void setMao(ArrayList<Carta> mao, int i) 
	{
		this.maos.get(i).setMao(mao);
	}
	
	public int getPontos(int i) 
	{
		return this.maos.get(i).getPontos();
	}
	
	public void setPontos(int pontos, int i)
	{
		this.maos.get(i).setPontos(pontos);
	}
	
	public float getCreditos()
	{
		return creditos;
	}
	
	public void setCreditos(float credito)
	{
		this.creditos = credito;
	}
	
	public int getTotalBet(int i)
	{
		return this.maos.get(i).getTotalBet();
	}
	
	public void setTotalBet(int valor, int i)
	{
		this.maos.get(i).setTotalBet(valor);
	}
	
	public void addToBet(int valor, int i)
	{
		this.maos.get(i).addToBet(valor);
	}
	
	public boolean getDealt()
	{
		return this.dealt;
	}
	
	public void setDealt(Boolean valor)
	{
		this.dealt = valor;
	}
	
	public boolean getOut(int i)
	{
		return this.maos.get(i).getOut();
	}
	
	public void setOut(Boolean valor, int i)
	{
		this.maos.get(i).setOut(valor); 
	}

	public boolean getBlackjack(int i) 
	{
		return this.maos.get(i).getBlackjack();
	}
}

