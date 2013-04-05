<%@ page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th><tiles:getAsString name="typeName" /></th>
			<th>Last Cost</th>
			<th>Last Updated</th>
		</tr>
	</thead>

	<tbody>
		<tiles:importAttribute name="types" />
		<c:forEach var="type" items="${types}">
			<c:set var="class_name">
			<c:choose>
				<c:when test="${dashboardViewHelper.isTypeDataOld(type)}">
					<c:set var="display_class_name" value="warning" />
					<c:set var="data_toggle" value="tooltip" />
					<c:set var="title" value="This data is old and may not be reliable" />
				</c:when>
			</c:choose>
			</c:set>
			
			<tr 
					class="<c:out value="${display_class_name}" />" 
					data-toggle="<c:out value="${data_toggle}" />" 
					title="<c:out value="${title}" />"
			>
				<td><c:out value="${type.name}" /></td>
				<td><c:out value="${currencyFormatter.format(type.cost)}" /></td>
				<td><c:out value="${type.costLastUpdated}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>