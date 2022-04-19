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
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.skill.domain.SkillCategory;
import wsb.employeemanagement.skill.domain.dto.SkillDto;
import wsb.employeemanagement.skill.service.SkillService;
import wsb.employeemanagement.task.domain.OpenCloseStatus;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequest;
import wsb.employeemanagement.task.domain.TaskRequestStatus;
import wsb.employeemanagement.task.domain.dto.TaskDto;
import wsb.employeemanagement.task.service.TaskRequestService;
import wsb.employeemanagement.task.service.TaskService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ModelViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelViewController.class);

    private final EmployeeService employeeService;
    private ProjectService projectService;
    private SkillService skillService;
    private TaskService taskService;
    private TaskRequestService taskRequestService;

    @Autowired
    public ModelViewController(EmployeeService employeeService,
                               ProjectService projectService,
                               SkillService skillService, TaskService taskService,
                               TaskRequestService taskRequestService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.skillService = skillService;
        this.taskService = taskService;
        this.taskRequestService = taskRequestService;
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

    @GetMapping("/allEmployees")
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView getEmployees() {
        ModelAndView modelAndView = new ModelAndView("list-employees");
        modelAndView.addObject("employees", employeeService.getAllEmployees());
        return modelAndView;
    }

    @GetMapping("/employeeDetails/{employeeId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_EMPLOYEE", "ROLE_PM"})
    public ModelAndView allEmployeeTaskModel(@PathVariable long employeeId) {
        ModelAndView modelAndView = new ModelAndView("employee-details");
        Employee employee = employeeService.getEmployeeById(employeeId);

        List<Task> tasks = taskService.getAllByEmployee(employee);
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("skills", employee.getSkillList());

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
    public String createOrUpdateProject(Project project, Principal principal) {
        Employee employee = employeeService.getEmployeeByUsername(principal.getName());
        try {
            if (project.getProjectStatus().equals(OpenCloseStatus.CLOSED)) {
                projectService.closeProject(project);
            } else {
                projectService.saveProject(project);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/allProjects/" + employee.getId();
    }

    @GetMapping("/projectDetails/{projectId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public ModelAndView getProjectsDetails(@PathVariable long projectId, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("project_details");
        Employee employee = employeeService.getEmployeeByUsername(principal.getName());
        Project project = projectService.getProjectById(projectId);
        boolean isEmployeePM = project.getOwner().equals(employee);
        List<Task> tasks = project.getTaskList();
        List<Task> userTasks = tasks.stream()
                .filter(task -> task.getTaskStatus().equals(OpenCloseStatus.OPEN))
                .collect(Collectors.toList());
        modelAndView.addObject("openStatus", OpenCloseStatus.OPEN);
        modelAndView.addObject("closeStatus", OpenCloseStatus.CLOSED);
        modelAndView.addObject("project", project);
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("userTasks", userTasks);
        modelAndView.addObject("isEmployeePM", isEmployeePM);
        return modelAndView;
    }

    // task

    @GetMapping("/addTask/{projectId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PM"})
    public ModelAndView addTaskModel(@PathVariable long projectId) {
        ModelAndView modelAndView = new ModelAndView("add-task");
        TaskDto taskDto = new TaskDto();
        Project project = projectService.getProjectById(projectId);
        taskDto.setProject(project);
        List<OpenCloseStatus> openCloseStatuses = Arrays.asList(OpenCloseStatus.values().clone());
        List<Skill> preferredSkills = skillService.getAllSkills();
        List<Grade> grades = Arrays.asList(Grade.values().clone());
        modelAndView.addObject("task", taskDto);
        modelAndView.addObject("preferredSkills", preferredSkills);
        modelAndView.addObject("statuses", openCloseStatuses);
        modelAndView.addObject("grades", grades);
        modelAndView.addObject("project", project);
        return modelAndView;
    }

    @GetMapping("/updateTask/{projectId}/{taskId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView updateTaskModel(@PathVariable long projectId, @PathVariable long taskId) {
        ModelAndView modelAndView = new ModelAndView("update-task");
        Task task = taskService.getTaskById(taskId);
        Project project = projectService.getProjectById(projectId);
        List<OpenCloseStatus> openCloseStatuses = Arrays.asList(OpenCloseStatus.values().clone());
        modelAndView.addObject("task", task);
        modelAndView.addObject("statuses", openCloseStatuses);
        modelAndView.addObject("project", project);
        modelAndView.addObject("preferredSkills", task.getPreferredSkillList());
        modelAndView.addObject("grade", task.getGrade());
        return modelAndView;
    }

    @PostMapping("/saveTask")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN"})
    public String createOrUpdateTask(Task task) {
        try {
            taskService.saveTask(task);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/projectDetails/" + task.getProject().getId();
    }

    @PostMapping("/reopenTask/{taskId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public String saveSkillSet(@PathVariable long taskId) {
        Task task = taskService.getTaskById(taskId);
        try {
            taskService.reopenTask(task);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/projectDetails/" + task.getProject().getId();
    }

    //TaskRequest

    @PostMapping("/createRequest/{taskId}")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN", "ROLE_EMPLOYEE"})
    public String createTaskRequest(@PathVariable long taskId, Principal principal) {
        Employee employee = employeeService.getEmployeeByUsername(principal.getName());
        Task task = taskService.getTaskById(taskId);
        try {
            TaskRequest taskRequest = new TaskRequest();
            taskRequest.setTask(task);
            taskRequest.setEmployee(employee);
            taskRequestService.createNewTaskRequest(taskRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/projectDetails/" + task.getProject().getId();
    }

    @GetMapping("/taskRequests/{taskId}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PM"})
    public ModelAndView allTaskRequestsModel(@PathVariable long taskId, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("list-task-requests");
        Employee employee = employeeService.getEmployeeByUsername(principal.getName());
        Task task = taskService.getTaskById(taskId);
        boolean isEmployeeOwner = task.getProject().getOwner().equals(employee);
        List<TaskRequest> taskRequests = taskRequestService.getAllTaskRequestByTask(task);
        modelAndView.addObject("task", task);
        modelAndView.addObject("taskRequests", taskRequests);
        modelAndView.addObject("isEmployeeOwner", isEmployeeOwner);
        modelAndView.addObject("waitingStatus", TaskRequestStatus.WAITING_FOR_APPROVE);
        return modelAndView;
    }

    @GetMapping("/taskRequests/employee/{employeeId}")
    @RolesAllowed({"ROLE_EMPLOYEE"})
    public ModelAndView allEmployeeRequestsModel(@PathVariable long employeeId) {
        ModelAndView modelAndView = new ModelAndView("list-task-requests-employee");
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<TaskRequest> taskRequestsByUser = taskRequestService.getAllTaskRequestByEmployee(employee);
        modelAndView.addObject("taskRequestsUser", taskRequestsByUser);
        modelAndView.addObject("waitingStatus", TaskRequestStatus.WAITING_FOR_APPROVE);
        return modelAndView;
    }

    @PostMapping("/rejectTaskRequest/{taskRequestId}")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN"})
    public String rejectTaskRequest(@PathVariable long taskRequestId) {
        TaskRequest taskRequest = taskRequestService.getById(taskRequestId);
        try {
            taskRequestService.rejectTaskRequest(taskRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/taskRequests/" + taskRequest.getTask().getId();
    }

    @PostMapping("/rejectTaskRequest/employee/{taskRequestId}")
    @RolesAllowed({"ROLE_EMPLOYEE"})
    public String rejectTaskRequestByEmployee(@PathVariable long taskRequestId) {
        TaskRequest taskRequest = taskRequestService.getById(taskRequestId);
        try {
            taskRequestService.rejectTaskRequest(taskRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/taskRequests/employee/" + taskRequest.getEmployee().getId();
    }

    @PostMapping("/acceptTaskRequest/{taskRequestId}")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN"})
    public String acceptTaskRequest(@PathVariable long taskRequestId) {
        TaskRequest taskRequest = taskRequestService.getById(taskRequestId);
        try {
            taskRequestService.acceptTaskRequest(taskRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/taskRequests/" + taskRequest.getTask().getId();
    }

    //    skills

    @GetMapping("/addSkill/employee/{employeeId}")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN", "ROLE_EMPLOYEE"})
    public ModelAndView addEmployeeSkillModel(@PathVariable long employeeId) {
        ModelAndView modelAndView = new ModelAndView("add-employee-skill");
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<Skill> skills = skillService.getAllSkills();
        SkillDto emptySkill = new SkillDto();
        skills.removeAll(employee.getSkillList());
        modelAndView.addObject("skills", skills);
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("emptySkill", emptySkill);
        return modelAndView;
    }

    @PostMapping("/removeSkill/{skillId}/employee/{employeeId}")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN", "ROLE_EMPLOYEE"})
    public String removeEmployeeSkill(@PathVariable long employeeId, @PathVariable long skillId) {
        try {
            employeeService.removeSkillFromEmployeeList(employeeId, skillId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/employeeDetails/" + employeeId;
    }

    @PostMapping("/saveEmployeeSkill/{employeeId}")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN", "ROLE_EMPLOYEE"})
    public String saveEmployeeSkill(@PathVariable long employeeId, Skill skill) {
        try {
            Skill newSkill = skillService.getSkillById(skill.getId());
            employeeService.updateEmployeeSkills(employeeId, newSkill);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/employeeDetails/" + employeeId;
    }

    @GetMapping("/addSkillSet")
    @RolesAllowed({"ROLE_PM", "ROLE_ADMIN", "ROLE_EMPLOYEE"})
    public ModelAndView addSkillSet() {
        ModelAndView modelAndView = new ModelAndView("add-skill-set");
        List<SkillCategory> categories = Arrays.asList(SkillCategory.values());
        SkillDto emptySkill = new SkillDto();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("skill", emptySkill);
        return modelAndView;
    }

    @PostMapping("/saveSkillSet")
    @RolesAllowed({"ROLE_ADMIN"})
    public String saveSkillSet(Skill skill) {
        try {
            skillService.createSetSkill(skill);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "operation-failed";
        }
        return "redirect:/allSkills";
    }

    @GetMapping("/allSkills")
    @RolesAllowed({"ROLE_ADMIN"})
    public ModelAndView skillList() {
        ModelAndView modelAndView = new ModelAndView("list-skills");
        List<Skill> skills = skillService.getAllSkills();
        modelAndView.addObject("skills", skills);
        return modelAndView;
    }
}
