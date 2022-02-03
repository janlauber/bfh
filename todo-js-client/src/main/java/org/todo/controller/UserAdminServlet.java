package org.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.todo.model.user.User;
import org.todo.model.user.UserAdmin;
import org.todo.model.user.UserAlreadyExistsException;

import java.io.IOException;

@WebServlet("/api/users/*")
public class UserAdminServlet extends HttpServlet {

	private final static UserAdmin userAdmin = UserAdmin.getInstance();
	private final static ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			User user = objectMapper.readValue(request.getInputStream(), User.class);
			if (user.getName() == null || user.getName().isEmpty() ||
					user.getPassword() == null || user.getPassword().isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			userAdmin.registerUser(user.getName(), user.getPassword());
			response.setStatus(HttpServletResponse.SC_CREATED);
		} catch (JsonProcessingException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (UserAlreadyExistsException ex) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
		}
	}
}
