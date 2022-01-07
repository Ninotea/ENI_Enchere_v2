<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>Insert title here</title>
</head>
<body>
<h3 class="col-xs-offset-1">ENI-Enchères</h3>
	<div class="container col-xs-6 col-xs-offset-3" >
	<div class="row d-flex flex-row" style="border:200px solid #fff;">
	<form method="POST" > <!-- action="<%--=request.getContextPath()--%>/connectionUtilisateur" -->
		<p class="col-xs-3"><label for="identifiant">Identifiant </label><br>
		<label for="motDePasse">Mot de passe  </label></p>
		
		<p class="col-xs-9" style=""><input type="text" name="identifiant" ><br/>
		<input type="password" name="motDePasse"></p>
		<input class="col-xs-offset-2 btn btn-success" type="submit" name="action" value="valider">
		<input class="btn btn-warning" type="submit" name="action" value="annuler"><br/>
	</form>
	
	<form method="POST" > <!-- /Inscription est la terminaison URL indiquée dans le Web.xml -->
		<input style="margin:30px padding:20px" class="col-xs-offset-2 btn btn-primary btn-lg" type="submit" name="redirection" value="Inscription"><br/>
	</form>
</div>
</div>
</body>
</html>