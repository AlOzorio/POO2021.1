package Model;

import java.util.ArrayList;

public class Mao 
{
	private ArrayList<Carta> cartas;
	private int pontos;
	private int totalBet;
	private Boolean out;
	
	public Mao()
	{
		this.cartas = new ArrayList<Carta>();
		this.pontos = 0;
		this.totalBet = 0;
		this.out = false;
	}
	
	public void addCarta(Carta carta)
	{
		this.cartas.add(carta);
		this.pontos += carta.GetValue();
		if(this.pontos > 21)
		{
			for (int i = 0; i < this.cartas.size(); i++) {
				if (cartas.get(i).GetValue() == 11 ) {
					cartas.get(i).SetValue(1);
					this.pontos -= 10;
					break;
				}
			}
		}
	}
	
	public ArrayList<Carta> getCartas()
	{
		return cartas;
	}
	
	public void setMao(ArrayList<Carta> mao) 
	{
		this.cartas = mao;
	}
	
	public int getPontos() 
	{
		return pontos;
	}
	
	public void setPontos(int pontos)
	{
		this.pontos = pontos;
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
		if(this.cartas.size() == 2 && this.getPontos() == 21)
		{
			return true;
		}
		
		return false;
	}
	
}
