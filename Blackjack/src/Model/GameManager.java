package Model;

public class GameManager 
{
	public void SecondShuffle(Deck d)
	{
		int NumCartas = d.NumCartas();
		
		if(NumCartas <= 187)
		{
			d.Embaralhar();
		}
	}
}
