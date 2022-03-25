package wsb.employeemanagement.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.task.domain.dto.TaskDto;
import wsb.employeemanagement.task.mapper.TaskMapper;
import wsb.employeemanagement.task.service.TaskService;

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

    @PreAuthorize("hasAnyRole('SUPER_USER')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public TaskDto createTask(@RequestBody final TaskDto taskDto) {
        return taskMapper.mapTaskToDto(taskService.saveTask(taskMapper.mapDtoToTask(taskDto)));
    }

    @PreAuthorize("hasAnyRole('SUPER_USER')")
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody final TaskDto taskDto) {
        return taskMapper.mapTaskToDto(taskService.saveTask(taskMapper.mapDtoToTask(taskDto)));
    }

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskMapper.mapTaskListToDto(taskService.getAllTasks());
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTaskById(@PathVariable long taskId) {
        return taskMapper.mapTaskToDto(taskService.getTaskById(taskId));
    }

    @PreAuthorize("hasAnyRole('SUPER_USER')")
    @DeleteMapping(value = "{taskId}")
    public void removeTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
    }
}
