package fr.eni.enchere.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/connexionUtilisateur")
public class connectionUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Page affichée lorsque l'utilisateur n'est pas connecté à l'appli
	 * Page par défaut
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connection.jsp");

		rd.forward(request, response);
	}

	/**
	 * Tentative de connexion de l'utilisateur
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String emailOrPseudo = request.getParameter("identifiant");
		String password = request.getParameter("motDePasse");
		
		System.out.println("servletdopost"+ emailOrPseudo + password);
		
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = utilisateurManager.getByEmailOrPseudo(emailOrPseudo);

		this.getServletContext().getRequestDispatcher("/WEB-INF/connection.jsp").forward(request, response);			
		
	
		if (utilisateur != null) {
														
			// Test du mot de passe
			if (utilisateurManager.passwordMatch(password, utilisateur)) {
				
				HttpSession session =request.getSession();
				Utilisateur user = utilisateurManager.getByEmailOrPseudo(emailOrPseudo);
				
				// Ajout d'une variable de session "user_id" pour autoriser l'accès aux autres pages du site
				session.setAttribute("user_id", user.getNoUtilisateur());
				session.setAttribute("prenom", user.getPrenom());
				session.setAttribute("nom", user.getNom());
				session.setAttribute("rue", user.getRue());
				session.setAttribute("codePostale", user.getCodePostal());
				session.setAttribute("ville", user.getVille());
				session.setAttribute("credit", user.getCredit());
				session.setAttribute("administrateur", user.getAdministrateur());
				
				System.out.println("Le mot passe correspond nous avons inséré l'ID Dans la session");

			} else {
				request.setAttribute("error", "Mot de passe incorrect");
			}

		} else {
			request.setAttribute("error", "Utilisateur incorrect");
		}
	}
}