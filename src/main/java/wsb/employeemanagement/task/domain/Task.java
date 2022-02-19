package wsb.employeemanagement.task.domain;

import lombok.Getter;
import lombok.Setter;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.project.domain.Project;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;

    @Size(max = 100)
    @Column(name = "capacity")
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
