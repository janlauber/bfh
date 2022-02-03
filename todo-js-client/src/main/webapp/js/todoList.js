import service from "./service.js";

// get the list via the service
$('#getTodos').click(() => {
    service.getTodos(getUserData())
        .then(todos => {
            $('#todoList').empty();
            todos.forEach(todo => {
                $('#todoList').append(`<li>${todo.title}</li>`);
            });
        });
});

function getUserData() {
    return {
        name: $('#username').val(),
        password: $('#password').val()
    };
}