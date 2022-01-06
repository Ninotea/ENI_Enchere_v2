<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profil</title>
</head>
<body>

<div style="margin-left :10%; width :60% ; border : 2px solid black; padding: 10px">

<%@ include file="TopNavigation.jsp" %>

<h3 style="margin-left :20%;">Profil Utilisateur de ${requestScope.profil.pseudo}</h3>

<c:if test="${empty requestScope.modifier}">
	
	  <label>Nom : </label>
	  <span>${requestScope.profil.nom} </span> <br>
	
	  <label>Prenom : </label>
	  <span>${requestScope.profil.prenom}</span><br>

	  <label>Téléphone : </label>
	  <span>${requestScope.profil.telephone} </span><br>
	    
	  <label>Mail : </label>
	  <span>${requestScope.profil.email} </span><br> 
	  
	  <label>Crédit : </label>
	  <span>${requestScope.profil.credit} </span><br><br> 
	        
		<fieldset style="max-width: 400px;">
		<legend> Adresse </legend>
		      
		<label>Rue : </label>
		<span>${requestScope.profil.rue} </span><br>
				
		<label>Code Postal : </label>
		<span>${requestScope.profil.codePostal} </span><br>
				
		<label>Ville : </label>
		<span>${requestScope.profil.ville} </span><br>
		      
		</fieldset><br>
		
</c:if>	
	
		<form method="POST">
			<c:if test="${!empty requestScope.modifier}">
				<div> 
					<label for="pseudo">Pseudo : </label><input type="text" name="pseudo" value="${requestScope.profil.pseudo}"><br>
					<label for="nom">Nom : </label><input type="text" name="nom" value="${requestScope.profil.nom}"><br>
					<label for="prenom">Prénom : </label><input type="text" name="prenom" value="${requestScope.profil.prenom}"><br>
					<label for="email">Email : </label><input type="text" name="email" value="${requestScope.profil.email}"><br>
					<label for="telephone">Téléphone : </label><input type="text" name="telephone" value="${requestScope.profil.telephone}"><br>
					<label for="motDePasse">Nouveau mot de passe : </label><input type="text" name="motDePasse"><br>
					<label for="confirmation">Confirmation nouveau mot de passe  : </label><input type="text" name="confirmation"><br>
					<fieldset style="max-width: 400px;">
					<legend> Adresse </legend>
						<label for="rue">Rue : </label><input type="text" name="rue" value="${requestScope.profil.rue}"><br>
						<label for="codePostal">Code postal : </label><input type="text" name="codePostal" value="${requestScope.profil.codePostal}"><br>
						<label for="ville">Ville : </label><input type="text" name="ville" value="${requestScope.profil.ville}"><br>
					</fieldset><br>
				</div>
				
			</c:if>
			<c:if test="${empty requestScope.modifier}"><input type="submit" name="modifier" value="Modifier le profil"></c:if>
			<c:if test="${empty requestScope.modifier}"><input type="submit" name="supprimer" value="Supprimer le profil"></c:if>
			<c:if test="${!empty requestScope.modifier}"><input type="submit" name="valider" value="Valider Modifications"></c:if>
			<c:if test="${!empty requestScope.modifier}"><input type="submit" name="annuler" value="Annuler Modifications"></c:if>
		</form>
		
</div>

</body>
</html>