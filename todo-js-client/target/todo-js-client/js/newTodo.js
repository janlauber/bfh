import service from "./service.js";

function getUserData() {
    return {
        name: $('#username').val(),
        password: $('#password').val()
    };
}

function getTodo() {
    return {
        title: $('#title').val(),
        category: $('#category').val(),
        dueDate: $('#dueDate').val(),
    };
}

$('#addTodo').click(function () {
    console.log(getTodo())
    service.postTodo(getUserData(), getTodo()).then(function (response) {
        console.log(response);
    });
});