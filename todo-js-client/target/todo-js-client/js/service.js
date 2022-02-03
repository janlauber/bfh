const BASE_URI = '/api';

export default {
    postUser: async function(user) {
        return fetch(`${BASE_URI}/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        }).then(
            (response) => {
                if (response.status === 201) {
                    console.log('User created');
                    return response.json();
                } else if (response.status === 409) {
                    console.log('User already exists');
                    throw new Error('User already exists');
                } else {
                    console.log('Error creating user');
                    throw new Error('Error creating user');
                }
            }
        );
    },
    getTodos: function(user) {
        // Authorization: Basic user.name user.password
        return fetch(`${BASE_URI}/todos`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(user.name + ':' + user.password)
            }
        }).then(
            (response) => {
                if (response.status === 200) {
                    console.log('Todos retrieved');
                    return response.json();
                } else if (response.status === 401) {
                    console.log('Unauthorized');
                    throw new Error('Unauthorized');
                } else {
                    console.log('Error getting todos');
                    throw new Error('Error getting todos');
                }
            }
        );
    },
    postTodo: async function(user, todo) {
        return fetch(`${BASE_URI}/todos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(user.name + ':' + user.password)
            },
            body: JSON.stringify(todo)
        }).then(
            (response) => {
                if (response.status === 201) {
                    console.log('Todo created');
                    return response.json();
                } else if (response.status === 409) {
                    console.log('Todo already exists');
                    throw new Error('Todo already exists');
                } else {
                    console.log('Error creating todo');
                    throw new Error('Error creating todo');
                }
            }
        );
    },
};