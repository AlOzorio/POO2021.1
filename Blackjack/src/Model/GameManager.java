package Model;

public class GameManager 
{
	public void SecondShuffle(Deck d)
	{
		int NumCartas = d.NumCartas();
		
		if(NumCartas <= 47)
		{
			d.Embaralhar();
		}
	}
}
