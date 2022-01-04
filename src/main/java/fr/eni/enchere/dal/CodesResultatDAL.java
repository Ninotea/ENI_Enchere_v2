package fr.eni.enchere.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {
	
	/**
	 * Echec à l'insertion d'un article dans la base de donnée
	 */
	public static final int INSERTION_ARTICLE_ERREUR=10000;
	
	/**
	 * Echec de récupération des catégories dans la base de donnée
	 */
	public static final int RECUPERATION_CATEGORIE_ERREUR=10001;
	
	/**
	 * Echec de récupération des catégories dans la base de donnée
	 */
	public static final int RECUPERATION_ARTICLE_WHERE_NULL=10002;
	
}
