package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.exceptions.GestionException;

public class ArticleDAOJdbcImpl implements ArticleDAO {
	
	private static final String INSERT_ARTICLE = "insert into ARTICLES"
			+ "(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie)"
			+ "values(?,?,?,?,?,?,?);";
	private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
	private static final String SQL_SELECT_ARTICLE_WHERE_CATEGORIES = "SELECT no_article, nom_article,description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie "
			+ "FROM ARTICLES WHERE no_categorie = ";
	private static final String SQL_SELECT_ARTICLE_ALL = "SELECT no_article, nom_article,description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie "
			+ "FROM ARTICLES";
	

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
		GestionException exception = new GestionException();
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{				
			Statement statement = cnx.createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT_ARTICLE_WHERE_CATEGORIES + idCatRecherche);
			
			while(rs.next()) {
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
						rs.getInt("no_utilisateur"));

				listeArticle.add(articleResultSet);
			}
		
		}
		catch(SQLException e)
		{
			
		}
		return listeArticle;
	}
	
	@Override
	public List<Article> SelectArticleAll(List<Categorie> listCatUse){
		
		List<Article> listeArticle = new ArrayList<Article>(); //Création d'une liste d'article
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{				
			Statement statement = cnx.createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT_ARTICLE_ALL);
			String nomCategorie = "";
			
			while(rs.next()) {
				for (Categorie categorie : listCatUse) {
					if(categorie.getId() == rs.getInt("no_categorie")) {
						nomCategorie = categorie.getLibelle();
					} else {nomCategorie = "Catégorie Inconnue";}
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
						rs.getInt("no_utilisateur"));

				listeArticle.add(articleResultSet);
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return listeArticle;
	}


	@Override
	public List<Article> filtre(String motRecherche,List<Article> listArticle) {
		List<Article> listArticleFiltre = new ArrayList<Article>();
		
		for (Article article : listArticle) {
			if(article.getNomArticle().contains(motRecherche) || article.getDescription().contains(motRecherche) ) {
				listArticleFiltre.add(article);	
			}
		}
		return listArticleFiltre;
	}

}
