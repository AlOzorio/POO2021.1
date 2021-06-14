// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
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
		this.maos.add(new Mao());
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
	
	public Mao getMao(int i)
	{
		return this.maos.get(i);
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

	public void split() {
		// TODO Auto-generated method stub
		this.maos.add(new Mao());
		
		this.getMao(1).getCartas().add(this.getMao(0).getCartas().get(1));
		this.getMao(0).getCartas().remove(1);
		
		this.maos.get(0).setPontos(this.maos.get(0).getPontos()/2);
		this.maos.get(1).setPontos(this.maos.get(0).getPontos());
		
		this.setCreditos(this.getCreditos() - this.maos.get(0).getTotalBet());
		this.maos.get(1).setTotalBet(this.maos.get(0).getTotalBet());
	}
	
	public void createNewHand()
	{
		this.maos.add(new Mao());
	}

	public void removeMao(int i) {
		// TODO Auto-generated method stub
		this.maos.remove(i);
	}
}

