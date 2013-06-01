<%@ page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<tiles:importAttribute name="blueprint" />

<table class="table table-striped table-hover types image-table">
	<thead>
		<tr>
			<th style="width: 325px;"><tiles:getAsString name="typeName" /></th>
			<th class="text-right">Quantity</th>
			<th class="text-right">Last Cost</th>
			<th class="text-right">Total Cost</th>
			<th class="text-right"><abbr title="Percentage of build cost">Percentage</abbr></th>
			<th class="text-right">Last Updated</th>
		</tr>
	</thead>

	<tbody>
		<tiles:importAttribute name="types" />
		<c:forEach var="requiredComponent"  items="${types}">
			<c:set var="class_name">
				<c:choose>
					<c:when test="${requiredComponent.key.cost == null}">
						error missing-data
					</c:when>
					
					<c:when test="${dashboardViewHelper.isTypeDataOld(requiredComponent.key)}">
						warning old-data
					</c:when>
				</c:choose>
			</c:set>
			
			<tr class="<c:out value="${class_name}" />" >
				<td>
					<a>
						<img src="<c:out value="${imageURILocator.getUriForType(requiredComponent.key, 32)}" />" />
						<c:out value="${requiredComponent.key.name}" />
					</a>
				</td>
				<td class="text-right"><c:out value="${requiredComponent.value}" /></td>
				<td class="text-right"><c:out value="${currencyFormatter.format(requiredComponent.key.cost)}" /></td>
				<td class="text-right">
					<strong>
						<c:out value="${currencyFormatter.format(requiredComponent.key.cost * requiredComponent.value)}" />
					</strong>
				</td>
				<td class="text-right">
					<em>
						<c:out value="${percentageFormatter.format(((requiredComponent.key.cost * requiredComponent.value) / blueprint.totalCost) * 100)}" />%
					</em>
				</td>
				<td class="text-right">
					<c:out value="${dateFormatter.format(requiredComponent.key.costLastUpdated)}" />
					<i class="icon-info-sign" title="<c:out value="${requiredComponent.key.costLastUpdated}" />"></i>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	$(function () {
		var tooltips = {
				'.old-data' : 'This data is old and may not be reliable',
				'.missing-data' : 'This blueprint is missing some pricing information'
		}
		
		$.each(tooltips, function(identifier, tooltip) {
			$(identifier, $('.types')).tooltip({
				'title': tooltip
			});
		});
	});
</script>