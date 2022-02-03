package org.todo.model.todo;

import java.time.LocalDate;

public class Todo {

	private Integer id;
	private String title;
	private String category;
	private LocalDate dueDate;
	private boolean completed;

	public Todo() {
	}

	public Todo(String title) {
		this.title = title;
	}

	public Todo(String title, String category, LocalDate dueDate) {
		this.title = title;
		this.category = category;
		this.dueDate = dueDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
