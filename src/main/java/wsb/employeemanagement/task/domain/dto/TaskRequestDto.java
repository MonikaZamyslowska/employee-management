package wsb.employeemanagement.task.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequestStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {
    private Long id;
    private Task task;
    private Employee employee;
    private TaskRequestStatus taskRequestStatus;
}
