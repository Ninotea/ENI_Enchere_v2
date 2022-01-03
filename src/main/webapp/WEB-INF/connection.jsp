<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST" > <!-- action="<%--=request.getContextPath()--%>/connectionUtilisateur" -->

<label for="identifiant">Identifiant </label>
<input type="text" name="identifiant" value="${sessionScope.user_id}">
<br/>
mot de passe <input type="password" name="motDePasse">

<br/>
<input type="submit" name="action" value="valider">
<input type="submit" name="action" value="annuler">
</form>
</body>
</html>