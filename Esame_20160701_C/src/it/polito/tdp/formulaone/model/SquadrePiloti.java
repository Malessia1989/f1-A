package it.polito.tdp.formulaone.model;

public class SquadrePiloti {
	
	private Driver p1;
	private Driver p2;
	private double peso;
	

	public SquadrePiloti(Driver p1, Driver p2, double peso) {
		
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SquadrePiloti other = (SquadrePiloti) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		return true;
	}
	public Driver getP1() {
		return p1;
	}
	public void setP1(Driver p1) {
		this.p1 = p1;
	}
	public Driver getP2() {
		return p2;
	}
	public void setP2(Driver p2) {
		this.p2 = p2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}

	
}
