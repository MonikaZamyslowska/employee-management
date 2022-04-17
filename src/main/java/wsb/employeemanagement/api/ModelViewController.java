package wsb.employeemanagement.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.employee.domain.dto.EmployeeDto;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.exception.EmployeeNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class ModelViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelViewController.class);

    private EmployeeService employeeService;

    @Autowired
    public ModelViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //login and logout view

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

    //employee view

    @GetMapping("/desktop")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView login(Principal principal, HttpServletRequest request) throws ServletException {
        ModelAndView modelAndView;
        System.out.println("kupa");
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

    @GetMapping("/addEmployee")
    public ModelAndView addEmployeeModel() {
        ModelAndView modelAndView = new ModelAndView("add-employee");
        EmployeeDto newEmployee = new EmployeeDto();
        List<Grade> grades = Arrays.asList(Grade.values().clone());
        List<Role> roles = Arrays.asList(Role.values().clone());
        modelAndView.addObject("employee", newEmployee);
        modelAndView.addObject("grades", grades);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/saveEmployee")
    @RolesAllowed({"ROLE_ADMIN"})
    public String createOrUpdateEmployee(Employee employee) {
        try {
            if (employeeService.getEmployeeByUsername(employee.getUsername()) != null) {
                employeeService.updateEmployeeKeycloack(employee);
                employeeService.saveEmployee(employee);
            } else {
                employeeService.createEmployee(employee);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/allEmployees";
    }

    @GetMapping("/keycloak/updateEmployee/{employeeId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView updateEmployeeModel(@PathVariable long employeeId) {
        ModelAndView modelAndView = new ModelAndView("add-employee");
        Employee employee = employeeService.getEmployeeById(employeeId);
        modelAndView.addObject("employee", employee);

        List<Grade> allGrades = Arrays.asList(Grade.values().clone());
        List<Role> allRoles = Arrays.asList(Role.values().clone());
        modelAndView.addObject("grades", allGrades);
        modelAndView.addObject("roles", allRoles);

        return modelAndView;
    }

    @GetMapping("/keycloak/updatePassword/{employeeId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView updateEmployeePasswordModel(@PathVariable long employeeId) {
        ModelAndView modelAndView = new ModelAndView("update_employee_password");
        Employee employee = employeeService.getEmployeeById(employeeId);
        modelAndView.addObject("employee", employee);


        return modelAndView;
    }

    @GetMapping("/deleteEmployee/{employeeId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public String deleteEmployee(@PathVariable long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/allEmployees";
    }

    @GetMapping("/allEmployees")
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView getEmployees() {
        ModelAndView modelAndView = new ModelAndView("list-employees");
        modelAndView.addObject("employees", employeeService.getAllEmployees());
        return modelAndView;
    }
}
