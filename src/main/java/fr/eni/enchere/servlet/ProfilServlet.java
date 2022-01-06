package fr.eni.enchere.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exceptions.GestionException;

/**
 * Servlet implementation class ProfilServlet
 */
@WebServlet("/MonProfil")
public class ProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UtilisateurManager userManager = UtilisateurManager.getInstance();
	Utilisateur currentUser = new Utilisateur();
       

    public ProfilServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		HttpSession session = request.getSession();
		int no_utilisateur = (int) session.getAttribute("user_id");
		currentUser = userManager.getById(no_utilisateur);
		
		
		
		request.setAttribute("profil", currentUser);
		this.getServletContext().getRequestDispatcher("/WEB-INF/Profil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		HttpSession session = request.getSession();
		int no_utilisateur = (int) session.getAttribute("user_id");
		currentUser = userManager.getById(no_utilisateur);
		String modifier = request.getParameter("modifier");
        String error = null;
		
		if (request.getParameter("valider") != null) {
			System.out.println("Validation de modification demandé");
			
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
	        String passwordHash = userManager.encryptPassword(password);

	        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
	        Matcher matcher = pattern.matcher(password);
	        
			if (!password.equals(confirmPassword)) {
	            error = "Les mots de passe ne correspondent pas";

	        }  else if (!userManager.isEmailAvailable(email)) {
		        	if(!email.equals(currentUser.getEmail())) {
		        		error = "L'email existe déjà";
		        	}
	        } else if (!matcher.matches()) {
	            error = "Le pseudo ne doit contenir que des caractères alphanumériques";

	        } else if(!pseudo.equals(currentUser.getPseudo())){
	        	
		        	try {
		        		userManager.isPseudoAvailable(pseudo);
		        	} catch (GestionException e1) {
						request.setAttribute("listExce",e1.getListeCodesErreur());
						pseudo = null;
						//this.getServletContext().getRequestDispatcher("/WEB-INF/InscriptionUtilisateur.jsp").forward(request, response);
					}
	        } 
			if (!pseudo.equals(null)){
	        	
		        		Utilisateur user = new Utilisateur();
		            	user.setNoUtilisateur(no_utilisateur);;
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
							userManager.upate(user);
						} catch (GestionException e) {
							error ="erreur a l'update";
						}   
	        }

			currentUser = userManager.getById(no_utilisateur);
		}
		if (request.getParameter("supprimer") != null) {
			// Ajouter methode Delete
		}
		


		request.setAttribute("modifier", modifier);
		request.setAttribute("profil", currentUser);
		this.getServletContext().getRequestDispatcher("/WEB-INF/Profil.jsp").forward(request, response);
	}

}
