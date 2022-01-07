<!-- Fihier JSP Uniquement inclus dans une page HTML conforme -->

<!-- Si l'utilisateur est connecté -->
<c:if test="${!empty sessionScope.user_id }">
	<nav class="d-flex flex-row col-lg-12 " style="padding-top: 20px;">
		<h4 class="col-lg-3">ENI-Enchères</h4>
		<p class="col-lg-6 col-lg-offset-2 nav" >
			<a class="btn-sm btn-primary " href="Les_Enchères" style="border: 1px solid white;"> Enchères </a>
			<a class="btn-sm btn-primary " href="AjouterUneEnchere" style="border: 1px solid white;"> Vendre </a>
			<a class="btn-sm btn-primary " href="MonProfil"style="border: 1px solid white;"> Profil </a>
			<a class="btn-sm btn-primary" href="Deconnexion"style="border: 1px solid white;"> Déconnexion </a>
		</p>
	</nav>
</c:if>

<!-- Si l'utilisateur n'est pas connecté -->
<c:if test="${empty sessionScope.user_id }">
	<nav class="d-flex flex-row col-lg-12 ">
		<p class="col-lg-2 col-lg-offset-1">ENI-Enchères</p>
		<p class="col-lg-5 col-lg-offset-2">
			<a class="btn-sm btn-primary" href="Connexion"> Connexion - Inscription</a>
		</p>
	</nav>
</c:if>