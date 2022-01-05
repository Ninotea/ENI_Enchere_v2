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
		ArticleManager artManag = new ArticleManager();
		Article articleDetail = new Article();
		HttpSession session = request.getSession();
		List<Categorie> listCatUse = new ArrayList<Categorie>();
		
		int noArticleDetail = (int) session.getAttribute("detailNoArt");
		session.removeAttribute("detailNoArt");
		
		try {
			articleDetail = artManag.recupererArticleWhereID(noArticleDetail);
			listCatUse = artManag.recupererCategorie();
		} catch (GestionException e) {
			request.setAttribute("listExce",e.getListeCodesErreur());			
		}

		request.setAttribute("listCatUse", listCatUse);
		request.setAttribute("articleDetail", articleDetail);
		
		if(articleDetail.getVendeur().getNoUtilisateur() == (int)session.getAttribute("user_id")) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/DetailArticle.jsp").forward(request, response);
		}else {
			System.out.println("l'ID ne correspond pas");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/DetailArticle.jsp").forward(request, response);
	}

}
