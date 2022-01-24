<!DOCTYPE html>
<html>
<head>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

<meta charset="UTF-8">
<title>Liste des Enchères</title>

</head>


<body class="">

<div class="container col-lg-8 col-lg-offset-2 col-md-11 col-md-offset-1 lead-2" style="background: linear-gradient(to bottom, rgba(30,87,153,1) 0%,rgba(125,185,232,0) 100%); border-radius: 10px;">

	<!-- Inclusion de la barre horizontale de navigation -->
	<%@ include file="TopNavigation.jsp" %>
	
	<!-- affichage des codes erreurs récupérés dans le Catch de la request POST -->
	
		<c:forEach items="${listExce}" var="CodeExcep">
			<p>${GestionException.getMessageErreur(CodeExcep)}</p>
		</c:forEach> 
	
	<h3 class="col-md-offset-3" style="color:white;"> Liste des enchères </h3>
	
	<c:if test="${!empty listeArticleEnchere}">
		<div class="col-xs-12" style="color:white;background: linear-gradient(to bottom, rgba(125,126,125,1) 0%,rgba(14,14,14,1) 100%); border: 1px solid white; border-radius: 5px;margin : 10px; padding : 5px;">Nous avons trouvé ${listeArticleEnchere.size()} articles !</div>
	</c:if>	
	
	
	<form name="ListerArticles" method="post">
	
		<div class="d-flex flex-row col-xs-12" style="color:white;background: linear-gradient(to bottom, rgba(125,126,125,1) 0%,rgba(14,14,14,1) 100%); border: 1px solid white;border-radius: 10px;margin : 10px; padding : 5px;">
			<div class="col-md-11 ">
			
				<div class="d-flex flex-row col-md-6 col-xs-8 "> 
					<p class="col-xs-3 " style="min-width: 120px;">
						<label for="champRecherche"> Filtres :</label><br>
						<label for="categorie">Catégorie : </label> 
					</p>
					<p class="col-xs-2 ">
						<input type="text" name="champRecherche" id="champRecherche" value="${requestScope.motRecherche}" placeholder="Rechercher un article ICI ..."> <br>
						<select name="categorie" id="categorie" style="color:black;">
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
					</p>
				</div>	
				<p class="col-md-1 col-xs-12" >
					<span><input class="btn btn-default" type="submit" value="Rechercher" name="btRechercher"></span>		
				</p>
			</div>
			<div>
				<c:choose>
				<c:when test="${!empty sessionScope.user_id }">
				
					<div class="d-flex flex-md-row flex-xs-column col-md-12 ">
					<fieldset class="col-md-5 col-xs-10">
					
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
							
						<span><input type="checkbox" name="encNonRemporte" id="encNonRemporte"
							<c:if test="${!requestScope.typeArticle.equals('achat') }">disabled="disabled"</c:if>
							<c:if test="${requestScope.encNonRemporte}">checked="checked"</c:if>>
							<label for="encRemporte"> enchères terminées sans acquéreurs </label></span>
					</fieldset>
					
					<fieldset class="col-md-5 col-xs-10">
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
		</div>
	</form>	

	<div class="d-flex flex-row col-xs-12 " >
		<c:if test="${!empty listeArticleEnchere}">
			<c:forEach items="${listeArticleEnchere}" var="articleAff">
			  	
				<div class="col-md-5 " style="background: linear-gradient(to bottom, rgba(248,255,232,1) 0%,rgba(227,245,171,1) 33%,rgba(183,223,45,1) 100%); border: 1px solid white; border-radius: 10px; margin : 10px; padding : 15px;">
					<div>
						<p ><strong>${articleAff.getNomArticle()}</strong> <br>
						</p>
						<p>
						Prix : ${articleAff.getMiseAPrix()} points <br>
						Fin de l'enchère : ${articleAff.getDateFinEnchere()}
						<p>
						Vendeur : ${articleAff.getVendeur().getPseudo()}<br>
						Etat vente : ${articleAff.etatVente }</p>
						
					</div>
					<p>
						<c:if test="${!empty sessionScope.user_id }">
							<form name="details" method="post">
								<input type="text" name="detailNoArt" value="${articleAff.getNoArticle()}" hidden="hidden"> <!-- hidden pour transmettre le parametre noArticle sans l'afficher -->
								<input class="btn-xs btn-default" type="submit" value="Details" name="details" >
							</form>
						</c:if>
					</p>
				</div>
			</c:forEach> 
		</c:if>
	</div>
	
</div>

</body>
</html>