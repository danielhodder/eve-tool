<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
			</a> 
			<a class="brand" href="<s:url value="/" />">EVE Blueprint Tool</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active"><a href='<s:url value="/" />'>Home</a></li>
					<li><a href="#new-blueprint" data-toggle="modal">Add a blueprint</a></li>
				</ul>
				
				<!-- Object Search -->
<!-- 				<form class="navbar-search pull-right"> -->
<!-- 					<input type="text" class="search-query" autocomplete="off" data-provide="typeahead" placeholder="Object Name" /> -->
<!-- 				</form> -->
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>

<tiles:insertTemplate template="../views/_add_blueprint_modal.jsp" />