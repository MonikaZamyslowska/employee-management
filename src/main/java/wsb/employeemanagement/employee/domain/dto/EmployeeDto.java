package wsb.employeemanagement.employee.domain.dto;

import lombok.*;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.user.domain.User;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private User user;
    private Grade grade;
    private Integer capacity;
    private Employee supervisor;
    private List<Skill> skillList;
    private List<Role> roles;
    private List<Task> tasks;
    private Project ownerProject;
}
