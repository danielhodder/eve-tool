<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<table class="table table-striped table-hover">
	<thead>
		<tr>
			<th><tiles:getAsString name="typeName" /></th>
			<th>Last Cost</th>
			<th>Last Updated</th>
		</tr>
	</thead>

	<tbody>
		<tr>
			<td>Tritanium</td>
			<td>6.12 ISK</td>
			<td>31/03/2013 20:15</td>
		</tr>

		<tr class="warning" data-toggle="tooltip"
			title="This data is old and may not be reliable">
			<td>Morphite <i class="icon-info-sign"></i></td>
			<td>1.00 ISK</td>
			<td>01/01/1970 00:00</td>
		</tr>

		<tr class="error" data-toggle="tooltip"
			title="No price information is avaliable for this item">
			<td>Noxium <i class="icon-exclamation-sign"></i></td>
			<td>???</td>
			<td>Never</td>
		</tr>
	</tbody>
</table>