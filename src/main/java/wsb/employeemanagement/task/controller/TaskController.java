package wsb.employeemanagement.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public TaskDto createTask(@RequestBody final TaskDto taskDto) {
        return null;
    }

    @PutMapping
    public TaskDto updateTask(@RequestBody final TaskDto taskDto) {
        return null;
    }

    @GetMapping
    public List<TaskDto> getTasks() {
        return null;
    }

    @GetMapping(value = "/{taskId}")
    public TaskDto getTaskById(@PathVariable long taskId) {
        return null;
    }

    @DeleteMapping(value = "/{taskId}")
    public void removeTask(@PathVariable long taskId) {

    }
}
