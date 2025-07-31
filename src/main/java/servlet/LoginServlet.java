package servlet;

import entity.Profile;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.ProfileRepository;
import service.PasswordService;
import service.ProfileService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final ProfileService profileService = new ProfileService(new ProfileRepository(), new PasswordService());;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Profile profile = profileService.login(login, password);
            HttpSession session = req.getSession();
            session.setAttribute("userId", profile.getId());
            session.setAttribute("userName", login);
            resp.sendRedirect(req.getContextPath() + "/user/welcome");
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath() + "/user/welcome");
            return;
        }
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }
}
