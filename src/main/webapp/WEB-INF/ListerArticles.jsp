<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="fr.eni.enchere.exceptions.GestionException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style=" width: 600px;">

<header >
<h1>ENI - Enchères</h1>
<h2>Liste des Enchères</h2>
</header>

<!-- affichage des codes erreurs récupérés dans le Catch de la request POST -->
<c:if test="${!empty listExce}">
	<c:forEach items="${listExce}" var="CodeExcep">
		<p>${GestionException.getMessageErreur(CodeExcep)}</p>
	</c:forEach> 
</c:if>

<form name="ListerArticles" method="post">
<div style="display: flex">
	<p style="display: flex; flex-direction: column">
		Filtres :
		<input type="text" name="champRecherche" id="champRecherche" placeholder="Rechercher un article ICI ..."> 
			<label for="categorie">Catégorie : </label>
			<select name="categorie" id="categorie">
					<Option value="Toutes" selected="selected"> Toutes </Option>
			   		<c:forEach items="${listCatUse}" var="categorieTransfert"><Option value="${categorieTransfert.libelle}"> ${categorieTransfert.libelle} </Option></c:forEach>
			</select>
	</p>
	<p>
		<span style="margin: 50px"><input type="submit" value="rechercher" name="btRechercher" title="Rechercher" ></span>		
	</p>
		
</div>

<div style = "display: flex; flex-wrap: wrap;" >
	<c:if test="${!empty listeArticleEnchere}">
		<c:forEach items="${listeArticleEnchere}" var="listArt">
			<p style ="width : 50% ;">
			<strong>${listArt.getNomArticle()}</strong> <br>
			Prix : ${listArt.getMiseAPrix()} points <br> <!-- à remplacer par prix enchère  -->
			Fin de l'enchère : ${listArt.getDateFinEnchere()} <br><br>
			Vendeur : ${listArt.getVendeur().getPseudo()} "pas encore fonctionnel"
			</p>
		</c:forEach> 
	</c:if>
</div>


</form>

</body>
</html>