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
		Merci de vous connecter avant de pouvoir accèder à l'ajout d'article.</p>
</c:if>

<c:if test="${!empty sessionScope.user_id }">


	<div style="margin-left :10%; width :60%">
	
	  <label>Nom de l'article : </label>
	  <span>${requestScope.articleDetail.nomArticle} </span> <br><br>
	
	  <label>Descrition : </label>
	  <p>${requestScope.articleDetail.description}</p>
	
	   <label>Catégorie de l'ogjet : </label>
	   
	   <!-- On utilise ici la liste des catégories récupérer dans la BDD -->
	   		<c:forEach items="${listCatUse}" var="catTrans">
		   		<c:choose>
		   			<c:when test="${catTrans.libelle==articleDetail.categorie}"><span> ${catTrans.libelle} </span></c:when>
		   			<c:otherwise></c:otherwise>
		   		</c:choose>
	   		</c:forEach><br>
	   
	   <!-- AJOUTER AU BESOIN LES PHOTOS QUAND ELLES SERONT FONCTIONNELLES -->
	  
	  <label>Mise à prix : </label>
	  <span>${requestScope.articleDetail.miseAPrix} </span><br>
	    
	  <label>Début de l'enchère : </label>
	  <span>${requestScope.articleDetail.dateDebutEnchere} </span><br> 
	  
	  <label>Fin de l'enchère : </label>
	  <span>${requestScope.articleDetail.dateFinEnchere} </span><br><br> 
	        
		<fieldset style="max-width: 400px;">
		<legend> Retrait </legend>
		      
		<label>Rue : </label>
		<span>${requestScope.articleDetail.vendeur.rue} </span><br>
				
		<label>Code Postal : </label>
		<span>${requestScope.articleDetail.vendeur.codePostal} </span><br>
				
		<label>Ville : </label>
		<span>${requestScope.articleDetail.vendeur.ville} </span><br>
		      
		</fieldset><br>
		
	<form name="VendreArticle" method="post">
			<input type="text" name="noArt" value="${requestScope.articleDetail.noArticle}" hidden="hidden">	  
			<label for="proposition">Ma Proposition : </label>
	  		<input type="number" min="0" step="5" name="proposition" id="proposition" value="5000" required>
	      	<input type="submit" value="Encherir" name="encherir">
		
	</form>
	</div>
</c:if>


</body>

</html>