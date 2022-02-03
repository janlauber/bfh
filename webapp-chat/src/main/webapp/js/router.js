import status from './status.js';

const routes = {};

export default {
	register: function(path, component) {
		console.log(`Register path ${path}`);
		routes[path] = component;
	},
	navigate: function(uri) {
		if (location.hash === '#' + uri)
			navigateTo(uri);
		else location.hash = uri;
	}
}

$(window).on('hashchange', event => {
	let uri = location.hash.replace('#', '');
	navigateTo(uri);
});

function navigateTo(uri) {
	console.log(`Navigate to ${uri}`);
	let [path, params] = parse(uri);
	let component = routes[path];
	if (component) {
		show(component, params);
	} else {
		$('main').empty().html('<h1>Error</h1>');
		status.info(`Path ${path} not found`);
	}
}

function parse(uri) {
	let [path, query] = uri.split('?');
	let parts = path.split('/');
	path = '/' + parts[1];
	let params = parts.splice(2);
	if (query) {
		for (let param of query.split('&')) {
			let [key, value] = param.split('=');
			params[key] = value;
		}
	}
	return [path, params];
}

function show(component, param) {
	status.clear();
	let $view = component.render(param);
	$('main').empty().append($view);
	document.title = component.title;
}
