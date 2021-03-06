// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package Model;

public class Deck 
{

	private Pilha<Carta> baralho; 
	private String[] naipe = new String[] {"h_Copas", "d_Ouros", "c_Paus", "s_Espadas"};
	private int[] valor = new int[] {2,3,4,5,6,7,8,9};
	private String[] nome = new String[] {"dois","tres","quatro","cinco","seis","sete","oito","nove"};
	
	// Inicializa a pilha contendo as cartas
	public Deck()
	{
		this.baralho = new Pilha<Carta>();
	}
	
	// Cria e empilha as cartas 
	public void IniciaBaralho()
	{
		Carta c;
		int i, j, k;
		
		for(k = 0; k < 4; k++)
		{
			for(i = 0; i < 4; i++)
			{
				c = new Carta(naipe[i], 10, "t_dez");
				baralho.Push(c);
				c = new Carta(naipe[i], 10, "j_valete");
				baralho.Push(c);
				c = new Carta(naipe[i], 10, "q_dama");
				baralho.Push(c);
				c = new Carta(naipe[i], 10, "k_rei");
				baralho.Push(c);
				c = new Carta(naipe[i], 11, "a_as");
				baralho.Push(c);
			}	
			
			for(i = 0; i < 4; i++)
			{
				for(j = 0; j < 8; j++)
				{
					c = new Carta(naipe[i], valor[j], nome[j]);
					baralho.Push(c);
				}
			}
		}
	}
	
	public void Embaralhar()
	{
		baralho.Shuffle();
	}
	
	// Obtem o n?mero de cartas que ainda est?o no deck
	public int NumCartas()
	{
		return baralho.GetSize();
	}
	
	// Compra uma carta do baralho
	public Carta Draw()
	{
		if(this.NumCartas() == 187)
		{
			this.Embaralhar();
		}
		
		return (Carta) baralho.Pop();
	}
	
	// Adiciona uma carta ao topo do baralho
	public void AddCard(Carta c)
	{
		baralho.Push(c);
	}
}