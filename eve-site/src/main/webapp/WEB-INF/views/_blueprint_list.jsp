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
<!-- 		<tr class="success"> -->
<!-- 			<td>Sabre</td> -->
<!-- 			<td>1,000.00 ISK</td> -->
<!-- 			<td>10,000 ISK</td> -->
<!-- 			<td>9,000 ISK (90%)</td> -->
<!-- 		</tr> -->
		
<!-- 		<tr class="warning"> -->
<!-- 			<td>Sabre</td> -->
<!-- 			<td>9.500.00 ISK</td> -->
<!-- 			<td>10,000 ISK</td> -->
<!-- 			<td>500 ISK (5%)</td> -->
<!-- 		</tr> -->
		
<!-- 		<tr class="error"> -->
<!-- 			<td>EMP L</td> -->
<!-- 			<td>1,000.00 ISK</td> -->
<!-- 			<td>900 ISK</td> -->
<!-- 			<td>-100 ISK (-10%)</td> -->
<!-- 		</tr> -->
		
<!-- 		<tr class="info" data-toggle="tooltip" title="One of the components that makes up this item is missing some information"> -->
<!-- 			<td>Station Container <i class="icon-info-sign"></i></td> -->
<!-- 			<td>???</td> -->
<!-- 			<td>10,000 ISK</td> -->
<!-- 			<td>???</td> -->
<!-- 		</tr> -->
		
		<tiles:importAttribute name="blueprints" />
		<c:forEach var="blueprint" items="${blueprints}">
			<c:choose>
				<c:when test="${blueprint.profitPercentage > 15}">
					<tr class="success">
				</c:when>
				
				<c:when test="${blueprint.profitPercentage > 0 }">
					<tr class="warning">
				</c:when>
				
				<c:otherwise>
					<tr class="error">
				</c:otherwise>
			</c:choose>
			
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