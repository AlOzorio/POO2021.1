package Model;

import java.util.ArrayList;

public class Jogador {

	private int id;
	private String nome;
	private ArrayList<Carta> mao;
	private ArrayList<Ficha> ficha;
	private int pontos;
	
	public Jogador(){
		this.pontos = 0;
		this.id = (int) System.currentTimeMillis() % 1000;
		this.mao = new ArrayList<Carta>();
		this.ficha = new ArrayList<Ficha>();
	}
	
	public void addCarta(Carta carta){
		this.pontos += carta.GetValue();
		this.mao.add(carta);
	}
	
	public void criarFichas() {
		Ficha f;
		f = new Ficha("$100", 100, 2);
		ficha.add(f);
		f = new Ficha("$50", 50, 2);
		ficha.add(f);
		f = new Ficha("$20", 20, 5);
		ficha.add(f);
		f = new Ficha("$10", 10, 5);
		ficha.add(f);
		f = new Ficha("$5", 5, 8);
		ficha.add(f);
		f = new Ficha("$1", 1, 10);
		ficha.add(f);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public ArrayList<Carta> getMao() {
		return mao;
	}
	
	public void setMao(ArrayList<Carta> mao) {
		this.mao = mao;
	}
	
	public int getPontos() {
		return pontos;
	}
	
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	public ArrayList<Ficha> getFicha(){
		return ficha;
	}
	
	public void setFicha(ArrayList<Ficha> ficha) {
		this.ficha = ficha;
	}
}

