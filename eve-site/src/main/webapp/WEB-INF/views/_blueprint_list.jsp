<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<table class="table table-striped table-hover blueprints image-table click-row">
	<thead>
		<tr>
			<th colspan="2">Blueprint Name</th>
			<th>Cost</th>
			<th>Sale Value</th>
			<th>Profit</th>
		</tr>
	</thead>
	
	<tbody>
		<tiles:importAttribute name="blueprints" />
		<c:forEach var="blueprint" items="${blueprints}">
			<c:choose>
				<c:when test="${blueprint.totalCost == null}">
					<c:set var="class_name">info</c:set>
					<c:set var="tooltip">This blueprint is missing some pricing information</c:set>
				</c:when>
				
				<c:when test="${blueprint.profitPercentage > 15}">
					<c:set var="class_name">success</c:set>
					<c:set var="tooltip" />
				</c:when>
				
				<c:when test="${blueprint.profitPercentage > 0 }">
					<c:set var="class_name">warning</c:set>
					<c:set var="tooltip" />
				</c:when>
				
				<c:otherwise>
					<c:set var="class_name">error</c:set>
					<c:set var="tooltip" />
				</c:otherwise>
			</c:choose>
			
			<tr class="${class_name}" title="${tooltip}" data-toggle="tooltip" data-container="body">
				<td>
					<img src="<c:out value="${imageURILocator.getUriForTypeID(blueprint.producedTypeID, 32)}" />" />
				</td>
				<td><a href="<c:url value="/blueprints/${blueprint.id}" />"><c:out value="${blueprint.name}" /></a></td>
				<td><c:out value="${currencyFormatter.format(blueprint.totalCost)}" /></td>
				<td><c:out value="${currencyFormatter.format(blueprint.saleValue)}" /></td>
				<td>
					<span class="absolute"><c:out value="${currencyFormatter.format(blueprint.profit)}" /></span> 
					<span class="percent">(<c:out value="${percentageFormatter.format(blueprint.profitPercentage)}" /> %)</span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>