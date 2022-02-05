const BASE_URI = '/api';

export default {
	getQuotes: function() {
		let settings = {
			url: BASE_URI + '/quotes',
			type: 'GET',
			dataType: 'json'
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	},
	getCompany: function(companyId) {
		let settings = {
			url: BASE_URI + '/companies/' + companyId,
			type: 'GET',
			dataType: 'json'
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	},
};
