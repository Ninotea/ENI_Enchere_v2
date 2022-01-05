package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exceptions.GestionException;

public class ArticleDAOJdbcImpl implements ArticleDAO {
	
	private static final String INSERT_ARTICLE = "insert into ARTICLES"
			+ "(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie)"
			+ "values(?,?,?,?,?,?,?);";
	private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
	private static final String SQL_SELECT_ARTICLE_ALL = 
			"SELECT no_article, nom_article,description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_categorie," 				// La partie Article
			+ "UTILISATEURS.no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur " 		// La partie Utilisateur (on utilise "UTILISATEUR.no_utilisateur pour spécifier à dans quel table récupérer no_utilisateur)
			+ "FROM ARTICLES inner join UTILISATEURS on ARTICLES.no_utilisateur=UTILISATEURS.no_utilisateur";											// Le Inner Join pour utiliser le Foreign Key
	private static final String SQL_SELECT_ARTICLE_WHERE_CATEGORIES = 
			"SELECT no_article, nom_article,description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_categorie," 				
			+ "UTILISATEURS.no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur " 		
			+ "FROM ARTICLES  inner join UTILISATEURS on ARTICLES.no_utilisateur=UTILISATEURS.no_utilisateur WHERE no_categorie = ";					
	private static final String SQL_SELECT_ARTICLE_WHERE_ID =
			"SELECT no_article, nom_article,description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_categorie," 				
			+ "UTILISATEURS.no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur " 		
			+ "FROM ARTICLES  inner join UTILISATEURS on ARTICLES.no_utilisateur=UTILISATEURS.no_utilisateur WHERE no_article =";						

	@Override
	public void insert(Article article) throws GestionException{
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{

					PreparedStatement statement = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
					statement.setString(1, article.getNomArticle());
					statement.setString(2, article.getDescription());
					statement.setDate(3, new Date(article.getDateDebutEnchere().getTime()));
					statement.setDate(4, new Date(article.getDateFinEnchere().getTime()));
					statement.setInt(5, article.getMiseAPrix());
					statement.setInt(6, article.getVendeur().getNoUtilisateur());
					statement.setInt(7, article.getNoCategorie());
					statement.executeUpdate();
					ResultSet rs = statement.getGeneratedKeys();
					if(rs.next())
					{
						article.setNoArticle(rs.getInt(1));
					}

			}
			catch(SQLException e)
			{
				GestionException gestionException = new GestionException();
				gestionException.ajouterErreur(CodesResultatDAL.INSERTION_ARTICLE_ERREUR);
			}
			
		}
	
