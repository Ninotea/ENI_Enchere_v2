package fr.eni.enchere.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet("/ajoutUtilisateur")
public class AjoutUtilisateurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    /**
     * Page d'inscription à l'application
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/InscriptionUtilisateur.jsp");
        rd.forward(request, response);
    }

    /**
     * Tentative d'inscription d'un nouvel utilisateur
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	

		String error = null;

        UtilisateurManager userManag = UtilisateurManager.getInstance();

        String pseudo = request.getParameter("pseudo");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String password = request.getParameter("motDePasse");
        String confirmPassword = request.getParameter("confirmation");
        String passwordHash = userManag.encryptPassword(password);

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(password);
        

        if (!password.equals(confirmPassword)) {
            error = "Les mots de passe ne correspondent pas";

        } else if (!userManag.isPseudoAvailable(pseudo)) {
            error = "Le pseudo existe déjà";

        } else if (!userManag.isEmailAvailable(email)) {
            error = "L'email existe déjà";

        } else if (!matcher.matches()) {
            error = "Le pseudo ne doit contenir que des caractères alphanumériques";

        } else {
            Utilisateur user = new Utilisateur();
            	user.setPseudo(pseudo);
            	user.setNom(nom);
            	user.setPrenom(prenom);
            	user.setEmail(email);
            	user.setTelephone(telephone);
            	user.setRue(rue);
            	user.setCodePostal(codePostal);
            	user.setVille(ville);
            	user.setMotDePasse(passwordHash);
            	
            try {
                userManag.ajouter(user);
            } catch (Exception e) {
            }
        }

       // Redirige vers la page inscription avec un message d'erreur
        if (error != null) {
            request.setAttribute("error", error);
			this.getServletContext().getRequestDispatcher("/WEB-INF/InscriptionUtilisateur.jsp").forward(request, response);
        }

       response.sendRedirect(request.getContextPath() + "/ajoutUtilisateur");
    }
}