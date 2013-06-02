<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div class="row-fluid">
	<div class="span12">
		<tiles:insertTemplate template="_blueprint_information.jsp">
			<tiles:putAttribute name="blueprint" value="${view.blueprint}" />
		</tiles:insertTemplate>
	</div>
</div>

<div class="row-fluid">
	<div class="span12">
		<h2>Required Components and Minerals</h2>
		<tiles:insertTemplate template="_type_list.jsp">
			<tiles:putAttribute name="typeName">Name</tiles:putAttribute>
			<tiles:putAttribute name="types"
				value="${view.requiredTypes.allRequiredTypes}" />
			<tiles:putAttribute name="blueprint" value="${view.blueprint}" />
		</tiles:insertTemplate>
	</div>
</div>