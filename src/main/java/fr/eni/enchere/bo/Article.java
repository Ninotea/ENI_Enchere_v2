package fr.eni.enchere.bo;

import java.util.Date;

public class Article {

	//Attributs d'instance
	private Integer noArticle;
	private String nomArticle;
	private String description;
	private String categorie;
	private int noCategorie;
	private Date dateDebutEnchere;
	private Date dateFinEnchere;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private Utilisateur vendeur;
	private int no_vendeur;
	
	
	//Constructeur
	
	public Article() {
		super();
	}
	
	
	// Constructeur utilisant tous les champs et Vendeur de type Utilsiateur
	public Article(Integer noArticle, String nomArticle, String description, String categorie, int noCategorie,
			Date dateDebutEnchere, Date dateFinEnchere, int miseAPrix, int prixVente, String etatVente,
			Utilisateur vendeur) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.categorie = categorie;
		this.noCategorie = noCategorie;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.vendeur = vendeur;
	}
	
	// Constructeur utilisant tous les champs et no_Vendeur de type int
	public Article(Integer noArticle, String nomArticle, String description, String categorie, int noCategorie,
			Date dateDebutEnchere, Date dateFinEnchere, int miseAPrix, int prixVente,int no_vendeur) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.categorie = categorie;
		this.noCategorie = noCategorie;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.no_vendeur = no_vendeur;
	}

	public int getNo_vendeur() {
		return no_vendeur;
	}

	public void setNo_vendeur(int no_vendeur) {
		this.no_vendeur = no_vendeur;
	}

	public int getNoCategorie() {
		return noCategorie;
	}
	
	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Integer getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateDebutEnchere() {
		return dateDebutEnchere;
	}
	public void setDateDebutEnchere(Date dateDebutEnchere) {
		this.dateDebutEnchere = dateDebutEnchere;
	}
	public Date getDateFinEnchere() {
		return dateFinEnchere;
	}
	public void setDateFinEnchere(Date dateFinEnchere) {
		this.dateFinEnchere = dateFinEnchere;
	}
	public int getMiseAPrix() {
		return miseAPrix;
	}
	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	public String getEtatVente() {
		return etatVente;
	}
	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}
	
}

	
