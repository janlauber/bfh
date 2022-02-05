import service from '../service.js';
import router from "../router.js";

let Timer = null;

const template = `
	<div>
		<h1>Quotes</h1>
		<table>
			<thead>
				<tr>
					<th>Symbol</th>
					<th>Valor</th>
					<th>Last Price</th>
					<th>Current Price</th>
					<th>Change</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
`;

export default {
	title: 'Quotes',
	render: function() {
		let $view = $(template);
		fetchQuotes($view);
		// refresh messages every 10 seconds by timer
		Timer = setInterval(() => fetchQuotes($view), 10000);
		// on click on a quote row redirect to the quote detail page td symbol
		$view.on('click', 'tbody tr', function() {
			// get data-companyId from first td
			let companyId = $(this).find('td:first').data('companyid');
			clearInterval(Timer);
			router.navigate('/company/' + companyId);
		});
		return $view;
	}
};

function fetchQuotes($view) {
	service.getQuotes()
		.then(quotes => renderQuotes(quotes, $view))
		.catch(xhr => status.error('Unexpected error (' + xhr.status + ')'));
}

function renderQuotes(quotes, $view) {
	let $tbody = $('tbody', $view).empty();
	for (let quote of quotes) {
		$tbody.append(renderQuote(quote));
	}
}

function renderQuote(quote) {
	let changeColor = quote.change >= 0 ? 'green' : 'red';

	return $('<tr>').html(`
		<td data-companyid="${quote.companyId}">${quote.symbol}</td>
		<td>${quote.valor}</td>
		<td>${quote.lastPrice}</td>
		<td>${quote.currentPrice}</td>
		<td style="color: ${changeColor}">${quote.change}</td>
	`);
}
