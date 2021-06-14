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
		// funcao que retorna a quantidade de maos que o jogador tem
		return maos.size();
	}
	
	public void addCarta(Carta carta, int i)
	{
		// funcao wrapper pra adiconar uma carta na lista de cartas da mao de indice "i"
		this.maos.get(i).addCarta(carta);
	}
	
	// Funcoes de nome implementadas e nao utilizadas
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
		// funcao wrapper pra buscar a lista de cartas da mao de indice "i"
		return this.maos.get(i);
	}
	
	public void setMao(ArrayList<Carta> mao, int i) 
	{
		// funcao wrapper pra mudar a lista de cartas na mao de indice "i"
		this.maos.get(i).setMao(mao);
	}
	
	public int getPontos(int i) 
	{
		// funcao wrapper par ver se a pontuacao da mao de indice "i"
		return this.maos.get(i).getPontos();
	}
	
	public void setPontos(int pontos, int i)
	{
		// funcao wrapper pra mudar o valor da pontuacao para o valor "valor" na mao de indice "i"
		this.maos.get(i).setPontos(pontos);
	}
	
	// Funcaoes para manipular os creditos do jogador
	public float getCreditos()
	{
		return creditos;
	}
	
	public void setCreditos(float credito)
	{
		this.creditos = credito;
	}
	//
	
	public int getTotalBet(int i)
	{
		// funcao wrapper par ver a aposta da mao de indice "i"
		return this.maos.get(i).getTotalBet();
	}
	
	public void setTotalBet(int valor, int i)
	{
		// funcao wrapper pra mudar o valor da aposta para o valor "valor" na mao de indice "i"
		this.maos.get(i).setTotalBet(valor);
	}
	
	public void addToBet(int valor, int i)
	{
		// funcao wrapper pra mudar adicionar o valor "valor" na aposta da mao de indice "i"
		this.maos.get(i).addToBet(valor);
	}
	
	public boolean getDealt()
	{
		// funcao wrapper par ver se a mao de indice "i" ja deu deal
		return this.dealt;
	}
	
	public void setDealt(Boolean valor)
	{
		// funcao wrapper pra mudar o valor booleano de dealt da mao de indice "i"
		this.dealt = valor;
	}
	
	public boolean getOut(int i)
	{
		// funcao wrapper pra ver se a mao de indice "i" saiu da partida
		return this.maos.get(i).getOut();
	}
	
	public void setOut(Boolean valor, int i)
	{
		// funcao wrapper pra mudar o valor booleano de saida da partida da mao de indice "i"
		this.maos.get(i).setOut(valor); 
	}

	public boolean getBlackjack(int i) 
	{
		// funcao wrapper par ver se a mao de indice "i" possui blackjack
		return this.maos.get(i).getBlackjack();
	}

	public void split() {
		// funcao usada no split para criar uma mao com a segunda carta da mao principal
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
		// funcao usada no loadgame para criar uma mao sem carta nenhuma
		this.maos.add(new Mao());
	}

	public void removeMao(int i) {
		// Deleta a mao
		this.maos.remove(i);
	}
}

