<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
	prefix="tilesx"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<div class="row-fluid">
	<div class="span12">
		<div class="hero-unit">
			<h1>
				<img src="<c:out value="${imageURILocator.getUriForTypeID(view.blueprint.producedTypeID, 64)}" />" />
				${view.blueprint.name}
				<small>
					<abbr title="Material Efficency">ME:</abbr> ${view.blueprint.materialEfficiency}
					<abbr title="Production Efficency">PE:</abbr> ${view.blueprint.productionEfficiency}
				</small>
				<a href="#update-blueprint-details" role="button"
					class="btn btn-primary pull-right" data-toggle="modal" data-keyboard="true">Update Sale Cost</a>
			</h1>
		
			<h2>Production Cost: ${currencyFormatter.format(view.blueprint.totalCost)}</h2>
			<h2>Sale Value: ${currencyFormatter.format(view.blueprint.saleValue)}</h2>
			<h2>Profit: ${currencyFormatter.format(view.blueprint.profit)}</h2>
			
			<h3>Number per production run: ${view.blueprint.numberPerRun}</h3>
			<h3>
				Takes: ${view.blueprint.hours} <abbr title="This is approximate">hours</abbr>
				(${durationFormatter.hoursToDays(view.blueprint.hours)} <abbr title="This is approximate">Days</abbr>)
			</h3>
		</div>
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
		
		<div id="update-blueprint-details" class="modal hide fade">
			<form class="form-horizontal" action="<s:url value="/blueprints/${view.blueprint.id}" />" method="post" autocomplete="off">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>Edit the sale price of the ${view.blueprint.name}</h3>
				</div>
				<div class="modal-body">
					<div class="control-group">
						<label class="control-label" for="saleValue">Sale Value:</label>
						<div class="controls">
							<div class="input-append">
								<input type="text" name="saleValue" value="${view.form.saleValue}" required="required" 
								pattern="[0-9]+\.[0-9]{2}" title="Please enter a currency including the cost to two decimal places" />
								<span class="add-on">ISK</span>
							</div>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="numberPerRun">Number per run:</label>
						
						<div class="controls">
							<input type="number" min="0" name="numberPerRun" value="${view.form.numberPerRun}" required="required" pattern="[0-9]+" />
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="materialEfficency">Material Efficiency Level:</label>
						
						<div class="controls">
							<input type="number" name="materialEfficency" value="${view.form.materialEfficency}" required="required" pattern="-?[0-9]+" />
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="productionEffiecincy">Production Efficiency Level:</label>
						
						<div class="controls">
							<input type="number" name="productionEffiecincy" value="${view.form.productionEffiecincy}" required="required" pattern="-?[0-9]+" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="reset" class="btn" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary" data-loading-text="Saving...">Save new price</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function () {
		$('#update-blueprint-details').on('hidden', function () {
			$('form', this).trigger('reset');
		});
		
		$('#update-blueprint-details').on('shown', function () {
			$('form input:first', this).focus();
		})
	});
</script>