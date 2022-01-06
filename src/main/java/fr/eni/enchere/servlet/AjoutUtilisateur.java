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
public class AjoutUtilisateur extends HttpServlet {
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

        RequestDispatcher rd = null;
        String error = null;

        UtilisateurManager utilisateurManager = new UtilisateurManager();

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
        String passwordHash = utilisateurManager.encryptPassword(password);
        
        System.out.println(pseudo + " est le pseudo récupéré");

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(password);

        if (!password.equals(confirmPassword)) {
            error = "Les mots de passe ne correspondent pas";

        } else if (!utilisateurManager.isPseudoAvailable(pseudo)) {
            error = "Le pseudo existe déjà";

        } else if (!utilisateurManager.isEmailAvailable(email)) {
            error = "L'email existe déjà";

        } else if (!matcher.matches()) {
            error = "Le pseudo ne doit contenir que des caractères alphanumériques";

        } else {
            Utilisateur utilisateur = new Utilisateur();
            	utilisateur.setPseudo(pseudo);
            	utilisateur.setNom(nom);
            	utilisateur.setPrenom(prenom);
            	utilisateur.setEmail(email);
            	utilisateur.setTelephone(telephone);
            	utilisateur.setRue(rue);
            	utilisateur.setCodePostal(codePostal);
            	utilisateur.setVille(ville);
            	utilisateur.setMotDePasse(passwordHash);
            	
            System.out.println(utilisateur);
            try {
                utilisateurManager.ajouter(utilisateur);
            } catch (Exception e) {
            }
        }
        
        System.out.println("Les erreurs ICI : " + error);

       // Redirige vers la page inscription avec un message d'erreur
        if (error != null) {
            request.setAttribute("error", error);
            rd.forward(request, response);
        }

       response.sendRedirect(request.getContextPath() + "/ajoutUtilisateur");
    }
}