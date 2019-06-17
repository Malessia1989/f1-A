package it.polito.tdp.borders.model;

public class Country {
	
	private String abb;
	private int id;
	private String nome;
	public Country(String abb, int id, String nome) {
		super();
		this.abb = abb;
		this.id = id;
		this.nome = nome;
	}
	public String getAbb() {
		return abb;
	}
	public void setAbb(String abb) {
		this.abb = abb;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Country other = (Country) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Country [abb=" + abb + ", id=" + id + ", nome=" + nome + "]";
	}

	

}
