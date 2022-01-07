package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.exceptions.GestionException;

public interface EnchereDAO {
	
	Enchere selectByArticle(int idArticle) throws GestionException;
	void update(Enchere nouvelEnchere) throws GestionException;
	void insert(Enchere nouvelEnchere) throws GestionException;
	public List<Enchere> SelectEnchereAll() throws GestionException;
	public Enchere SelectEnchereWhereID(int no_Article) throws GestionException;


}