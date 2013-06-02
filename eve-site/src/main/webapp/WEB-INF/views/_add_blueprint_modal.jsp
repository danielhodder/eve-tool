<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div id="new-blueprint" class="modal hide fade" data-reset-on-close="true" data-focus-on-open="true">
	<form class="form-horizontal" action="<s:url value="/blueprints/new" />?return=${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}" method="post" autocomplete="off">
		<header class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h3>Add a new blueprint</h3>
		</header>
	
		<div class="modal-body">		
			<fieldset class="control-group">
				<label class="control-label" id="new-blueprint-image-container" style="margin-top: 0;">
				</label>
				
				<div class="controls">
					<input type="text" name="blueprint-name" class="input-xlarge search-query" placeholder="Blueprint Name" data-provide="typeahead" id="blueprint-name" required="required" />
					<input type="hidden" name="blueprint-id" id="blueprint-id" required="required" />
				</div>
			</fieldset>
			
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
					<input type="number" min="1" name="numberPerRun" value="${form.numberPerRun}" required="required" pattern="[1-9][0-9]*" />
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
		
		<footer class="modal-footer">
			<button type="reset" class="btn" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary" data-loading-text="Saving...">Save</button>
		</footer>
	</form>
</div>

<script type="text/javascript">
	$(function () {
		var names = [];
		var map = {};
		
		$('#blueprint-name').typeahead({
			source: function (search, processCallback) {				
				$.get('<s:url value="/blueprints/search" />', 
						{'blueprint-name': search},
						function (data) {
							names = [];
							map = {};
							
							$.each(data, function (i, result) {
								map[result.name] = result;
								names.push(result.name);
							});
							
							processCallback(names);
						});
			}, updater: function (selectedName) {
				$('#blueprint-id').val(map[selectedName].id);
				$('#new-blueprint-image-container').empty();
				$('#new-blueprint-image-container').append('<img src="'+map[selectedName].imageURI+'" />');
				
				return selectedName;
			}, minLength: 3
		});
		
		$('#new-blueprint form').submit(function () {
			if ($('input[name="blueprint-id"]').val() == "") {
				$('button[data-loading-text]').button('reset');
				
				return false;
			} else
				return true;
		});
	});
</script>