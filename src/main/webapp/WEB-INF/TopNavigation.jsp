<!-- Fihier JSP Uniquement inclus dans une page HTML conforme -->

<!-- Si l'utilisateur est connecté -->
<c:if test="${!empty sessionScope.user_id }">
	<nav style ="display : flex;">
		<p>ENI-Enchères</p>
		<p style ="margin-left : auto;">
			<a href="VoirEncheres"> Enchères </a>
			<a href="AjouterUneEnchere"> Vendre </a>
			<a href="MonProfil"> Profil </a>
			<a href="Deconnexion"> Déconnexion </a>
		</p>
	</nav>
</c:if>

<!-- Si l'utilisateur n'est pas connecté -->
<c:if test="${empty sessionScope.user_id }">
	<nav style ="display : flex;">
		<p style ="width : 80%">ENI-Enchères</p>
		<p>
			<a href="Connexion"> Connexion - Inscription</a>
		</p>
	</nav>
</c:if>