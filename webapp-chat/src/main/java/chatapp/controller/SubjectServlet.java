package chatapp.controller;

import chatapp.model.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/subjects")
public class SubjectServlet extends HttpServlet {

    private final ChatService chatService = ChatService.getInstance();
    private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // set response content type
        resp.setContentType("application/json");

        // response status code
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.getWriter().println(objectMapper.writeValueAsString(chatService.getSubjects()));
    }
}
