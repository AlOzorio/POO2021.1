package Model;

public class Deck 
{

	private Pilha<Carta> baralho;
	private String[] naipe = new String[] {"Copas", "Ouros", "Paus", "Espadas"};
	private int[] valor = new int[] {2,3,4,5,6,7,8,9};
	private String[] nome = new String[] {"dois","tres","quatro","cinco","seis","sete","oito","nove"};
	
	public Deck()
	{
		this.baralho = new Pilha<Carta>();
	}
	
	public void IniciaBaralho()
	{
		Carta c;
		int i, j, k;
		
		for(k = 0; k < 4; k++)
		{
			for(i = 0; i < 4; i++)
			{
				c = new Carta(naipe[i], 10, "dez");
				baralho.Push(c);
				c = new Carta(naipe[i], 10, "valete");
				baralho.Push(c);
				c = new Carta(naipe[i], 10, "dama");
				baralho.Push(c);
				c = new Carta(naipe[i], 10, "rei");
				baralho.Push(c);
				c = new Carta(naipe[i], 11, "as");
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
	
	public int NumCartas()
	{
		return baralho.GetSize();
	}
	
	public Carta Draw()
	{
		if(this.NumCartas() == 187)
		{
			this.Embaralhar();
		}
		
		return (Carta) baralho.Pop();
	}
	
	public void AddCard(Carta c)
	{
		baralho.Push(c);
	}
}