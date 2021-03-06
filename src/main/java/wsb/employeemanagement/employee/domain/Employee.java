package wsb.employeemanagement.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.task.domain.Task;
import wsb.employeemanagement.task.domain.TaskRequest;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private Grade grade;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JoinTable(
            name = "employee_skill",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private List<Skill> skillList;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "employee_roles",
            joinColumns = {@JoinColumn(name = "employee_id")}

    )
    @Column(name = "roles", nullable = false)
    private List<Role> roles;

    @OneToMany(
            targetEntity = Task.class,
            mappedBy = "employee",
            cascade = {CascadeType.ALL, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Task> tasks;


    @OneToMany(
            targetEntity = TaskRequest.class,
            mappedBy = "employee",
            cascade = {CascadeType.ALL, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<TaskRequest> taskRequests;

    @OneToMany(
            targetEntity = Project.class,
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Project> ownerProject;

    @Override
    public String toString() {
        return firstName + ' ' +  lastName + ' ' + email;
    }
}
