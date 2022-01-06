package fr.eni.enchere.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exceptions.GestionException;

/**
 * Servlet implementation class ServletDetailArticle
 */
@WebServlet("/Detail_Article")
public class ServletDetailArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletDetailArticle() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		ArticleManager artManag = new ArticleManager();
		Article articleDetail = new Article();
		List<Categorie> listCatUse = new ArrayList<Categorie>();
			
			int noArticleDetail = (int) session.getAttribute("detailNoArt");
			//session.removeAttribute("detailNoArt");
			
			try {
				articleDetail = artManag.recupererArticleWhereID(noArticleDetail);
				listCatUse = artManag.recupererCategorie();
			} catch (GestionException e) {
				request.setAttribute("listExce",e.getListeCodesErreur());			
			}
	
			request.setAttribute("listCatUse", listCatUse);
			request.setAttribute("articleDetail", articleDetail);
			
			if(articleDetail.getNomArticle()!= null) { // Verification d'un article à afficher
				if(articleDetail.getVendeur().getNoUtilisateur() == (int)session.getAttribute("user_id")) {
					this.getServletContext().getRequestDispatcher("/WEB-INF/DetailArticle.jsp").forward(request, response);
				}else {
					this.getServletContext().getRequestDispatcher("/WEB-INF/DetailEnchere.jsp").forward(request, response);
				}
			} else {
				response.sendRedirect("Acceuil");
			}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		HttpSession session = request.getSession();
		
		ArticleManager artManag = new ArticleManager();
		Article articleDetail = new Article();
		List<Categorie> listCatUse = new ArrayList<Categorie>();
		int noArticleDetail = -1;
		String messageDelete = null;

		// Récupération de la liste des catégories utilisable
		
			try {
				listCatUse = artManag.recupererCategorie();
			} catch (GestionException e1) {
				request.setAttribute("listExce",e1.getListeCodesErreur());	
			}			
			
		/*
		 * Redirection dans le cas ou utilisateur ne possedant pas l'article à voulu encherir
		 */
			
			if(request.getParameter("encherir") != null) {
				int noArt = Integer.valueOf(request.getParameter("noArt"));
				int proposition = Integer.valueOf(request.getParameter("proposition"));
				
				session.setAttribute("noArt", noArt);
				session.setAttribute("proposition", proposition);
				response.sendRedirect("Detail_Enchere"); // correspond au @WebServlet de la servlet
			}
			
		/*
		 * Si l'utilisateur souhaite annuler la vente
		 */
			
			else if (request.getParameter("annulerVente") != null) {
				
				try {
					int noArticleDelete = (int) session.getAttribute("detailNoArt");
					messageDelete = artManag.supprimer(noArticleDelete);
				} catch (GestionException e) {
					request.setAttribute("listExce",e.getListeCodesErreur());	
				}
				if(messageDelete != null) {
					response.sendRedirect("Acceuil");
					System.out.println("Etat suppression : " + messageDelete);
				}
				
			}
		
		/*
		 * Si l'utilisateur souhaite modifier vente la vente
		 */
			
			else if (request.getParameter("modifierVente") != null) {
				Article article = new Article();
				Utilisateur currentUser = new Utilisateur();
				UtilisateurManager userManag = new UtilisateurManager();
				
				try {
					article.setNoArticle((int) session.getAttribute("detailNoArt")); // récupération du Numéro d'article à Update
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
						
					currentUser = userManag.getById((int) session.getAttribute("user_id"));
					article.setVendeur(currentUser);		
					
					
					// Demande d'ajout à la base de données
					
					artManag.modifier(article);
					
					
					
				} catch (GestionException e) {
					e.ajouterErreur(CodeResultatServlets.FORMAT_CHAMP_ARTICLE_ERREUR);
					request.setAttribute("listExce",e.getListeCodesErreur());
				} catch (Exception e) {
					GestionException gE = new GestionException();
					gE.ajouterErreur(CodeResultatServlets.FORMAT_DATE_ERREUR);
					request.setAttribute("listExce",gE.getListeCodesErreur());
				}
				
			}
		
		/*
		 * Sinon récupération à nouveau les éléments article et catégorie pour affichage.
		 */	
		if(messageDelete == null) {
				noArticleDetail = (int) session.getAttribute("detailNoArt");
			
			try {
				articleDetail = artManag.recupererArticleWhereID(noArticleDetail);
			} catch (GestionException e) {
				request.setAttribute("listExce",e.getListeCodesErreur());			
			}
	
			request.setAttribute("listCatUse", listCatUse);
			request.setAttribute("articleDetail", articleDetail);
	
			if(articleDetail.getNomArticle()!= null) { // Verification d'un article à afficher et pas non plus delete
				
				if(articleDetail.getVendeur().getNoUtilisateur() == (int)session.getAttribute("user_id")) {
					// si le vendeur est le meme utilisateur que le visiteur
					this.getServletContext().getRequestDispatcher("/WEB-INF/DetailArticle.jsp").forward(request, response);
				}else {
					// si non ...
					this.getServletContext().getRequestDispatcher("/WEB-INF/DetailEnchere.jsp").forward(request, response);
				}
			} else {
				response.sendRedirect("Acceuil");
			}
		}
		
	}//fermeture DoPost

}//fermeture Servlet
