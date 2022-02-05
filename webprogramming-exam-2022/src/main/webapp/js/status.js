let $footer = $('footer');

export default {
	clear: function() {
		$footer.empty();
	},
	info: function(message) {
		$footer.html($('<div>').addClass('info').text(message));
	},
	error: function(message) {
		$footer.html($('<div>').addClass('error').text(message));
	}
};
