package it.polito.tdp.newufosightings.model;

public class StatoePeso {
	
	private State s;
	private  double peso;
	public StatoePeso(State s, double peso) {
		super();
		this.s = s;
		this.peso = peso;
	}
	public State getS() {
		return s;
	}
	public void setS(State s) {
		this.s = s;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	
}
