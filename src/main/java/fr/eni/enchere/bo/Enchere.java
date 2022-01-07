package fr.eni.enchere.bo;

import java.time.LocalDateTime;

public class Enchere {

		private int no_Enchere;
	
		private int no_Article;
		
		private int no_Utilisateur;
		
		private LocalDateTime dateEnchere;
		
		private int montantEnchere;
		
		
		public Enchere() {}
		
		public Enchere(int montantEnchere) {
			this.montantEnchere = montantEnchere;
		}
	
		public Enchere(LocalDateTime dateEnchere, int montantEnchere, int no_Article, int no_Utilisateur) {
			this.dateEnchere = dateEnchere;
			this.montantEnchere = montantEnchere;
			this.no_Article = no_Article;
			this.no_Utilisateur = no_Utilisateur;
		}
		
		public Enchere(int no_Enchere, LocalDateTime dateEnchere, int montantEnchere, int no_Article, int no_Utilisateur) {
			this.no_Enchere = no_Enchere;
			this.dateEnchere = dateEnchere;
			this.montantEnchere = montantEnchere;
			this.no_Article = no_Article;
			this.no_Utilisateur = no_Utilisateur;
		}

		//Getter et Setter
		//Nom en Id au lieu de no_ pour faire la différence
		public int getIdEnchere() {
			return no_Enchere;
		}

		public void setIdEnchere(int no_Enchere) {
			this.no_Enchere = no_Enchere;
		}

		public LocalDateTime getDateEnchere() {
			return dateEnchere;
		}

		
		public void setDateEnchere(LocalDateTime dateEnchere) {
			this.dateEnchere = dateEnchere;
		}

		
		public int getMontantEnchere() {
			return montantEnchere;
		}

		public void setMontantEnchere(int montantEnchere) {
			this.montantEnchere = montantEnchere;
		}
	
		
		public int getIdArticle() {
			return no_Article;
		}

		
		public void setIdArticle(int no_Article) {
			this.no_Article = no_Article;
		}

		
		public int getIdUtilisateur() {
			return no_Utilisateur;
		}

		
		public void setIdUtilisateur(int no_Utilisateur) {
			this.no_Utilisateur = no_Utilisateur;
		}

// Tostring
		@Override
		public String toString() {
			return "Enchere [enchere= " + no_Enchere + " article=" + no_Article + ", utilisateur=" + no_Utilisateur + ", dateEnchere=" + dateEnchere
					+ ", montantEnchere=" + montantEnchere + "]";
		}

	}
	