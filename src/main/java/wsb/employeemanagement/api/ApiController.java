package wsb.employeemanagement.api;



import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.exception.EmployeeNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ApiController {
    private EmployeeService employeeService;

    @Autowired
    public ApiController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login")
    @RolesAllowed({"User", "Moderator", "Admin"})
    public String login(Principal principal, HttpServletRequest request, Model model) throws ServletException {
        try {
            Employee employee = employeeService.getEmployeeByUsername(principal.getName());
            model.addAttribute("employee", employee);
            return "employee_main";
        } catch (EmployeeNotFoundException e) {
            request.logout();
            return "index";
        }
    }

    @GetMapping("/logout")
    @RolesAllowed({"User", "Moderator", "Admin"})
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "index";
    }

//    TODO: if token exist then redirect to the other page
    @GetMapping(path = "/")
    public String index() {
        return "index";
    }
}
