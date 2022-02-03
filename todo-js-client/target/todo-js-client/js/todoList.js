import service from "./service.js";

// get the list via the service
$('#getTodos').click(() => {
    service.getTodos(getUserData())
        .then(todos => {
            $('#todoList').empty();
            todos.forEach(todo => {
                $('#todoList').append(`<hr>`);
                $('#todoList').append(`<li>Title: ${todo.title}</li>`);
                $('#todoList').append(`<li>Category: ${todo.category}</li>`);
                $('#todoList').append(`<li>Due Date: ${todo.dueDate}</li>`);
            });
        });
});

function getUserData() {
    return {
        name: $('#username').val(),
        password: $('#password').val()
    };
}