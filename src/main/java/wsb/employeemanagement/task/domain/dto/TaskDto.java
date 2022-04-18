package wsb.employeemanagement.task.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.task.domain.OpenCloseStatus;
import wsb.employeemanagement.task.domain.TaskRequest;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private OpenCloseStatus taskStatus;
    private Project project;
    private Grade grade;
    private Integer capacity;
    private Employee employee;
    private List<TaskRequest> taskRequests;
    private List<Skill> preferredSkillList;
}
