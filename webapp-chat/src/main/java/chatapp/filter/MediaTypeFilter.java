package chatapp.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "mediatype")
public class MediaTypeFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String acceptHeader = request.getHeader("Accept");
		if (acceptHeader != null && !acceptHeader.contains("*/*") && !acceptHeader.contains("application/json")) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
			if (request.getContentType() == null || !request.getContentType().equals("application/json")) {
				response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
				return;
			}
		}
		chain.doFilter(request, response);
	}
}
