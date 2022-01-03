package fr.eni.enchere.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletManipulationSession
 */
@WebServlet("/Session")
public class Session extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String noUtilisateur = request.getParameter("noUtilisateur");
    	String pseudo = request.getParameter("pseudo");
    	String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
    	String email = request.getParameter("email");
    	String telephone = request.getParameter("telephone");
    	String rue = request.getParameter("rue");
    	String codePostal = request.getParameter("code postal");
    	String ville = request.getParameter("ville");
    	String motDePasse = request.getParameter("mot de passe");
    	String credit = request.getParameter("credit");
    	String administrateur = request.getParameter("administrateur");
        
    	HttpSession session = request.getSession();

        session.setAttribute("no Utilisateur", noUtilisateur);
        session.setAttribute("pseudo", pseudo);
        session.setAttribute("email", email);
        session.setAttribute("telephone", telephone);
        session.setAttribute("rue", rue);
        session.setAttribute("code postale", codePostal);
        session.setAttribute("ville", ville);
        session.setAttribute("mot de passe", motDePasse);
        session.setAttribute("credit", credit);
        session.setAttribute("administrateur", administrateur);
	}
}