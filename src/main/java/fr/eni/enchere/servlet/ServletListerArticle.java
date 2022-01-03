package fr.eni.enchere.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;


@WebServlet("/Liste_Article")
public class ServletListerArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ArticleManager artManag = new ArticleManager();
	List<Categorie> listCatUse = artManag.recupererCategorie();
	List<Article> listArticle = null;
	List<Article> listArticleFinal = null;

    public ServletListerArticle() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listCatUse", listCatUse);
		request.setCharacterEncoding("UTF-8");
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/ListerArticles.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listCatUse", listCatUse);
		request.setCharacterEncoding("UTF-8");
		
		// récupération des champs de saisie Post
		String motRecherche = request.getParameter("champRecherche");
		String catRecherche = request.getParameter("categorie");
		int idCatRecherche = -1;
		// récupération de l'ID Catégorie placé à -1 si "Toutes"
		if(!catRecherche.equals("Toutes")) {			
			for (Categorie categorie : listCatUse) {
				if(categorie.getLibelle().equals(catRecherche)) {
					idCatRecherche = categorie.getId();
				}
			}
		} 
			
		if (idCatRecherche == -1) {
			System.out.println("Categorie TOUTES");
			listArticle = artManag.recupererArticleAll(listCatUse);
			
		}else{
			System.out.println("Categorie " + catRecherche);
			listArticle = artManag.recupererArticleWhere(idCatRecherche,catRecherche);
		}
		
		if(!motRecherche.equals("")) {
			for (Article article : listArticle) {
				if(article.getNomArticle().contains(motRecherche) || article.getDescription().contains(motRecherche) ) {
					listArticleFinal.add(article);
					System.out.println(article.getNomArticle() + " : " + article.getDescription());
				}
			}
		}else{
			listArticleFinal = listArticle;
		}
		
		request.setAttribute("listeArticleEnchere", listArticleFinal);
		this.getServletContext().getRequestDispatcher("/WEB-INF/ListerArticles.jsp").forward(request, response);
	}

}
