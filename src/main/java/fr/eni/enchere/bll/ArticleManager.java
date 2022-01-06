package fr.eni.enchere.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.ArticleDAO;
import fr.eni.enchere.dal.DAOFactory;
import fr.eni.enchere.exceptions.GestionException;

public class ArticleManager {
	
	private static ArticleManager instance;
	private ArticleDAO articleDAO;
	public Date aujourdhui = new Date();
	
	
	public static ArticleManager getInstance() {
        if (instance == null) {
            return new ArticleManager();
        }
        return instance;
    }
	
	
	public ArticleManager() {
		articleDAO = DAOFactory.getArticleDAO();
	}
	
	
	public String ajouter(Article article) throws GestionException {
		
		GestionException exception = GestionException.getInstance();
		String message = null;
		
		this.validerNomArticle(article,exception);
		this.validerDescription(article,exception);
		this.validerDateDebut(article,exception);
		this.validerDateFin(article,exception);
		this.validerMiseAPrix(article,exception);

		if(!exception.hasErreurs())
		{
			this.articleDAO.insert(article);
			message = "Article ajouté avec succès";
		}
		else
		{
			throw exception;
		}
		return message;
	}

	
	public Article modifier(Article article) throws GestionException {
		GestionException exception = new GestionException();
		
		this.validerNomArticle(article,exception);
		this.validerDescription(article,exception);
		this.validerDateDebutUpdate(article,exception);
		this.validerDateFin(article,exception);
		this.validerMiseAPrix(article,exception);

		if(!exception.hasErreurs())
		{
			this.articleDAO.update(article);
		}
		else
		{
			throw exception;
		}
		return article;
	}

	public String supprimer(int noArticleDelete) throws GestionException {
		GestionException exception = new GestionException();
		String messageDelete = null;
		
		if(articleDAO.SelectArticleWhereID(noArticleDelete) == null) {
			exception.ajouterErreur(CodesResultatBLL.MANAGER_ARTICLE_DELETE_NULL);
			throw exception;
		}else {
			messageDelete = this.articleDAO.delete(noArticleDelete);
		}
		return messageDelete;
	}
	
	
	public List<Categorie> recupererCategorie() throws GestionException{
		
		List<Categorie> listeCategorie = null;
		GestionException exception = new GestionException();
		
		if(articleDAO.SelectAllCategories().size() == 0) {
			exception.ajouterErreur(CodesResultatBLL.MANAGER_CATEGORIE_NULL);
		}
		listeCategorie = articleDAO.SelectAllCategories();
	
		return listeCategorie;	
	}
	
	
	public List<Article> recupererArticleWhere(int idCatRecherche, String catRecherche) throws GestionException{
		List<Article> listeArticleWhere = null;
		GestionException exception = new GestionException();
		
		if(articleDAO.SelectArticleWhereCategorie(idCatRecherche,catRecherche).size() == 0) {
			exception.ajouterErreur(CodesResultatBLL.MANAGER_ARTICLE_NULL);
			throw exception;
		}
		else {
			listeArticleWhere = articleDAO.SelectArticleWhereCategorie(idCatRecherche,catRecherche);
		}

		return listeArticleWhere;	
	}
	
	public Article recupererArticleWhereID(int ArticleId) throws GestionException{
		Article ArticleWhereId = null;
		GestionException exception = new GestionException();
		
		if(articleDAO.SelectArticleWhereID(ArticleId) == null) {
			exception.ajouterErreur(CodesResultatBLL.MANAGER_ARTICLE_NULL);
			throw exception;
		}
		else {
			ArticleWhereId = articleDAO.SelectArticleWhereID(ArticleId);
		}

		return ArticleWhereId;	
	}
	
	
	public List<Article> recupererArticleAll() throws GestionException{
		List<Article> listeArticleAll = null;
		GestionException exception = new GestionException();
		
		if(articleDAO.SelectArticleAll().size() == 0) {
			exception.ajouterErreur(CodesResultatBLL.MANAGER_ARTICLE_NULL);
			throw exception;
		}
		else {
		listeArticleAll = articleDAO.SelectArticleAll();
		}
		
		return listeArticleAll;	
	}
	
	/*
	 * 
	 * Traitements sans DAO
	 * 
	 */
	
