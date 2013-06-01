$(function () {
	$('.modal[data-reset-on-close]').on('hidden', function () {
		$('form', this).trigger('reset');
	});
	
	$('.modal[data-focus-on-open]').on('shown', function () {
		$('form input:first', this).focus();
	});
});