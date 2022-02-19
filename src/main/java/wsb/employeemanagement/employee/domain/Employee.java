package wsb.employeemanagement.employee.domain;

import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.task.domain.Task;

import java.util.List;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private JobLevel jobLevel;
    private Integer capacity;
    private Employee supervisor;
    private List<Skill> skillList;
    private List<Role> roles;
    private List<Task> tasks;
}
