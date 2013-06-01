<%@ page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<table class="table table-striped table-hover types">
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
					<c:when test="${type.cost == null}">
						error missing-data
					</c:when>
					
					<c:when test="${dashboardViewHelper.isTypeDataOld(type)}">
						warning old-data
					</c:when>
				</c:choose>
			</c:set>
			
			<tr class="<c:out value="${class_name}" />" >
				<td><a><c:out value="${type.name}" /></a></td>
				<td><c:out value="${currencyFormatter.format(type.cost)}" /></td>
				<td><c:out value="${type.costLastUpdated}" /></td>
				<td class="hidden">
					<div class="popover-html">
						<img src="<c:out value="${imageURILocator.getUriForType(type, 64)}" />" />
					</div>
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
		
		$('.types tr').each(function () {
			$(this).popover({
				'html' : 'true',
				'content' : $('.popover-html', this).html(),
				'placement' : 'left',
				'trigger' : 'hover'
			});
		});
	});
</script>