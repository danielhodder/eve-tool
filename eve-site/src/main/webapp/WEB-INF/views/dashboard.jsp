<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<h1>Overview</h1>

<div class="row">
	<div class="span12">
		<tiles:insertTemplate template="_blueprint_list.jsp" />
	</div>
</div>

<div class="row">
	<div class="span6">
		<h2>Minerals</h2>
		
		<tiles:insertTemplate template="_type_list.jsp">
			<tiles:putAttribute name="typeName" value="Minerals" />
		</tiles:insertTemplate>
	</div>
	
	<div class="span6">
		<h2>Components</h2>
		
		<tiles:insertTemplate template="_type_list.jsp">
			<tiles:putAttribute name="typeName" value="Components" />
		</tiles:insertTemplate>
	</div>
</div>

<script type="text/javascript">
	$(function () {
		$('[data-toggle="tooltip"]').tooltip();
	});
</script>