	@Override
	public List<Categorie> SelectAllCategories(){
		
		List<Categorie> listeCategorie = new ArrayList<Categorie>(); //Création d'une liste de catégorie
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{				
			Statement statement = cnx.createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_CATEGORIES);
			
			while(rs.next()) {
				Categorie categorieResultSet = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
				listeCategorie.add(categorieResultSet);
			}
		
		}
		catch(SQLException e)
		{
			GestionException gestionException = new GestionException();
			gestionException.ajouterErreur(CodesResultatDAL.RECUPERATION_CATEGORIE_ERREUR);
		}
		return listeCategorie;
	}
	
	@Override
	public List<Article> SelectArticleWhereCategorie(int idCatRecherche,String catRecherche){
		
		List<Article> listeArticle = new ArrayList<Article>(); //Création d'une liste d'article
		ArticleManager artManag = new ArticleManager();
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{				
			Statement statement = cnx.createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT_ARTICLE_WHERE_CATEGORIES + idCatRecherche);

			while(rs.next()) {
				
				String etatVente = "";
				
				// Validation d'un état vente
				etatVente = artManag.ConversionEtatVente(rs.getInt("prix_vente"),rs.getDate("date_debut_encheres"),rs.getDate("date_fin_encheres"));
				
				Article articleResultSet = new Article(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						catRecherche,
						rs.getInt("no_categorie"),
						rs.getDate("date_debut_encheres"),
						rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						etatVente,
						new Utilisateur(
								rs.getInt("no_utilisateur"),
								rs.getString("pseudo"),
								rs.getString("nom"),
								rs.getString("prenom"),
								rs.getString("email"),
								rs.getString("telephone"),//no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur
								rs.getString("rue"),
								rs.getString("code_postal"),
								rs.getString("ville"),
								null,//ne pas récupérer le Mot de passe par principe et ça sert à rien
								rs.getInt("credit"),
								rs.getBoolean("administrateur")));	

				listeArticle.add(articleResultSet);
			}
		
		}
		catch(SQLException e)
		{
			
		}
		return listeArticle;
	}
	
	@Override
	public Article SelectArticleWhereID(int articleId) throws GestionException{
		
		Article articleWhereId = new Article();
		ArticleManager artManag = new ArticleManager();
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{				
			Statement statement = cnx.createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT_ARTICLE_WHERE_ID + articleId);

			while(rs.next()) {
				String nomCategorie = "";
				String etatVente = "";
				
				// Validation d'un état vente
				etatVente = artManag.ConversionEtatVente(rs.getInt("prix_vente"),rs.getDate("date_debut_encheres"),rs.getDate("date_fin_encheres"));
				
				// Récupération du nom catégorie en fonctione de l'ID
				ArticleManager articleManager = new ArticleManager();
				List<Categorie> listCatUse = articleManager.recupererCategorie();	
				
					for (Categorie categorie : listCatUse) {
						if(categorie.getId() == rs.getInt("no_categorie")) {
							nomCategorie = categorie.getLibelle();
						} 
					}
				
				Article articleResultSet = new Article(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						nomCategorie,
						rs.getInt("no_categorie"),
						rs.getDate("date_debut_encheres"),
						rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						etatVente,
						new Utilisateur(
								rs.getInt("no_utilisateur"),
								rs.getString("pseudo"),
								rs.getString("nom"),
								rs.getString("prenom"),
								rs.getString("email"),
								rs.getString("telephone"),//no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur
								rs.getString("rue"),
								rs.getString("code_postal"),
								rs.getString("ville"),
								null,//ne pas récupérer le Mot de passe par principe et ça sert à rien
								rs.getInt("credit"),
								rs.getBoolean("administrateur")));	

				articleWhereId = articleResultSet;
			}
		
		}
		catch(SQLException e)
		{
			
		}
		return articleWhereId;
	}
	
	@Override
	public List<Article> SelectArticleAll() throws GestionException{
		
		List<Article> listeArticle = new ArrayList<Article>(); //Création d'une liste d'article
		ArticleManager artManag = new ArticleManager();
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{				
			Statement statement = cnx.createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT_ARTICLE_ALL);
			
			// Itération des résultats SELECT 
			while(rs.next()) { 
				String nomCategorie = "";
				String etatVente = "";
				
			// Récupération du nom catégorie en fonctione de l'ID
				ArticleManager articleManager = new ArticleManager();
				List<Categorie> listCatUse = articleManager.recupererCategorie();	
				
					for (Categorie categorie : listCatUse) {
						if(categorie.getId() == rs.getInt("no_categorie")) {
							nomCategorie = categorie.getLibelle();
						}
					}
				
			// Validation d'un état vente
				etatVente = artManag.ConversionEtatVente(rs.getInt("prix_vente"),rs.getDate("date_debut_encheres"),rs.getDate("date_fin_encheres"));
				
				Article articleResultSet = new Article(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						nomCategorie,
						rs.getInt("no_categorie"),
						rs.getDate("date_debut_encheres"),
						rs.getDate("date_fin_encheres"),
						rs.getInt("prix_initial"),
						rs.getInt("prix_vente"),
						etatVente,
						new Utilisateur(
								rs.getInt("no_utilisateur"),
								rs.getString("pseudo"),
								rs.getString("nom"),
								rs.getString("prenom"),
								rs.getString("email"),
								rs.getString("telephone"),//no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur
								rs.getString("rue"),
								rs.getString("code_postal"),
								rs.getString("ville"),
								null,//ne pas récupérer le Mot de passe par principe et ça sert à rien
								rs.getInt("credit"),
								rs.getBoolean("administrateur")));			

				listeArticle.add(articleResultSet);
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return listeArticle;
	}

}
