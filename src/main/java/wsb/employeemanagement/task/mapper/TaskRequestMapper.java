package wsb.employeemanagement.task.mapper;

import org.springframework.stereotype.Component;
import wsb.employeemanagement.skill.domain.dto.TaskRequestDto;
import wsb.employeemanagement.task.domain.TaskRequest;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskRequestMapper {

    public TaskRequestDto mapTaskRequestToDto(TaskRequest taskRequest) {
        return new TaskRequestDto(
                taskRequest.getId(),
                taskRequest.getTask(),
                taskRequest.getEmployee(),
                taskRequest.getTaskRequestStatus()
        );
    }

    public TaskRequest mapDtoToTaskRequest(TaskRequestDto taskRequestDto) {
        return new TaskRequest(
                taskRequestDto.getId(),
                taskRequestDto.getTask(),
                taskRequestDto.getEmployee(),
                taskRequestDto.getTaskRequestStatus()
        );
    }

    public List<TaskRequestDto> mapTaskRequestListToDto(List<TaskRequest> taskRequests) {
        return taskRequests.stream()
                .map(taskRequest -> mapTaskRequestToDto(taskRequest))
                .collect(Collectors.toList());
    }
}
