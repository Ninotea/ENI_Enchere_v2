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

	ArticleManager artManag = ArticleManager.getInstance();
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
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		listArticleFinal = new ArrayList<Article>();
		List<Article> listArticleFiltreParType = null;
		List<Article> listArticleFiltreFinal = null;
		
		// Recupération de la liste de catégorie utilisés dans tous les cas
			try {
				listCatUse = artManag.recupererCategorie();
			} catch (GestionException e) {
				request.setAttribute("listExce",e.getListeCodesErreur());			
			}
		
		/*
		 * Dans le cas où nous recevons l'ordre d'afficher le détail
		 */
			if(request.getParameter("details") != null) {
				int detailNoArt = Integer.valueOf(request.getParameter("detailNoArt"));
				session.setAttribute("detailNoArt", detailNoArt);
				response.sendRedirect("Detail_Article");
			}
		
		/*
		 * Dans le cas d'utilisation standard
		 */
			else {
				// récupération des champs de saisie Post
				
					String motRecherche = request.getParameter("champRecherche");
					String catRecherche = request.getParameter("categorie");
					System.out.println(catRecherche);
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
					
					/*
					 * Test des filtres de page
					 */
					String typeArticle = request.getParameter("typeArticle");
					Boolean encEnCours = artManag.isNull(request.getParameter("encEnCours"));
					Boolean encNonDeb = artManag.isNull(request.getParameter("encNonDeb"));
					Boolean encRemporte = artManag.isNull(request.getParameter("encRemporte"));
					Boolean encNonRemporte = artManag.isNull(request.getParameter("encNonRemporte"));
					Boolean venEnCours = artManag.isNull(request.getParameter("venEnCours"));
					Boolean venNonDeb = artManag.isNull(request.getParameter("venNonDeb"));
					Boolean venTerm = artManag.isNull(request.getParameter("venTerm"));
					Boolean venNonRemporte = artManag.isNull(request.getParameter("venNonRemporte"));
					
					if (session.getAttribute("user_id") != null) {
						int noUser = (int) session.getAttribute("user_id");
						
						if(typeArticle != null) {
							//correspond a un filtre by no_utilisateur
							try {
								listArticleFiltreParType = artManag.filtreByTypeArticle(listArticleFinal,typeArticle,noUser);
							} catch (GestionException e) {
								request.setAttribute("listExce",e.getListeCodesErreur());
							}
							listArticleFinal = listArticleFiltreParType;
						}
						
						if(typeArticle != null && typeArticle.equals("achat")) {
							// si aucune CheckBox n'est coché on affiche tous les achats
							if(!encEnCours && !encEnCours && !encRemporte && !encNonRemporte) {
								listArticleFiltreFinal = listArticleFiltreParType;
								
							// sinon on ajoute a la Liste filtrée finale chacun des articles correspondant au critère suivant état vente
							} else {
								try {
									if(encEnCours) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"EnCours");
									}if(encNonDeb) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"NonDeb");
									}if(encRemporte) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"Remporte");
									}if(encNonRemporte) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"NonRemporte");
									}
								} catch (GestionException e) {
									request.setAttribute("listExce",e.getListeCodesErreur());
								}
							}
							listArticleFinal = listArticleFiltreFinal;
						}
						if(typeArticle != null && typeArticle.equals("vente")) {
							// si aucune CheckBox n'est coché on affiche tous les achats
							if(!venEnCours && !venNonDeb && !venTerm && !venNonRemporte) {
								listArticleFiltreFinal = listArticleFiltreParType;
							// sinon on ajoute a la Liste filtrée finale chacun des articles correspondant au critère suivant état vente
							} else {
								try {
									if(venEnCours) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"EnCours");
									}if(venNonDeb) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"NonDeb");
									}if(venTerm) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"Termine");
									}if(venNonRemporte) {
										listArticleFiltreFinal = artManag.addWhenEtatVente(listArticleFiltreFinal,listArticleFiltreParType,"NonRemporte");
									}
								} catch (GestionException e) {
									request.setAttribute("listExce",e.getListeCodesErreur());
								}
							}
							listArticleFinal = listArticleFiltreFinal;
						}
						request.setAttribute("encEnCours",encEnCours);
						request.setAttribute("encNonDeb",encNonDeb);
						request.setAttribute("encRemporte",encRemporte);
						request.setAttribute("encNonRemporte",encNonRemporte);
						request.setAttribute("venEnCours",venEnCours);
						request.setAttribute("venNonDeb",venNonDeb);
						request.setAttribute("venTerm",venTerm);
						request.setAttribute("venNonRemporte",venNonRemporte);
						request.setAttribute("typeArticle",typeArticle);
					}
					
					request.setAttribute("motRecherche",motRecherche);
					request.setAttribute("catRecherche",catRecherche);
					request.setAttribute("listCatUse", listCatUse);
					request.setAttribute("listeArticleEnchere", listArticleFinal);
					this.getServletContext().getRequestDispatcher("/WEB-INF/ListerArticles.jsp").forward(request, response);
				}
		}

}
