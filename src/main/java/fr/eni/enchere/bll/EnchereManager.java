package fr.eni.enchere.bll;



import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.exceptions.GestionException;
import fr.eni.enchere.dal.DAOFactory;
import fr.eni.enchere.dal.EnchereDAO;
import fr.eni.enchere.dal.EnchereDAOJdbcImpl;

public class EnchereManager {
		//Singleton pour l'instance
	private static EnchereManager instance;
	private EnchereDAO EnchereDAO;
	public static EnchereManager getInstance() {
		if (instance == null) {
			return new EnchereManager();
		}
		return instance;
	}

	//Dao factory
	public EnchereManager() {
		EnchereDAO = DAOFactory.getEnchereDAO();
	}
	//Méthode SelectEnchereWhereID
public Enchere SelectEnchereWhereID (int no_Article) throws GestionException {
		
			try {
				return EnchereDAO.SelectEnchereWhereID(no_Article);
			} catch (GestionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return SelectEnchereWhereID(no_Article);
	}
	//Récupération des encheres
public List<Enchere> recupererEnchere() throws GestionException{
	
	List<Enchere> listeEnchere = null;
	GestionException exception = new GestionException();
	
	if(EnchereDAO.SelectEnchereAll().size() == 0) {
		exception.ajouterErreur(CodesResultatBLL.MANAGER_ENCHERE_NULL);
	}
	listeEnchere = EnchereDAO.SelectEnchereAll();

	return listeEnchere;	
}
}