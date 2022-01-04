<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="fr.eni.enchere.exceptions.GestionException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vendre un nouvel article</title>
</head>

<body style=" width: 60%; border : 2px solid black; margin-left : 10%; padding : 8px;">

<!-- Inclusion de la barre horizontale de navigation -->
<%@ include file="TopNavigation.jsp" %>

<h3 style="margin-left :40%"> Nouvelle Vente </h3>

<!-- affichage des codes erreurs récupérés dans le Catch de la request POST -->
	<c:forEach items="${listExce}" var="CodeExcep">
		<p>${GestionException.getMessageErreur(CodeExcep)}</p>
	</c:forEach>

<c:if test="${empty sessionScope.user_id }">
	<p> Vous n'êtes pas connecté.<br>
		Merci de vous connecter avant de pouvoir accèder à l'ajout d'article.</p>
</c:if>

<c:if test="${!empty sessionScope.user_id }">
	<form style="margin-left :10%; width :60%" name="VendreArticle" method="post">
	
	  <label for="nomArticle">Nom de l'article : </label>
	  <input type="text" name="nomArticle" id="nomArticle" placeholder="..." autofocus required> <br><br>
	
	  <label for="description">Descrition :</label>
	  <textarea name="description" id="description" cols="30" rows="10" placeholder="Desciption du produit..."></textarea><br><br>
	
	   <label for="categorie"> Catégorie de l'ogjet : </label>
	   
	   <!-- On utilise ici la liste des catégories récupérer dans la BDD -->
	   <select name="categorie" id="categorie">
	   		<c:forEach items="${listCatUse}" var="categorieTransfert"><Option value="${categorieTransfert.libelle}"> ${categorieTransfert.libelle} </Option></c:forEach>
	   </select><br><br>
	
	  <label for="photoArticle">Photo de l'article </label>
	  <input type="file" name="photoArticle" id="photoArticle"> <br><br>
	  
	  <label for="prixDepart">Mise à prix : </label>
	  <input type="number" min="0" step="5" name="prixDepart" id="prixDepart" required> <br><br>
	    
	  <label for="dateDeDebut">Début de l'enchère : </label>
	  <input type="date" name="dateDeDebut" id="dateDeDebut" required><br><br> 
	  
	  <label for="dateDeFin">Fin de l'enchère : </label>
	  <input type="date" name="dateDeFin" id="dateDeFin" required><br><br> 
	        
		<fieldset style="max-width: 400px;">
		<legend> Retrait </legend>
		      
		<label for="rue">Rue : </label>
		<input type="text" name="rue" id="rue" value="${sessionScope.rue}" placeholder="ex: 135 avenue Foche" required> <br><br>
				
		<label for="codePostal">Code Postal : </label>
		<input type="text" name="codePostal" id="codePostal" value="${sessionScope.codePostal}" placeholder="ex: 75052" required> <br><br>
				
		<label for="ville">Ville : </label>
		<input type="text" name="ville" id="ville" value="${sessionScope.ville}" placeholder="ex: Paris" required> <br><br>
		      
		</fieldset>
	    
		<div>
	      <input type="submit" value="enregistrer" name="btEnregistrer" title="Enregistrer" >
	      <input type="reset" value="annuler" name="btAnnuler" title="Annuler" >
		</div>
	</form>
</c:if>


</body>

</html>