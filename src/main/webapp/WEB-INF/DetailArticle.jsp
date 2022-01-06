<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="fr.eni.enchere.exceptions.GestionException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Détail de l'article</title>
</head>

<body style=" width: 60%; border : 2px solid black; margin-left : 10%; padding : 8px;">

<!-- Inclusion de la barre horizontale de navigation -->
<%@ include file="TopNavigation.jsp" %>

<h3 style="margin-left :40%"> Détail de l'article en tant que vendeur </h3>

<!-- affichage des codes erreurs récupérés dans le Catch de la request POST -->
	<c:forEach items="${listExce}" var="CodeExcep">
		<p>${GestionException.getMessageErreur(CodeExcep)}</p>
	</c:forEach>

<c:if test="${empty sessionScope.user_id }">
	<p> Vous n'êtes pas connecté.<br>
		Merci de vous connecter avant de pouvoir accèder au détail de l'article.</p>
</c:if>

<c:if test="${!empty sessionScope.user_id }">

	<form style="margin-left :10%; width :60%" name="formDetailArticle" method="post">
	
	  <label for="nomArticle">Nom de l'article : </label>
	  <input type="text" name="nomArticle" id="nomArticle" value="${requestScope.articleDetail.nomArticle}" required> <br><br>
	
	  <label for="description">Descrition :</label>
	  <textarea name="description" id="description" cols="30" rows="10">${requestScope.articleDetail.description}</textarea><br><br>
	
	   <label for="categorie"> Catégorie de l'ogjet : </label>
	   
	   <!-- On utilise ici la liste des catégories récupérer dans la BDD -->
	   <select name="categorie" id="categorie">
	   		<c:forEach items="${listCatUse}" var="catTrans">
		   		<c:choose>
		   			<c:when test="${catTrans.libelle==articleDetail.categorie}"><Option value="${catTrans.libelle}"  selected="selected"> ${catTrans.libelle} </Option></c:when>
		   			<c:otherwise><Option value="${catTrans.libelle}"> ${catTrans.libelle} </Option></c:otherwise>
		   		</c:choose>
	   		</c:forEach>
	   </select><br><br>
	   
	   <!-- AJOUTER AU BESOIN LES PHOTOS QUAND ELLES SERONT FONCTIONNELLES -->
	  
	  <label for="prixDepart">Mise à prix : </label>
	  <input type="number" min="0" step="5" name="prixDepart" id="prixDepart" value="${requestScope.articleDetail.miseAPrix}" required> <br><br>
	    
	  <label for="dateDeDebut">Début de l'enchère : </label>
	  <input type="date" name="dateDeDebut" id="dateDeDebut" value="${requestScope.articleDetail.dateDebutEnchere}" required><br><br> 
	  
	  <label for="dateDeFin">Fin de l'enchère : </label>
	  <input type="date" name="dateDeFin" id="dateDeFin" value="${requestScope.articleDetail.dateFinEnchere}" required><br><br> 
	        
		<fieldset style="max-width: 400px;">
		<legend> Retrait </legend>
		      
		<label for="rue">Rue : </label>
		<input type="text" name="rue" id="rue" value="${requestScope.articleDetail.vendeur.rue}" required> <br><br>
				
		<label for="codePostal">Code Postal : </label>
		<input type="text" name="codePostal" id="codePostal" value="${requestScope.articleDetail.vendeur.codePostal}" required> <br><br>
				
		<label for="ville">Ville : </label>
		<input type="text" name="ville" id="ville" value="${requestScope.articleDetail.vendeur.ville}" required> <br><br>
		      
		</fieldset>
	    
		<div>
			<input type="text" name="noArticle" value="${requestScope.articleDetail.noArticle}" hidden="hidden">
			<input type="submit" value="Modifer" name="modifierVente">
			<input type="submit" value="Annuler la vente" name="annulerVente">
		</div>
	</form>
</c:if>


</body>

</html>