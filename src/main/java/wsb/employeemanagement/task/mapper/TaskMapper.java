package wsb.employeemanagement.task.mapper;

import org.springframework.stereotype.Component;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.dto.TaskDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskDto mapTaskToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTaskStatus(),
                task.getProject(),
                task.getGrade(),
                task.getCapacity(),
                task.getEmployee(),
                task.getTaskRequests(),
                task.getPreferredSkillList()
        );
    }

    public Task mapDtoToTask(TaskDto taskDto) {
        return new Task(
                taskDto.getId(),
                taskDto.getTaskStatus(),
                taskDto.getProject(),
                taskDto.getGrade(),
                taskDto.getCapacity(),
                taskDto.getEmployee(),
                taskDto.getTaskRequests(),
                taskDto.getPreferredSkillList()
        );
    }

    public List<TaskDto> mapTaskListToDto(List<Task> tasks) {
        return tasks.stream()
                .map(this::mapTaskToDto)
                .collect(Collectors.toList());
    }
}
