package stockquoter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import stockquoter.model.StockQuote;
import stockquoter.model.StockQuoterService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/api/quotes/*")
public class StockQuoterServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(StockQuoterServlet.class.getName());
	private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && !pathInfo.equals("/")) {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}
		logger.info("Getting all quotes");
		List<StockQuote> quotes = StockQuoterService.getQuotes();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		objectMapper.writeValue(response.getOutputStream(), quotes);
	}
}
