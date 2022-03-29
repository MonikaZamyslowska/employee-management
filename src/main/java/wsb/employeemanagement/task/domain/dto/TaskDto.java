package wsb.employeemanagement.task.domain.dto;

import lombok.*;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.task.domain.TaskRequest;
import wsb.employeemanagement.task.domain.TaskStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private TaskStatus taskStatus;
    private Project project;
    private Role role;
    private Grade grade;
    private Integer capacity;
    private Employee employee;
    private List<TaskRequest> taskRequests;
}
