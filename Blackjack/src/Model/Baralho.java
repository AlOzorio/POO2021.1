package Model;

import java.util.*;

public class Baralho<T>
{
	private ArrayList<T> cartas = new ArrayList<T>();
	
	public Baralho()
	{
		cartas = new ArrayList<T>();
	}
	
	public void Push(T t)
	{
		this.cartas.add(t);
	}
	
	public Object Pop()
	{
		return this.cartas.remove(this.cartas.size()-1);
	}
	
	
	//Funções utilizadas para realizar o embaralhamento
	public void Shuffle()
	{
		Collections.shuffle(cartas);
	}
	
	public int GetSize()
	{
		return cartas.size();
	}
}