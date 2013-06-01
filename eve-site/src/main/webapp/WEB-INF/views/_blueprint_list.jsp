<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<table class="table table-striped table-hover blueprints image-table">
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
			<c:set var="class_name">
				<c:choose>
					<c:when test="${blueprint.totalCost == null}">
						info missing-data
					</c:when>
					
					<c:when test="${blueprint.profitPercentage > 15}">
						success
					</c:when>
					
					<c:when test="${blueprint.profitPercentage > 0 }">
						warning
					</c:when>
					
					<c:otherwise>
						error
					</c:otherwise>
				</c:choose>
			</c:set>
			
			<tr class="<c:out value="${class_name}" />">
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

<script type="text/javascript">
	$(function () {
		var tooltips = {
				'.missing-data' : 'This blueprint is missing some pricing information'
		}
		
		$.each(tooltips, function(identifier, tooltip) {
			$(identifier, $('.blueprints')).tooltip({
				'title': tooltip
			});
		});
	});
</script>