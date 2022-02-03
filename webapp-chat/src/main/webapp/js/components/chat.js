import service from '../service.js';
import status from '../status.js';
import store from "../store.js";
import router from "../router.js";

let Timer = null;

const template = `
	<div>
		<h1 id="chatname"></h1>
		<div>
			<input id="message" placeholder="Enter new message" class="large" required>
			<button id="postMessage">Post</button>
		</div>
		<ul id="messages"></ul>
		<div id="home">
			<a id="homeButton" style="cursor: pointer">Home</a>
		</div>
	</div>
`;

export default {
	title: 'Chat',
	render: function() {
		let $view = $(template);
		fetchMessages($view);
		// change the chat name
		$view.find('#chatname').text("Chat " + store.getSubject());

		// post a message
		$view.find('#postMessage').click(function () {
			let message = $view.find('#message').val();
			if (message.length > 0) {
				// parse message with subject to JSON
				let json = {
					"subject": store.getSubject(),
					"text": message
				};
				service.postMessage(json).then(r => {
					// clear the input field
					$view.find('#message').val("");
					// fetch the messages again
					fetchMessages($view);
				});
			}


		});

		// refresh messages every 10 seconds by timer
		Timer = setInterval(() => fetchMessages($view), 10000);
		// go to home and destroy the timer
		$view.find('#homeButton').click(() => {
			clearInterval(Timer);
			router.navigate('/');
		});
		return $view;
	}
};

function fetchMessages($view) {
	service.getMessages(store.getSubject())
		.then(messages => renderMessages(messages, $view))
		.catch(xhr => status.error('Unexpected error (' + xhr.status + ')'));
}

function renderMessages(messages, $view) {
	let $messages = $('#messages', $view);
	$messages.empty();
	for (let message of messages) {
		let $item = $('<li>').text(message.text);
		$messages.append($item);
	}
}

function postMessage(event, $view) {
	event.preventDefault();
	if (!$('form', $view)[0].reportValidity()) return;
	let message = {
		text: $('#message', $view).val()
	};
	service.postMessage(message)
		.then(message => {
			fetchMessages($view);
			$('#message', $view).val('');
		})
		.catch(xhr => status.error('Unexpected error (' + xhr.status + ')'));
}
