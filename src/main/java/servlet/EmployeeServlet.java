package servlet;

import entity.Employee;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.EmployeeRepository;
import repository.ProfileRepository;
import service.EmployeeService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/user/employee")
public class EmployeeServlet extends HttpServlet {
    private final ProfileRepository profileRepository = new ProfileRepository();
    ;
    private final EmployeeService employeeService = new EmployeeService(new EmployeeRepository(), profileRepository);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Long userId = (Long) session.getAttribute("userId");

        // Обработка flash-сообщений
        req.setAttribute("error", session.getAttribute("error"));
        req.setAttribute("success", session.getAttribute("success"));
        session.removeAttribute("error");
        session.removeAttribute("success");

        // Обработка поиска
        String searchName = req.getParameter("searchName");
        List<Employee> employees;

        if (searchName != null && !searchName.trim().isEmpty()) {
            employees = employeeService.searchEmployee(searchName.trim(), userId);
        } else {
            employees = employeeService.getAllEmployees(userId);
        }

        req.setAttribute("searchName", searchName);
        req.setAttribute("employees", employees);

        // Обработка редактирования
        Employee editEmployee = (Employee) session.getAttribute("editEmployee");
        if (editEmployee != null) {
            req.setAttribute("editEmployee", editEmployee);
        }

        req.getRequestDispatcher("/WEB-INF/user/employee.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        String action = req.getParameter("action");

        try {
            switch (action) {
                case "insert":
                    Employee newEmployee = new Employee();
                    newEmployee.setName(req.getParameter("name"));
                    newEmployee.setAge(Integer.parseInt(req.getParameter("age")));
                    newEmployee.setId_profile(userId);
                    employeeService.addEmployee(newEmployee);
                    session.setAttribute("success", "Employee added successfully");
                    break;

                case "edit":
                    Long editId = Long.parseLong(req.getParameter("id"));
                    Employee editEmployee = employeeService.getEmployeeByIdAndProfileId(editId, userId);
                    session.setAttribute("editEmployee", editEmployee);
                    break;

                case "update":
                    Employee updateEmployee = new Employee();
                    updateEmployee.setId(Long.parseLong(req.getParameter("id")));
                    updateEmployee.setName(req.getParameter("name"));
                    updateEmployee.setAge(Integer.parseInt(req.getParameter("age")));
                    updateEmployee.setId_profile(userId);
                    employeeService.updateEmployee(updateEmployee, userId);
                    session.setAttribute("success", "Employee updated successfully");
                    session.removeAttribute("editEmployee");
                    break;

                case "delete":
                    Long deleteId = Long.parseLong(req.getParameter("id"));
                    employeeService.deleteEmployee(deleteId, userId);
                    session.setAttribute("success", "Employee deleted successfully");
                    break;

                case "search":
                    String searchName = req.getParameter("searchName");
                    String redirectUrl = req.getContextPath() + "/user/employee";

                    if (searchName != null && !searchName.trim().isEmpty()) {
                        redirectUrl += "?searchName=" + URLEncoder.encode(searchName.trim(), StandardCharsets.UTF_8);
                    }

                    resp.sendRedirect(redirectUrl);
                    return;
            }

            resp.sendRedirect(req.getContextPath() + "/user/employee");

        } catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/user/employee");
        }
    }
    }

