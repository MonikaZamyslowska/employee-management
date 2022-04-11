package wsb.employeemanagement.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/desktop")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView login(Principal principal, HttpServletRequest request) throws ServletException {
        ModelAndView modelAndView;

        try {
            modelAndView = new ModelAndView("employee-desktop");
            Employee employee = employeeService.getEmployeeByUsername(principal.getName());
            modelAndView.addObject("employee", employee);
        } catch (EmployeeNotFoundException e) {
            request.logout();
            modelAndView = new ModelAndView("login-failed");
        }

        return modelAndView;
    }

    @PostMapping("/logout")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return new ModelAndView("index");
    }

    @GetMapping(path = "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
