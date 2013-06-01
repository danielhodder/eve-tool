$(function () {
	$('form').submit(function () { 
		$('.btn[data-loading-text]', this).button('loading'); 
	});
});