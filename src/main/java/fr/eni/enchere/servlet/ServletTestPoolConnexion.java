package fr.eni.enchere.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;




@WebServlet("/ServletTestPoolConnexion")
public class ServletTestPoolConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletTestPoolConnexion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		try {
			Context context = new InitialContext();
			// Recherche de la DataSource
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
			//Demande de connexion. La methode getConnection met la demande ne attente tant qu'il n'y a pas de dispo dans le pool
			Connection cnx = dataSource.getConnection();
			out.print("La connexion est " + (cnx.isClosed() ? "Fermée" : "Ouverte")+".");
			//Libérer la connexion quand on en a plus besoin
			cnx.isClosed(); // ICI la connexion n'est pas fermée mais remise dans le pool
			
			
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println("Une erreur est survenue lors de la conenxion à la base de données : " + e.getMessage());
		}
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
