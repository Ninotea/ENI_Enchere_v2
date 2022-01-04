package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.exceptions.GestionException;

public interface ArticleDAO {
	
	public void insert(Article article) throws GestionException;
	public List<Categorie> SelectAllCategories();
	public List<Article> SelectArticleWhereCategorie(int idCatRecherche,String catRecherche);
	public List<Article> SelectArticleAll(List<Categorie> listCatUse);
	public List<Article> filtre(String motRecherche,List<Article> listArticle);
	
}
