package wsb.employeemanagement.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.task.domain.TaskStatus;
import wsb.employeemanagement.task.domain.dto.TaskDto;
import wsb.employeemanagement.task.mapper.TaskMapper;
import wsb.employeemanagement.task.service.TaskService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("*")
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    private TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @RolesAllowed({"ROLE_ADMIN"})
    public TaskDto createTask(@RequestBody final TaskDto taskDto) {
        return taskMapper.mapTaskToDto(taskService.saveTask(taskMapper.mapDtoToTask(taskDto)));
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody final TaskDto taskDto) {
        return taskMapper.mapTaskToDto(taskService.saveTask(taskMapper.mapDtoToTask(taskDto)));
    }

    @GetMapping
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public List<TaskDto> getTasks() {
        return taskMapper.mapTaskListToDto(taskService.getAllTasks());
    }

    @GetMapping("{taskId}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public TaskDto getTaskById(@PathVariable long taskId) {
        return taskMapper.mapTaskToDto(taskService.getTaskById(taskId));
    }

    @DeleteMapping("{taskId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public void removeTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping("employee")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public List<TaskDto> getTasksByEmployee(@RequestBody Employee employee) {
        return taskMapper.mapTaskListToDto(taskService.getAllByEmployee(employee));
    }

    @GetMapping("taskStatus/{taskStatus}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public List<TaskDto> getTasksByTaskStatus(@PathVariable TaskStatus taskStatus) {
        return taskMapper.mapTaskListToDto(taskService.getAllByStatus(taskStatus));
    }

    @GetMapping("role/{role}")
    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_PM", "ROLE_ADMIN"})
    public List<TaskDto> getTasksByTaskStatus(@PathVariable Role role) {
        return taskMapper.mapTaskListToDto(taskService.getAllByRole(role));
    }
}
