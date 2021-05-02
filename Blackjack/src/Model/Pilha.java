package Model;

import java.util.*;

public class Pilha<T>
{
	private ArrayList<T> obj = new ArrayList<T>();
	
	public Pilha()
	{
		obj = new ArrayList<T>();
	}
	
	public void Push(T t)
	{
		this.obj.add(t);
	}
	
	public Object Pop()
	{
		return this.obj.remove(this.obj.size()-1);
	}
	
	
	//Funções utilizadas para realizar o embaralhamento
	public void Shuffle()
	{
		Collections.shuffle(obj);
	}
	
	public int GetSize()
	{
		return obj.size();
	}
}