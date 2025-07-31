package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.ProfileRepository;
import service.PasswordService;
import service.ProfileService;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final ProfileService profileService= new ProfileService(new ProfileRepository(), new PasswordService());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/registration.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            profileService.register(req.getParameter("login"), req.getParameter("password"));
            req.getSession().setAttribute("registrationSuccess",true);
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
            }
        catch(Exception e){
            req.setAttribute("message", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/registration.jsp");
            dispatcher.forward(req, resp);
        }

    }
}
