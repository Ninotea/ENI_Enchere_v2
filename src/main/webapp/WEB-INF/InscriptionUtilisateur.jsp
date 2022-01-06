<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="fr.eni.enchere.exceptions.GestionException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription Utilisateur</title>
</head>

<body>

<c:if test="${!empty requestScope.error}">${requestScope.error}</c:if>

	<c:forEach items="${listExce}" var="CodeExcep">
		<p>${GestionException.getMessageErreur(CodeExcep)}</p>
	</c:forEach>

<form name="InscritionUtilisateur" method="post">
           <label for="lastName">Pseudo:</label>
                    <input type="text" class="form-control" id="pseudo" name="pseudo"
                           placeholder="Pseudo" required>
                <br>
                <label for="lastName">Nom:</label>
                    <input type="text" id="nom" name="nom"
                           placeholder="Nom" required>
               
<br>
            <div>
                <label for="firstName">Prénom:</label>
                    <input type="text" id="prenom" name="prenom"
                           placeholder="Prénom" required>
                </div>
                <label for="contact">Email:</label>
                <div>
                    <input type="email"id="email" name="email"
                           placeholder="Email" required>
                </div>
           

            <div>
                <label for="contact">Téléphone:</label>
                <div>
                    <input type="text" id="telephone" name="telephone"
                           placeholder="Téléphone">
                </div>
                <label for="contact">Rue:</label>
                <div>
                    <input type="text" id="rue" name="rue"
                           placeholder="Rue">
                </div>
            </div>

            <div>
                <label for="lastName">Code
                    postal:</label>
                <div>
                    <input type="text" id="codePostal" name="codePostal"
                           placeholder="Code Postal" required>
                </div>
                <label for="lastName">Ville:</label>
                <div>
                    <input type="text" class="form-control" name="ville"
                           placeholder="Ville" required>
                </div>
            </div>

            <div>
                <label for="motdepasse">Mot
                    de passe:</label>
                
                    <input type="password" id="motDePasse" name="motDePasse"
                           placeholder="Mot de passe" required>
                </div>
                <label for="confirmation">Confirmation:</label>
                
                    <input type="password" id="motDePasse" name="confirmation"
                           placeholder="Confirmation du mot de passe" required>
               
                    <button type="submit" class="btn btn-primary">
                            Créer
                    </button>
             
        </form>
   