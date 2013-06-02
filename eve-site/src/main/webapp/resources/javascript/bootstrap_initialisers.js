$(function () {
	$('form').submit(function () { 
		$('.btn[data-loading-text]', this).button('loading'); 
	});
	
	$('[data-toggle="tooltip"]').tooltip();
});