	public List<Article> filtrerArticle(String motRecherche,List<Article> listArticle)throws GestionException{
		List<Article> listeArticleFiltre = new ArrayList<Article>();
		GestionException exception = new GestionException();
		
		for (Article article : listArticle) {
			if(article.getNomArticle().contains(motRecherche) || article.getDescription().contains(motRecherche) ) {
				listeArticleFiltre.add(article);	
			}
		}
		if(listeArticleFiltre.size() == 0) {
			exception.ajouterErreur(CodesResultatBLL.ARTICLE_CORRESPONDANT_MOT_NULL);
			throw exception;
		}
		return listeArticleFiltre;	
	}
	

	public String ConversionEtatVente(int prixVente, java.sql.Date dateDeDebut, java.sql.Date dateDeFin) {
		String etatVente = null;
		//"EnCours""NonDeb""Remporte""Termine"
		
		if(prixVente != 0) {
			// Si on a un prix de vente l'article est vendu
			etatVente = "Vendu";
			
		} else if(dateDeDebut.before(aujourdhui)) {
			//si la date de début est avant aujourd'hui l'enchère à commencé
			if(dateDeFin.before(aujourdhui)) {
				//si la date de fin est avant aujourd'hui l'enchère est terminé
				etatVente = "Terminé sans vente";
			}else {
				// sinon l'enchère est en cours
				etatVente = "En cours";
			}
		}else {
			//l'enchère n'a pas démarrée
			etatVente = "Non débutée";
		}
		return etatVente;
	}
	
	public Boolean isNull(String message) {
		Boolean resultat;
		if (message == null) {
			resultat = false;
		}else {
			resultat = true;
		}
		return resultat;
	}

	public List<Article> filtreByTypeArticle(List<Article> listArticleFinal, String typeArticle, int noUser) throws GestionException {
		List<Article> resultatList = new ArrayList<Article>();
		GestionException exception = GestionException.getInstance();
		if(typeArticle.equals("achat")) {
			for (Article article : listArticleFinal) {
				if(article.getVendeur().getNoUtilisateur() != noUser) {
					resultatList.add(article);
				}
			}
			
		}else if(typeArticle.equals("vente")) {
			for (Article article : listArticleFinal) {
				if(article.getVendeur().getNoUtilisateur() == noUser) {
					resultatList.add(article);
				}
			}
			
		}else {
			throw exception;
		}
		
		return resultatList;
	}


	public List<Article> addWhenEtatVente(List<Article> listAAgrementer, List<Article> listArticleFiltreParType,String etatVenteRecherche) throws GestionException {
		List<Article> resultatList = new ArrayList<Article>();
		GestionException exception = GestionException.getInstance();
		
		//"EnCours""NonDeb""Termine""NonRemporte"
		//"Remporte" signifie  Terminé + meilleur enchère par l'utilisateur courant
		//TODO modifier la formulaire quand on aura le système d'enchère
		
		if(listAAgrementer != null) {
			resultatList = listAAgrementer;
		}
		
		if(etatVenteRecherche.equals("Termine")) {
			for (Article article : listArticleFiltreParType) {
				if(article.getEtatVente() == "Vendu") {
					resultatList.add(article);
				}
			}
			
		}else if(etatVenteRecherche.equals("Remporte")) {
			//TODO à ajouter une comparaison des enchères
			for (Article article : listArticleFiltreParType) {
				if(article.getEtatVente() == "Vendu") { 
					resultatList.add(article);
				}
			}
			
		}else if(etatVenteRecherche.equals("EnCours")) {
			for (Article article : listArticleFiltreParType) {
				if(article.getEtatVente() == "En cours") {
					resultatList.add(article);
				}
			}
		}else if(etatVenteRecherche.equals("NonDeb")) {
			for (Article article : listArticleFiltreParType) {
				if(article.getEtatVente() == "Non débutée") {
					resultatList.add(article);
				}
			}
		}else if(etatVenteRecherche.equals("NonRemporte")) {
			for (Article article : listArticleFiltreParType) {
				if(article.getEtatVente() == "Terminé sans vente") {
					resultatList.add(article);
				}
			}
		}else{
			throw exception;
		}
		
		return resultatList;
	}

	
	/*
	 * 
	 * Validation des entrées
	 * 
	 */
	
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
		if(article.getDateDebutEnchere()==null  || article.getDateDebutEnchere().before(aujourdhui) || article.getDateDebutEnchere().after(article.getDateFinEnchere())){
			gestionExcep.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATE_DEBUT_ERREUR);
		}
	}
	
	private void validerDateDebutUpdate(Article article, GestionException gestionExcep){
		if(article.getDateDebutEnchere()==null  ||  article.getDateDebutEnchere().after(article.getDateFinEnchere())){
			gestionExcep.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATE_DEBUT_UPDATE_ERREUR);
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
		}
	}

}
