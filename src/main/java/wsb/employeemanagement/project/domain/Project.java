package wsb.employeemanagement.project.domain;

import lombok.*;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.task.domain.OpenCloseStatus;
import wsb.employeemanagement.task.domain.Task;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private OpenCloseStatus projectStatus;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    @OneToMany(
            targetEntity = Task.class,
            mappedBy = "project",
            cascade = {CascadeType.ALL, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Task> taskList;

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectStatus=" + projectStatus +
                ", owner=" + owner +
                '}';
    }
}
