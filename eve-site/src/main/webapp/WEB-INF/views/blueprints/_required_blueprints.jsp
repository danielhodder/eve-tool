<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:importAttribute name="blueprints" />

<table class="table table-striped table-hover types image-table click-row">
	<thead>
		<tr>
			<th style="width: 323px;">Name</th>
			<th class="text-right">Number of runs</th>
			<th class="text-right">Cost to run</th>
			<th class="text-right">Number of items Produced</th>
			<th class="text-right">Number of items left after production of	${view.blueprint.name}</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach items="${blueprints}" var="blueprint">
			<tr>
				<td>
					<a href="<c:url value="/blueprints/${blueprint.typeBlueprint.id}" />">
						<img src="<c:out value="${imageURILocator.getUriForTypeID(blueprint.typeBlueprint.id, 32)}" />" />
						${blueprint.typeBlueprint.name}
					</a>
				</td>
				<td class="text-right">${blueprint.runs}</td>
				<td class="text-right">${currencyFormatter.format(blueprint.productionCost)}</td>
				<td class="text-right">${blueprint.producedUnits}</td>
				<td class="text-right">${blueprint.producedUnits - blueprint.requiredUnits}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>