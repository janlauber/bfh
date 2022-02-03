const BASE_URI = '/api';

export default {
	getMessages: async function(subject) {
		let settings = {
			url: BASE_URI + '/messages' + (subject ? '?subject=' + subject : ''),
			type: 'GET',
			dataType: 'json'
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	},
	getSubjects: async function() {
		let settings = {
			url: BASE_URI + '/subjects',
			type: 'GET',
			dataType: 'json'
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	},
	postMessage: async function(message) {
		let settings = {
			url: BASE_URI + '/messages',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(message)
		};
		console.log('Sending ' + settings.type + ' request to ' + settings.url);
		return $.ajax(settings);
	},
};
