<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
			</a> 
			<a class="brand" href="#">EVE Blueprint Tool</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href='<s:url value="/" />'>Home</a></li>					
					
					<!-- Blueprint menu items -->
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href='<s:url value="/blueprints" />'>Blueprints <b class="caret"></b></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href='<s:url value="/blueprints" />'>List Blueprints</a></li>
							<li><a href='<s:url value="/blueprints/new" />'>Add a blueprint</a></li>
						</ul>
					</li>
					
					<!-- Minerals and components -->
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href='<s:url value="/parts" />'>Parts <b class="caret"></b></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href='<s:url value="/parts" />'>List Parts</a></li>
							<li><a href='<s:url value="/parts/new" />'>Add a part</a></li>
						</ul>
					</li>
				</ul>
				
				<!-- Object Search -->
				<form class="navbar-search pull-right">
					<input type="text" class="search-query" autocomplete="off" data-provide="typeahead" placeholder="Object Name" />
				</form>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function () {
		$('.navbar .search-query').typeahead({
			source: ['Sabre Blueprint',
			         'EMP L',
			         'Station Container',
			         'Tritatium',
			         'Morphoite',
			         'Noxium',
			         'Construction Blocks',
			         'Deflection Shield Emmitter',
			         'Plasma Thruster'],
			         
			updater: function (item) {
				$('.navbar .search-query').parents('form').submit();
				
				return item;
			}
		});
	});
</script>