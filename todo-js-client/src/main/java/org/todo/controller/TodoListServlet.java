package org.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.todo.model.todo.Todo;
import org.todo.model.todo.TodoNotFoundException;
import org.todo.model.user.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/todos/*")
public class TodoListServlet extends HttpServlet {

	private static final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get todos
		String category = request.getParameter("category");
		User user = (User) request.getAttribute("user");
		List<Todo> todos = category != null ? user.getTodoList().getTodos(category) : user.getTodoList().getTodos();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		objectMapper.writeValue(response.getOutputStream(), todos);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// parse and validate todo
			Todo todo = objectMapper.readValue(request.getInputStream(), Todo.class);
			if (todo.getId() != null || todo.getTitle() == null || todo.getTitle().isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			// add todo
			User user = (User) request.getAttribute("user");
			user.getTodoList().addTodo(todo);
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.setContentType("application/json");
			objectMapper.writeValue(response.getOutputStream(), todo);
		} catch (JsonProcessingException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// get id from request path
			String pathInfo = request.getPathInfo();
			if (pathInfo == null || pathInfo.equals("/")) {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return;
			}
			int id = Integer.parseInt(pathInfo.substring(1));

			// parse and validate todo
			Todo todo = objectMapper.readValue(request.getInputStream(), Todo.class);
			if (todo.getId() != id || todo.getTitle() == null || todo.getTitle().isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			// update todo
			User user = (User) request.getAttribute("user");
			user.getTodoList().updateTodo(todo);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");
			objectMapper.writeValue(response.getOutputStream(), todo);
		} catch (JsonProcessingException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (NumberFormatException | TodoNotFoundException ex) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		try {
			// get id from request path
			String pathInfo = request.getPathInfo();
			if (pathInfo == null || pathInfo.equals("/")) {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return;
			}
			int id = Integer.parseInt(pathInfo.substring(1));

			// remove todo
			User user = (User) request.getAttribute("user");
			user.getTodoList().removeTodo(id);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (NumberFormatException | TodoNotFoundException ex) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
