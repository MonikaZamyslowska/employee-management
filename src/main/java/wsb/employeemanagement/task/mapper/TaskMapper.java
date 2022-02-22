package wsb.employeemanagement.task.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.dto.TaskDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskMapper {

    public TaskDto mapTaskToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getProject(),
                task.getRole(),
                task.getGrade(),
                task.getCapacity(),
                task.getEmployee()
        );
    }

    public Task mapDtoToTask(TaskDto taskDto) {
        return new Task(
                taskDto.getId(),
                taskDto.getProject(),
                taskDto.getRole(),
                taskDto.getGrade(),
                taskDto.getCapacity(),
                taskDto.getEmployee()
        );
    }

    public List<TaskDto> mapTaskListToDto(List<Task> tasks) {
        return tasks.stream()
                .map(task -> mapTaskToDto(task))
                .collect(Collectors.toList());
    }
}
