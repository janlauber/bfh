package org.todo.model.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class TodoList {

	private final List<Todo> todos = new ArrayList<>();
	private int lastId = 0;

	public List<Todo> getTodos() {
		return todos;
	}

	public List<Todo> getTodos(String category) {
		return todos.stream().filter(todo -> Objects.equals(todo.getCategory(), category)).collect(toList());
	}

	public Todo findTodo(int id) throws TodoNotFoundException {
		return todos.stream().filter(todo -> Objects.equals(todo.getId(), id)).findFirst()
				.orElseThrow(TodoNotFoundException::new);
	}

	public void addTodo(Todo todo) {
		todo.setId(++lastId);
		todos.add(todo);
	}

	public void updateTodo(Todo todo) throws TodoNotFoundException {
		Todo existing = findTodo(todo.getId());
		existing.setTitle(todo.getTitle());
		existing.setCategory(todo.getCategory());
		existing.setDueDate(todo.getDueDate());
		existing.setCompleted(todo.isCompleted());
	}

	public void removeTodo(int id) throws TodoNotFoundException {
		Todo todo = findTodo(id);
		todos.remove(todo);
	}
}
