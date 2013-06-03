/**
 * This component adds a hidden field that captures the value of a set of bootstrap radio buttons
 */
$(function () {
	$('form .btn-group[data-toggle="buttons-radio"][data-name]').each(function () {
		console.log($('input[name="'+$(this).attr('data-name')+'"]', $(this).parent()));
		if ($('input[name="'+$(this).attr('data-name')+'"]', $(this).parent()).length == 0) {
			$(this).parent().append('<input type="hidden" name="'+$(this).attr('data-name')+'" />');
		}
		
		var hiddenField = $('input', $(this).parent());
		
		$('button', this).click(function () {
			hiddenField.val($(this).val());
		});
	});
});