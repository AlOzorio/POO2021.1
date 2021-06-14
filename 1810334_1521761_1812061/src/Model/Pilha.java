// Arthur de Lima Ozorio - 1810334
// Natalia ferreira Lima - 1521761
// Pedro Henrique Pereira Guedes - 1812061
package Model;

import java.util.*;

public class Pilha<T>
{
	private ArrayList<T> obj = new ArrayList<T>();
	
	// Inicializa a pilha que será utilizada para o baralho
	public Pilha()
	{
		obj = new ArrayList<T>();
	}
	
	// Realiza operações básicas de pilha
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