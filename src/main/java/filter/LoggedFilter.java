package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/user/*")
public class LoggedFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null && session.getId() != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
        }
    }
}

