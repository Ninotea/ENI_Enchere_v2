package fr.eni.enchere.bo;

public class Categorie {
	private int id;
	private String libelle;
	
	public Categorie(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}
	public Categorie(String libelle) {
		super();
		this.libelle = libelle;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
}
