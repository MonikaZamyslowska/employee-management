package wsb.employeemanagement.task.domain;

import wsb.employeemanagement.employee.domain.JobLevel;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.project.domain.Project;

public class Task {
    private Project project;
    private Role role;
    private JobLevel jobLevel;
    private Integer capacity;
}
