<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des Enchères</title>
</head>

<body style=" width: 60%; border : 2px solid black; margin-left : 10%; padding : 8px;">

<!-- Inclusion de la barre horizontale de navigation -->
<%@ include file="TopNavigation.jsp" %>

<!-- affichage des codes erreurs récupérés dans le Catch de la request POST -->

	<c:forEach items="${listExce}" var="CodeExcep">
		<p>${GestionException.getMessageErreur(CodeExcep)}</p>
	</c:forEach> 

<h3 style="margin-left :40%"> Liste des enchères </h3>

<c:if test="${!empty listeArticleEnchere}">
	<p>Nous avons trouvé ${listeArticleEnchere.size()} articles !</p>
</c:if>

<form name="ListerArticles" method="post">

	<div style="display: flex;">
		<div style="display: flex; flex-direction: column; width :60%;">
			Filtres :
			<input type="text" name="champRecherche" id="champRecherche" value="${requestScope.motRecherche}" placeholder="Rechercher un article ICI ..."> 
			<label for="categorie">Catégorie : </label>
			<select name="categorie" id="categorie">
					<Option value="Toutes" selected="selected"> Toutes </Option>
			   		<c:forEach items="${listCatUse}" var="categorie">
				   		<c:choose>
				   			<c:when test="${categorie.libelle == requestScope.catRecherche}">
				   				<Option value="${categorie.libelle}"  selected="selected"> ${categorie.libelle} </Option>
				   			</c:when>
				   			<c:otherwise><Option value="${categorie.libelle}"> ${categorie.libelle} </Option></c:otherwise>
			   			</c:choose>
			   		</c:forEach>
			</select>
			
			<c:choose>
			<c:when test="${!empty sessionScope.user_id }">
				<div style="display: flex">
				<fieldset style="width : 50%; border: none;">
					<input type="radio" name="typeArticle" id="achat" value="achat"
						<c:if test="${requestScope.typeArticle.equals('achat') }">checked="checked"</c:if>>
						<label for="achat">Mes Achats </label><br>
						
					<input type="checkbox" name="encEnCours" id="encEnCours"
						<c:if test="${!requestScope.typeArticle.equals('achat') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.encEnCours}">checked="checked"</c:if>>
						<label for="encOuverte"> enchères en cours </label><br>
						
					<input type="checkbox" name="encNonDeb" id="encNonDeb"
						<c:if test="${!requestScope.typeArticle.equals('achat') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.encNonDeb}">checked="checked"</c:if>>
						<label for="encEnCours"> enchères non débutées </label><br>
						
					<input type="checkbox" name="encRemporte" id="encRemporte"
						<c:if test="${!requestScope.typeArticle.equals('achat') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.encRemporte}">checked="checked"</c:if>>
						<label for="encRemporte"> enchères remportées </label><br>
						
					<input type="checkbox" name="encNonRemporte" id="encNonRemporte"
						<c:if test="${!requestScope.typeArticle.equals('achat') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.encNonRemporte}">checked="checked"</c:if>>
						<label for="encRemporte"> enchères terminées sans acquéreurs </label><br>
				</fieldset>
				
				<fieldset style="width : 50%; border: none;">
					<input type="radio" name="typeArticle" id="vente" value="vente"
						<c:if test="${requestScope.typeArticle.equals('vente') }">checked="checked"</c:if>>
						<label for="vente"> Mes Ventes </label><br>
						
					<input type="checkbox" name="venEnCours" id="venEnCours"
						<c:if test="${!requestScope.typeArticle.equals('vente') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.venEnCours}">checked="checked"</c:if>>
						<label for="venEnCours"> ventes en cours </label><br>
						
					<input type="checkbox" name="venNonDeb" id="venNonDeb"
						<c:if test="${!requestScope.typeArticle.equals('vente') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.venNonDeb}">checked="checked"</c:if>>
						<label for="venNonDeb"> ventes non débutées </label><br>
						
					<input type="checkbox" name="venTerm" id="venTerm"
						<c:if test="${!requestScope.typeArticle.equals('vente') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.venTerm}">checked="checked"</c:if>>
						<label for="venTerm"> ventes terminées </label><br>
						
					<input type="checkbox" name="venNonRemporte" id="venNonRemporte"
						<c:if test="${!requestScope.typeArticle.equals('vente') }">disabled="disabled"</c:if>
						<c:if test="${requestScope.venNonRemporte}">checked="checked"</c:if>>
						<label for="venTerm"> ventes terminées sans acquéreurs </label><br>
				</fieldset>
				</div>
			</c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
			
		</div>
		<p>
			<span style="margin: 30px"><input type="submit" value="Rechercher" name="btRechercher"></span>		
		</p>	
	</div>
</form>	
		


<div style = "display: flex; flex-wrap: wrap;" >
	<c:if test="${!empty listeArticleEnchere}">
		<c:forEach items="${listeArticleEnchere}" var="articleAff">
		  	
			<div style ="width : 50% ;">
				<p>
					<strong>${articleAff.getNomArticle()}</strong> <br>
					Prix : ${articleAff.getMiseAPrix()} points <br> <!-- à remplacer par prix enchère  -->
					Fin de l'enchère : ${articleAff.getDateFinEnchere()} <br><br>
					Vendeur : ${articleAff.getVendeur().getPseudo()} <br><br>
					Etat vente : ${articleAff.etatVente }<br>
					
				</p>
				<p>
					<c:if test="${!empty sessionScope.user_id }">
						<form name="details" method="post">
							<input type="text" name="detailNoArt" value="${articleAff.getNoArticle()}" hidden="hidden"> <!-- hidden pour transmettre le parametre noArticle sans l'afficher -->
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