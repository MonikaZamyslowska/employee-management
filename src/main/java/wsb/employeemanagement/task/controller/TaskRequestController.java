package wsb.employeemanagement.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.dto.TaskRequestDto;
import wsb.employeemanagement.task.mapper.TaskRequestMapper;
import wsb.employeemanagement.task.service.TaskRequestService;

import javax.annotation.security.RolesAllowed;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("*")
@RequestMapping("/taskRequest")
public class TaskRequestController {
    private TaskRequestService taskRequestService;
    private TaskRequestMapper taskRequestMapper;

    @Autowired
    public TaskRequestController(TaskRequestService taskRequestService, TaskRequestMapper taskRequestMapper) {
        this.taskRequestService = taskRequestService;
        this.taskRequestMapper = taskRequestMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PM"})
    public TaskRequestDto createTaskRequest(@RequestBody final TaskRequestDto taskRequestDto) {
        return taskRequestMapper.mapTaskRequestToDto(taskRequestService.createNewTaskRequest(taskRequestMapper.mapDtoToTaskRequest(taskRequestDto)));
    }

    @PutMapping(value = "/accept", consumes = APPLICATION_JSON_VALUE)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PM"})
    public TaskRequestDto acceptTaskRequest(@RequestBody TaskRequestDto taskRequestDto) {
        return taskRequestMapper.mapTaskRequestToDto(taskRequestService.acceptTaskRequest(taskRequestMapper.mapDtoToTaskRequest(taskRequestDto)));
    }

    @PutMapping(value = "/reject", consumes = APPLICATION_JSON_VALUE)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_PM"})
    public TaskRequestDto rejectTaskRequest(@RequestBody TaskRequestDto taskRequestDto) {
        return taskRequestMapper.mapTaskRequestToDto(taskRequestService.rejectTaskRequest(taskRequestMapper.mapDtoToTaskRequest(taskRequestDto)));
    }

    @GetMapping()
    public List<TaskRequestDto> getTaskRequestsByTask(@RequestBody Task task) {
        return taskRequestMapper.mapTaskRequestListToDto(taskRequestService.getAllTaskRequestByTask(task));
    }
}
