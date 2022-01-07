<!-- Fihier JSP Uniquement inclus dans une page HTML conforme -->

<!-- Si l'utilisateur est connect� -->
<c:if test="${!empty sessionScope.user_id }">
	<nav class="d-flex flex-row col-lg-12 " style="padding-top: 20px;">
		<h4 class="col-lg-3">ENI-Ench�res</h4>
		<p class="col-lg-6 col-lg-offset-2 nav" >
			<a class="btn-sm btn-primary " href="Les_Ench�res" style="border: 1px solid white;"> Ench�res </a>
			<a class="btn-sm btn-primary " href="AjouterUneEnchere" style="border: 1px solid white;"> Vendre </a>
			<a class="btn-sm btn-primary " href="MonProfil"style="border: 1px solid white;"> Profil </a>
			<a class="btn-sm btn-primary" href="Deconnexion"style="border: 1px solid white;"> D�connexion </a>
		</p>
	</nav>
</c:if>

<!-- Si l'utilisateur n'est pas connect� -->
<c:if test="${empty sessionScope.user_id }">
	<nav class="d-flex flex-row col-lg-12 ">
		<p class="col-lg-2 col-lg-offset-1">ENI-Ench�res</p>
		<p class="col-lg-5 col-lg-offset-2">
			<a class="btn-sm btn-primary" href="Connexion"> Connexion - Inscription</a>
		</p>
	</nav>
</c:if>