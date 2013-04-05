<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th>Blueprint Name</th>
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
				<td><c:out value="${blueprint.name}" /></td>
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