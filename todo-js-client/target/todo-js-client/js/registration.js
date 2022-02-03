import service from './service.js';

// on click of the submit button
$('#register').click(function () {

    // call the service
    service.postUser(getUserData())
        .then(function (response) {
            // if the response is successful
            if (response.status === 200) {
                // redirect to the login page
                console.log('user registered');
            } else {
                // display the error message
                $('#error').text(response.message);
            }
        }).catch(function (error) {
            // display the error message
            $('#error').text(error.message);
        });
});

function getUserData() {
    return {
        name: $('#username').val(),
        password: $('#password').val()
    };
}