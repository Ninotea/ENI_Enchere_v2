package fr.eni.enchere.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.CodesResultatBLL;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exceptions.GestionException;

/**
 * Servlet implementation class ServletVendreArticle
 */
@WebServlet("/VendreArticle")
public class ServletVendreArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Article article = new Article();
	ArticleManager artManag = new ArticleManager();
	List<Categorie> listCatUse = null;
	Utilisateur currentUser = new Utilisateur();
	UtilisateurManager userManag = new UtilisateurManager();
	    


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			listCatUse = artManag.recupererCategorie();
		} catch (GestionException e) {
			request.setAttribute("listExce",e.getListeCodesErreur());			
		}
		request.setAttribute("listCatUse", listCatUse);
		request.setCharacterEncoding("UTF-8");
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/VendreArticle.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			listCatUse = artManag.recupererCategorie();
		} catch (GestionException e) {
			request.setAttribute("listExce",e.getListeCodesErreur());			
		}
		request.setAttribute("listCatUse", listCatUse);
		request.setCharacterEncoding("UTF-8");
		
		try {
			article.setNomArticle(request.getParameter("nomArticle"));
			article.setDescription(request.getParameter("description"));
			article.setMiseAPrix(Integer.valueOf(request.getParameter("prixDepart")));
						
			
			// Formatage des dates
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateDebut = sdFormat.parse(request.getParameter("dateDeDebut"));
			Date dateDeFin = sdFormat.parse(request.getParameter("dateDeFin"));	
			
			article.setDateDebutEnchere(dateDebut);
			article.setDateFinEnchere(dateDeFin);
			
		
			// Formatage categorie et set correct pour article
			String catStrPost = request.getParameter("categorie");
			
			article.setCategorie(catStrPost);
			
				for (Categorie categorie : listCatUse) {
					if(categorie.getLibelle().equals(catStrPost)) {
						article.setNoCategorie(categorie.getId());
					}
				}
			
				
			// Récupération des données utilisateurs via ID
			HttpSession session = request.getSession();
			currentUser = userManag.getById((int) session.getAttribute("user_id"));

			article.setVendeur(currentUser);		
			
			
			// Demande d'ajout à la base de données
			artManag.ajouter(article);
			
		} catch (GestionException e) {
			e.ajouterErreur(CodeResultatServlets.FORMAT_CHAMP_ARTICLE_ERREUR);
			request.setAttribute("listExce",e.getListeCodesErreur());
		} catch (Exception e) {
			GestionException gE = new GestionException();
			gE.ajouterErreur(CodeResultatServlets.FORMAT_DATE_ERREUR);
			request.setAttribute("listExce",gE.getListeCodesErreur());
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/VendreArticle.jsp").forward(request, response);
	}
	
}
