package fr.eni.enchere.bll;

import java.util.Date;
import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.ArticleDAO;
import fr.eni.enchere.dal.DAOFactory;
import fr.eni.enchere.exceptions.GestionException;

public class ArticleManager {
	
	public static ArticleManager instance;
	private ArticleDAO articleDAO;
	public Date aujourdhui = new Date();	
	
	
	
	public ArticleManager() {
		articleDAO = DAOFactory.getArticleDAO();
	}
	
	
	/*public static ArticleManager getInstance() {
		if (instance == null) {
			instance = new ArticleManager();
		}
		return instance;
	}*/
	
	
	public Article ajouter(Article article) throws GestionException {
		
		GestionException exception = new GestionException();
		//l'article est déja créé dans la servlet, pas de besoin de l'instancier un nouvel article ici
		
		this.validerNomArticle(article,exception);
		this.validerDescription(article,exception);
		this.validerDateDebut(article,exception);
		this.validerDateFin(article,exception);
		this.validerMiseAPrix(article,exception);

		if(!exception.hasErreurs())
		{
			this.articleDAO.insert(article);
		}
		
		if(exception.hasErreurs())
		{
			throw exception;
		}
		return article;
	}
	
	public List<Categorie> recupererCategorie() {
		List<Categorie> listeCategorie = null;
		
		try {
			listeCategorie = articleDAO.SelectAllCategories();
		} catch (Exception e) {
			GestionException gE = new GestionException();
			gE.ajouterErreur(CodesResultatBLL.MANAGER_CATEGORIE_ERREUR);
		} 
		return listeCategorie;	
	}
	
	public List<Article> recupererArticleWhere(int idCatRecherche, String catRecherche) {
		List<Article> listeArticleWhere = null;
		
		try {
			listeArticleWhere = articleDAO.SelectArticleWhereCategorie(idCatRecherche,catRecherche);
		} catch (Exception e) {
			GestionException gE = new GestionException();
			gE.ajouterErreur(CodesResultatBLL.MANAGER_ARTICLE_ERREUR);
		} 
		return listeArticleWhere;	
	}
	
	public List<Article> recupererArticleAll(List<Categorie> listCatUse) {
		List<Article> listeArticleWhere = null;
		
		try {
			listeArticleWhere = articleDAO.SelectArticleAll(listCatUse);
		} catch (Exception e) {
			GestionException gE = new GestionException();
			gE.ajouterErreur(CodesResultatBLL.MANAGER_ARTICLE_ERREUR);
		} 
		return listeArticleWhere;	
	}
	
	//Validation
	
	private void validerNomArticle(Article article, GestionException gestionExcep){
		if(article.getNomArticle()==null||article.getNomArticle().length()>30){
			gestionExcep.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_NOM_ERREUR);
		}
	}
	
	private void validerDescription(Article article, GestionException gestionExcep){
		if(article.getDescription()==null  || article.getDescription().equals("")|| article.getDescription().length()>300){
			gestionExcep.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DESCRIPTION_ERREUR);
		}
	}
	
	private void validerDateDebut(Article article, GestionException gestionExcep){
		if(article.getDateDebutEnchere()==null  || article.getDateDebutEnchere().before(aujourdhui)){
			gestionExcep.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATE_DEBUT_ERREUR);
		}
	}
	
	private void validerDateFin(Article article, GestionException gestionExcep){
		if(article.getDateDebutEnchere()==null  || article.getDateFinEnchere().before(aujourdhui)){
			gestionExcep.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATE_FIN_ERREUR);
		}
	}
	private void validerMiseAPrix(Article article, GestionException gestionExcep){
		if(article.getMiseAPrix()<=0 ){
			gestionExcep.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_MISE_A_PRIX_ERREUR);
			System.out.println("problème mise à prix, code envoyé" + CodesResultatBLL.REGLE_ARTICLE_MISE_A_PRIX_ERREUR);
		}
	}

}
