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
		// Contrutor da mao, com alguns atributos necessarios
		this.cartas = new ArrayList<Carta>();
		this.pontos = 0;
		this.totalBet = 0;
		this.out = false;
	}
	
	public void addCarta(Carta carta)
	{
		// Adicioona a carta recebida na mao, atualizando o valor da soma
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
		// Retorna a lista de cartas da mao do jogador 
		return cartas;
	}
	
	public void setMao(ArrayList<Carta> mao) 
	{
		// Coloca na lista de cartas o array recebido, principalmente utilizado para limpar a mão
		this.cartas = mao;
	}
	
	public int getPontos() 
	{
		// Retorna a soma das cartas
		return pontos;
	}
	
	public void setPontos(int pontos)
	{
		// Coloca na pontuação da soma das cartas o valor recebido
		this.pontos = pontos;
	}
	
	public int getTotalBet()
	{
		// Retorna o valor total da aposta dessa mao
		return this.totalBet;
	}
	
	public void setTotalBet(int valor)
	{
		// Coloca no valor da aposta o valor recebido
		this.totalBet = valor;
	}
	
	public void addToBet(int valor)
	{
		// Adiciona o valor recebido na aposta dessa mao
		this.totalBet += valor;
	}
	
	public boolean getOut()
	{
		// Retorna o booleano que diz se essa mao do jogador estouorou ou desistiu
		return this.out;
	}
	
	public void setOut(Boolean valor)
	{
		// Modifica o valor do booleano setOut
		this.out = valor;
	}

	public boolean getBlackjack() 
	{
		// Retorna se a mao do jogador possua um blackjack
		if(this.cartas.size() == 2 && this.getPontos() == 21)
		{
			return true;
		}
		
		return false;
	}
	
}
