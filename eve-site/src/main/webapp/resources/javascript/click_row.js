$(function () {
	$('table.click-row tbody tr').click(function () {
		window.location = $('a', this).first().attr('href');
	});
});