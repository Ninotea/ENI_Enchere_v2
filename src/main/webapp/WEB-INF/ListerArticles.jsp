<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des Ench�res</title>
</head>

<body style=" width: 60%; border : 2px solid black; margin-left : 10%; padding : 8px;">

<!-- Inclusion de la barre horizontale de navigation -->
<%@ include file="TopNavigation.jsp" %>

<!-- affichage des codes erreurs r�cup�r�s dans le Catch de la request POST -->

	<c:forEach items="${listExce}" var="CodeExcep">
		<p>${GestionException.getMessageErreur(CodeExcep)}</p>
	</c:forEach> 

<h3 style="margin-left :40%"> Liste des ench�res </h3>



<form name="ListerArticles" method="post">

	<div style="display: flex;">
		<div style="display: flex; flex-direction: column; width :60%;">
			Filtres :
			<input type="text" name="champRecherche" id="champRecherche" placeholder="Rechercher un article ICI ..."> 
			<label for="categorie">Cat�gorie : </label>
			<select name="categorie" id="categorie">
					<Option value="Toutes" selected="selected"> Toutes </Option>
			   		<c:forEach items="${listCatUse}" var="categorieTransfert"><Option value="${categorieTransfert.libelle}"> ${categorieTransfert.libelle} </Option></c:forEach>
			</select>
			
			<c:if test="${!empty sessionScope.user_id }">
				<div style="display: flex">
				<fieldset style="width : 50%; border: none;">
					<input type="radio" name="typeArticle" id="achat" value="achat" checked="checked"><label for="achat"> Achats </label><br>
					<input type="checkbox" name="encOuverte" id="encOuverte" value="encOuverte" ><label for="encOuverte"> ench�res ouvertes </label><br>
					<input type="checkbox" name="encEnCours" id="encEnCours" value="encEnCours" ><label for="encEnCours"> mes ench�res en cours </label><br>
					<input type="checkbox" name="encRemporte" id="encRemporte" value="encRemporte" ><label for="encRemporte"> mes ench�res remport�es </label><br>
				</fieldset>
				<fieldset style="width : 50%; border: none;">
					<input type="radio" name="typeArticle" id="vente" value="vente"><label for="vente"> Mes Ventes </label><br>
					<input type="checkbox" name="venEnCours" id="venEnCours" value="venEnCours" ><label for="venEnCours"> ventes en cours </label><br>
					<input type="checkbox" name="venNonDeb" id="venNonDeb" value="venNonDeb" ><label for="venNonDeb"> ventes non d�but�es </label><br>
					<input type="checkbox" name="venTerm" id="venTerm" value="venTerm" ><label for="venTerm"> ventes termin�es </label><br>
				</fieldset>
				</div>
			</c:if>
			
		</div>
		<p>
			<span style="margin: 30px"><input type="submit" value="Rechercher" name="btRechercher"></span>		
		</p>	
	</div>
</form>	
		


<div style = "display: flex; flex-wrap: wrap;" >
	<c:if test="${!empty listeArticleEnchere}">
		<c:forEach items="${listeArticleEnchere}" var="listArt">
		  	
			<div style ="width : 50% ;">
				<p>
					<strong>${listArt.getNomArticle()}</strong> <br>
					Prix : ${listArt.getMiseAPrix()} points <br> <!-- � remplacer par prix ench�re  -->
					Fin de l'ench�re : ${listArt.getDateFinEnchere()} <br><br>
					Vendeur : ${listArt.getVendeur().getPseudo()} <br><br>
				</p>
				<p>
					<c:if test="${!empty sessionScope.user_id }">
						<form name="details" method="post">
							<input type="text" name="detailNoArt" value="${listArt.getNoArticle()}" hidden="hidden"> <!-- hidden pour transmettre le parametre noArticle sans l'afficher -->
							<input type="submit" value="Details" name="details" >
						</form>
					</c:if>
				</p>
			</div>
		</c:forEach> 
	</c:if>
</div>
	

</body>
</html>