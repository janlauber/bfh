package stockquoter.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

// webfilter for DELETE method
@WebFilter(filterName = "authentication")
public class AuthenticationFilter extends HttpFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_PREFIX = "Basic";

    private static final String username = "admin";
    private static final String password = "admin";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // check if request method is DELETE
        if (request.getMethod().equals("DELETE")) {
            // get authorization header
            String authHeader = request.getHeader(AUTH_HEADER);
            // check if authorization header is null
            if (authHeader == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // check if authorization header starts with "Basic"
            if (!authHeader.startsWith(AUTH_PREFIX)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // get encoded username and password
            String encodedUsernamePassword = authHeader.substring(AUTH_PREFIX.length()).trim();
            // decode username and password
            String decodedUsernamePassword = new String(Base64.getDecoder().decode(encodedUsernamePassword));
            // split username and password
            String[] usernamePasswordArray = decodedUsernamePassword.split(":", 2);
            // check if username and password are correct
            if (!usernamePasswordArray[0].equals(username) || !usernamePasswordArray[1].equals(password)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (request.getMethod().equals("PUT")) {
            // get authorization header
            String authHeader = request.getHeader(AUTH_HEADER);
            // check if authorization header is null
            if (authHeader == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // check if authorization header starts with "Basic"
            if (!authHeader.startsWith(AUTH_PREFIX)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // get encoded username and password
            String encodedUsernamePassword = authHeader.substring(AUTH_PREFIX.length()).trim();
            // decode username and password
            String decodedUsernamePassword = new String(Base64.getDecoder().decode(encodedUsernamePassword));
            // split username and password
            String[] usernamePasswordArray = decodedUsernamePassword.split(":", 2);
            // check if username and password are the same
            if (!username.equals(password)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        // continue filter chain
        chain.doFilter(request, response);
    }
}
