package org.todo.model.user;

import org.todo.model.todo.TodoList;

public class User {

	private String name;
	private String password;
	private TodoList todoList;

	public User() {
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.todoList = new TodoList();
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public TodoList getTodoList() {
		return todoList;
	}
}
