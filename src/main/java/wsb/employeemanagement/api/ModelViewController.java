package wsb.employeemanagement.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.employee.service.EmployeeService;
import wsb.employeemanagement.exception.EmployeeNotFoundException;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.project.domain.dto.ProjectDto;
import wsb.employeemanagement.project.service.ProjectService;
import wsb.employeemanagement.task.domain.OpenCloseStatus;
import wsb.employeemanagement.task.domain.Task;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class ModelViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelViewController.class);

    private final EmployeeService employeeService;
    private ProjectService projectService;

    @Autowired
    public ModelViewController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
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
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView addEmployeeModel() {
        ModelAndView modelAndView = new ModelAndView("add-employee");
        Employee newEmployee = new Employee();
        List<Grade> grades = Arrays.asList(Grade.values().clone());
        List<Role> roles = Arrays.asList(Role.values().clone());
        modelAndView.addObject("employee", newEmployee);
        modelAndView.addObject("grades", grades);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/saveEmployee")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
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
        return "redirect:/desktop";
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

    // Projects

    @GetMapping("/allProjects/{employeeId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView getProjects(@PathVariable long employeeId) {
        ModelAndView modelAndView = new ModelAndView("list-projects");
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<Project> projects = projectService.getAllProjects();
        List<Project> userProjects = employee.getOwnerProject();
        modelAndView.addObject("projects", projects);
        modelAndView.addObject("projectsPM", userProjects);
        return modelAndView;
    }

    @GetMapping("/addProject")
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView addProjectModel() {
        ModelAndView modelAndView = new ModelAndView("add-project");
        ProjectDto projectDto = new ProjectDto();
        List<Employee> employees = employeeService.getEmployeeByRole(Role.PM);
        List<OpenCloseStatus> openCloseStatuses = Arrays.asList(OpenCloseStatus.values().clone());
        modelAndView.addObject("project", projectDto);
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("statuses", openCloseStatuses);
        return modelAndView;
    }

    @GetMapping("/updateProject/{projectId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView updateProjectModel(@PathVariable long projectId) {
        ModelAndView modelAndView = new ModelAndView("add-project");
        Project project = projectService.getProjectById(projectId);
        List<Employee> employees = employeeService.getEmployeeByRole(Role.PM);
        List<OpenCloseStatus> openCloseStatuses = Arrays.asList(OpenCloseStatus.values().clone());
        modelAndView.addObject("project", project);
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("statuses", openCloseStatuses);
        return modelAndView;
    }

    @PostMapping("/saveProject")
    @RolesAllowed({"ROLE_ADMIN"})
    public String createOrUpdateProject(Project project) {
        try {
            projectService.saveProject(project);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/desktop";
    }

    @GetMapping("/projectDetails/{projectId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView getProjectsDetails(@PathVariable long projectId) {
        ModelAndView modelAndView = new ModelAndView("project_details");
        Project project = projectService.getProjectById(projectId);
        List<Task> tasks = project.getTaskList();
        modelAndView.addObject("project", project);
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }
}
