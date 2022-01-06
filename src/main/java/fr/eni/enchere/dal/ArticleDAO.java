package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.exceptions.GestionException;

public interface ArticleDAO {
	
	public void insert(Article article) throws GestionException;
	public void update(Article article) throws GestionException;
	public String delete(int noArticleDelete) throws GestionException;
	public List<Categorie> SelectAllCategories();
	public List<Article> SelectArticleWhereCategorie(int idCatRecherche,String catRecherche);
	public List<Article> SelectArticleAll() throws GestionException;
	public Article SelectArticleWhereID(int articleId) throws GestionException;
	
}
