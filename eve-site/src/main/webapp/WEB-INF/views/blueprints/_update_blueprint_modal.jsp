<%@ page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<tiles:importAttribute name="blueprint" />
<tiles:importAttribute name="form" />

<div id="update-blueprint-details" class="modal hide fade" data-reset-on-close="true" data-focus-on-open="true">
	<form class="form-horizontal" action="<s:url value="/blueprints/${blueprint.id}" />" method="post" autocomplete="off">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>Edit the sale price of the ${blueprint.name}</h3>
		</div>
		<div class="modal-body">
			<div class="control-group">
				<label class="control-label" for="saleValue">Sale Value:</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" name="saleValue" value="${form.saleValue}" required="required" 
						pattern="[0-9]+\.[0-9]{2}" title="Please enter a currency including the cost to two decimal places" />
						<span class="add-on">ISK</span>
					</div>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="numberPerRun">Number per run:</label>
				
				<div class="controls">
					<input type="number" min="0" name="numberPerRun" value="${form.numberPerRun}" required="required" pattern="[0-9]+" />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="materialEfficency">Material Efficiency Level:</label>
				
				<div class="controls">
					<input type="number" name="materialEfficency" value="${form.materialEfficency}" required="required" pattern="-?[0-9]+" />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="productionEffiecincy">Production Efficiency Level:</label>
				
				<div class="controls">
					<input type="number" name="productionEffiecincy" value="${form.productionEffiecincy}" required="required" pattern="-?[0-9]+" />
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="reset" class="btn" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary" data-loading-text="Saving...">Save</button>
		</div>
	</form>
</div>