package wsb.employeemanagement.task.domain;

import lombok.*;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.employee.domain.Role;
import wsb.employeemanagement.project.domain.Project;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private Grade grade;

    @Size(max = 100)
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
