package wsb.employeemanagement.employee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequest;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Grade grade;
    private Integer capacity;
    private List<Skill> skillList;
    private List<Role> roles;
    private List<Task> tasks;
    private List<TaskRequest> taskRequests;
    private Project ownerProject;

//    for view model
    public EmployeeDto(Long id, String username, String firstName, String lastName, String email,
                       Grade grade, Integer capacity) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.capacity = capacity;
    }

//    for tests only
    public EmployeeDto(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }
}
