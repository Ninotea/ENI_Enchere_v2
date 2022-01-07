package fr.eni.enchere.bll;

/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public abstract class CodesResultatBLL {
	
	/**
	 * Echec quand la description de l'avis ne repsecte pas les règles définies
	 */
	public static final int REGLE_ARTICLE_NOM_ERREUR=20000;
	/**
	 * Echec quand la description de l'avis ne repsecte pas les règles définies
	 */
	public static final int REGLE_ARTICLE_DESCRIPTION_ERREUR=20001;
	/**
	 * Echec quand la date de début de l'enchère ne repsecte pas les règles définies
	 */
	public static final int REGLE_ARTICLE_DATE_DEBUT_ERREUR=20002;
	/**
	 * Echec quand la date de fin de l'enchère ne repsecte pas les règles définies
	 */
	public static final int REGLE_ARTICLE_DATE_FIN_ERREUR=20003;
	/**
	 * Echec quand la date de fin de l'enchère ne repsecte pas les règles définies
	 */
	public static final int REGLE_ARTICLE_MISE_A_PRIX_ERREUR=20004;
	/**
	 * Echec de récupération catégorie dans le manager categorie
	 */
	public static final int MANAGER_CATEGORIE_ERREUR=20005;
	/**
	 * Echec de récupération catégorie dans le manager
	 */
	public static final int MANAGER_CATEGORIE_NULL=20006;
	/**
	 * Echec de récupération article dans le manager
	 */
	public static final int MANAGER_ARTICLE_ERREUR=20007;
	/**
	 * Echec de récupération article dans le manager 
	 */
	public static final int MANAGER_ARTICLE_NULL=20008;
	/**
	 * Pas d'article correspondant à la recherche
	 */
	public static final int ARTICLE_CORRESPONDANT_MOT_NULL=20009;
	/**
	 * Echec quand la date de début de l'enchère ne repsecte pas les règles définies
	 */
	public static final int REGLE_ARTICLE_DATE_DEBUT_UPDATE_ERREUR=20010;
	/**
	 * Echec de récupération article à supprimer dans le manager 
	 */
	public static final int MANAGER_ARTICLE_DELETE_NULL = 20011;
	/**
	 * Validation de la disponibilité du pseudo Erreur
	 */
	public static final int VERIFICATION_PSEUDO_ERREUR = 20012;
	/**
	 * Echec de la r�cup�ration enchere dans le manager
	 */
	public static final int MANAGER_ENCHERE_NULL = 40001;
	
	
}
