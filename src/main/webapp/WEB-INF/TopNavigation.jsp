<!-- Fihier JSP Uniquement inclus dans une page HTML conforme -->

<!-- Si l'utilisateur est connect� -->
<c:if test="${!empty sessionScope.user_id }">
	<nav style ="display : flex;">
		<p>ENI-Ench�res</p>
		<p style ="margin-left : auto;">
			<a href="VoirEncheres"> Ench�res </a>
			<a href="AjouterUneEnchere"> Vendre </a>
			<a href="MonProfil"> Profil </a>
			<a href="Deconnexion"> D�connexion </a>
		</p>
	</nav>
</c:if>

<!-- Si l'utilisateur n'est pas connect� -->
<c:if test="${empty sessionScope.user_id }">
	<nav style ="display : flex;">
		<p style ="width : 80%">ENI-Ench�res</p>
		<p>
			<a href="Connexion"> Connexion - Inscription</a>
		</p>
	</nav>
</c:if>