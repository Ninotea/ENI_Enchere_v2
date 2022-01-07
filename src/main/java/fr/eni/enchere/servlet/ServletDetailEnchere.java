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
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.exceptions.GestionException;

/**
 * Servlet implementation class ServletDetailEnchere
 */
@WebServlet("/Detail_Enchere")
public class ServletDetailEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public ServletDetailEnchere() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager enchManag = new EnchereManager();
		Enchere enchereDetail = new Enchere();
		HttpSession session = request.getSession();

		System.out.println("DoGET");
		
		int noArticleDetail = (int) session.getAttribute("detailNoArt");
		int propositionEnchere = (int) session.getAttribute("proposition");
		
		try {
			enchereDetail = enchManag.SelectEnchereWhereID(noArticleDetail);
			/* TODO: Modifié le prix par la proposition
			 * propositionEnchere =
			 */
		} catch (GestionException e) {
			request.setAttribute("listExce",e.getListeCodesErreur());			
		
		}
		
		//TODO: 
		//si l'enchere n'a pas pu etre insérée
		this.getServletContext().getRequestDispatcher("/WEB-INF/DetailEnchere.jsp").forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DoPOST");
	}

}
