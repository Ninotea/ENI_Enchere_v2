package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import fr.eni.enchere.bll.ArticleManager;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exceptions.GestionException;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String SELECT_BY_NO_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article= ?"	;
	
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES(date_enchere, montant_enchere, no_article, no_utilisateur) VALUES(?,?,?,?)";
	
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET date_enchere = ?, montant_enchere = ?, no_utilisateur = ? WHERE no_article = ?";	
	
	public EnchereDAOJdbcImpl() {
		
	}

	
	public Enchere selectByArticle(int no_Article) throws GestionException {
		Enchere enchere = null;
		LocalDateTime dateEnchere = null;
		int montantEnchere = 0;
		int no_Utilisateur = 0;
		
		
		
		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pStmt = cnx.prepareStatement(SELECT_BY_NO_ARTICLE);
			pStmt.setInt(1, no_Article);

			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				enchere = map(rs);
			}
			
			cnx.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		 enchere = new Enchere(dateEnchere, montantEnchere, no_Article, no_Utilisateur);
		 return enchere;
	}
	
	
	
	public void insert(Enchere nouvelEnchere) throws GestionException {
		
		
			try (Connection cnx = ConnectionProvider.getConnection()) {
				PreparedStatement pStmt = cnx.prepareStatement(INSERT_ENCHERE, Statement.RETURN_GENERATED_KEYS);
				pStmt.setTimestamp(1, Timestamp.valueOf(nouvelEnchere.getDateEnchere()));
				pStmt.setInt(2, nouvelEnchere.getMontantEnchere());
				pStmt.setInt(3, nouvelEnchere.getIdArticle());
				pStmt.setInt(4, nouvelEnchere.getIdUtilisateur());
			
				pStmt.executeUpdate();

				ResultSet rs = pStmt.getGeneratedKeys();
				if (rs.next()) {
					int noEnchere = rs.getInt(1); 
					nouvelEnchere.setIdUtilisateur(noEnchere);
				}
				
				cnx.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

	
	public void update(Enchere nouvelEnchere) throws GestionException {
		
		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement statement = cnx.prepareStatement(UPDATE_ENCHERE);

		
			statement.setTimestamp(1, Timestamp.valueOf(nouvelEnchere.getDateEnchere()));
			statement.setInt(2, nouvelEnchere.getMontantEnchere());
			statement.setInt(3, nouvelEnchere.getIdUtilisateur());
			statement.setInt(4, nouvelEnchere.getIdArticle());
			
			
			
			statement.executeUpdate();
			
			cnx.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
		
	
	private Enchere map(ResultSet rs) throws SQLException {
		Enchere enchere = null;
		LocalDateTime dateEnchere = rs.getTimestamp("date_enchere").toLocalDateTime();
		int montantEnchere = rs.getInt("montant_enchere");
		int no_Article = rs.getInt("no_article");
		int no_Utilisateur = rs.getInt("no_utilisateur");
	

		enchere = new Enchere(dateEnchere, montantEnchere, no_Article, no_Utilisateur);
		return enchere;
	

	}


	@Override
	public List<Enchere> SelectEnchereAll() throws GestionException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Enchere SelectEnchereWhereID(int no_Article) throws GestionException{
		
		Enchere enchereWhereId = new Enchere();
		EnchereManager enchManag = new EnchereManager();
		
		try(Connection cnx = ConnectionProvider.getConnection()) 
		{				
			Statement statement = cnx.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_BY_NO_ARTICLE + no_Article);

			while(rs.next()) {
				int no_article  ;
		
				// RÃ©cupÃ©ration de l'enchere en fonction de l'id article
				EnchereManager enchereManager = new EnchereManager();
				List<Enchere> listEnchere = enchereManager.recupererEnchere();	
				
					for (Enchere enchere : listEnchere) {
						if(enchere.getIdArticle() == rs.getInt("no_article")) {
							no_article = enchere.getIdArticle();
						} 
					}
				
					Enchere enchereResultSet = new Enchere(
							//Changer le getDate to LocalDateTime
						rs.getTimestamp("date_enchere").toLocalDateTime(),
						rs.getInt("montant_enchere"),
						rs.getInt("no_article"),
						rs.getInt("no_utilisateur")

);	

				enchereWhereId = enchereResultSet;
			}
		
		}
		catch(SQLException e)
		{
			
		}
		return enchereWhereId;
	}

}