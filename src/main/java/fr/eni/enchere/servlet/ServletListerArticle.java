package fr.eni.enchere.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.exceptions.GestionException;


@WebServlet(
		urlPatterns= {
		"/Acceuil",
		"/Les_Encheres",
		"/"
		})
public class ServletListerArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ArticleManager artManag = new ArticleManager();
	List<Categorie> listCatUse = null;
	List<Article> listArticle = null;
	List<Article> listArticleFinal = null;
	

    public ServletListerArticle() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Récupération des catégorie et des articles
			try {
				listCatUse = artManag.recupererCategorie();
				listArticleFinal = artManag.recupererArticleAll();
			} catch (GestionException e) {
				request.setAttribute("listExce",e.getListeCodesErreur());
			}

		request.setAttribute("listeArticleEnchere", listArticleFinal);
		request.setAttribute("listCatUse", listCatUse);
		request.setCharacterEncoding("UTF-8");
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/ListerArticles.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("details") != null) {
			int detailNoArt = Integer.valueOf(request.getParameter("detailNoArt"));
			HttpSession session =request.getSession();
			session.setAttribute("detailNoArt", detailNoArt);
			response.sendRedirect("Detail_Article");
		}
		else {
			
			try {
				listCatUse = artManag.recupererCategorie();
			} catch (GestionException e) {
				request.setAttribute("listExce",e.getListeCodesErreur());			
			}
			
			request.setAttribute("listCatUse", listCatUse);
			request.setCharacterEncoding("UTF-8");
			listArticleFinal = new ArrayList<Article>();
			
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
				
				/*
				 * Si id catégorie est inchangé et vaut donc toujours -1 pour signifier "toutes"
				 * on utilise la methode recupererArticleAll sinon on utilise la methode recupererArticleWhere
				 */
				
				if (idCatRecherche == -1) {
						try {
							listArticle = artManag.recupererArticleAll();
						} catch (GestionException e) {
							request.setAttribute("listExce",e.getListeCodesErreur());
						}
					
				}else{
						try {
							listArticle = artManag.recupererArticleWhere(idCatRecherche,catRecherche);
						} catch (GestionException e) {
							request.setAttribute("listExce",e.getListeCodesErreur());
						}
				}
				
				if(!motRecherche.equals("")) {
					try {
						listArticleFinal = artManag.filtrerArticle(motRecherche,listArticle);
					} catch (GestionException e) {
						request.setAttribute("listExce",e.getListeCodesErreur());
					}
				}else{
					listArticleFinal = listArticle;
				}
			request.setAttribute("listeArticleEnchere", listArticleFinal);
			this.getServletContext().getRequestDispatcher("/WEB-INF/ListerArticles.jsp").forward(request, response);
			}
		}

}
