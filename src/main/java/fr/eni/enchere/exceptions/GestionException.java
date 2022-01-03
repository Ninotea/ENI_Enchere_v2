package fr.eni.enchere.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// TEST PUSH
/**
 * 
 * @author Administrator
 *
 * Cette classe permet de recenser l'ensemble des erreurs (par leur code) pouvant survenir lors d'un traitement
 * quel que soit la couche à l'origine.
 */
public class GestionException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<Integer> listeCodesErreur;
	
	// Lecture du fichier properties servant à récupérer les codes erreurs et leurs valeures
	private static ResourceBundle resourceBun;
	static
	{
		try
		{
			resourceBun = ResourceBundle.getBundle("fr.eni.enchere.exceptions.messages_erreur");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// recupération des messages d'erreur dans la variable message.
	public static  String getMessageErreur(int code)
	{
		String message="";
		try
		{
			if(resourceBun!=null)
			{
				message = resourceBun.getString(String.valueOf(code));
			}
			else
			{
				message="Problème à la lecture du fichier contenant les messages";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			message="Une erreur inconnue est survenue";
		}
		return message;
	}
	
	// Declaration de la liste de code erreurs
	public GestionException() {
		super();
		this.listeCodesErreur=new ArrayList<>();
	}
	
	// Fonction ajout d'erreur à la liste
	public void ajouterErreur(int code)
	{
		if(!this.listeCodesErreur.contains(code))
		{
			this.listeCodesErreur.add(code);
			//System.out.println("une erreur à été ajoutée");
		}
	}
	
	// Si la liste est non vide
	public boolean hasErreurs()
	{
		return this.listeCodesErreur.size()>0;
	}
	
	// Renvoie la liste de code d'erreur
	public List<Integer> getListeCodesErreur()
	{
		//System.out.println("une demande de la liste de code à été réalisée");
		//System.out.println(listeCodesErreur.toString());
		return this.listeCodesErreur;
	}

}
