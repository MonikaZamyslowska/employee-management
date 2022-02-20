package wsb.employeemanagement.project.domain;

import lombok.Getter;
import lombok.Setter;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.task.domain.Task;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee owner;

    @OneToMany(
            targetEntity = Task.class,
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Task> taskList;
}
