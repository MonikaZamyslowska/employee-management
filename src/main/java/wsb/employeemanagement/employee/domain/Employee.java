package wsb.employeemanagement.employee.domain;

import lombok.Getter;
import lombok.Setter;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.task.domain.Task;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;

    @Column(name = "capacity")
    @Size(max = 100)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @ManyToMany(cascade = CascadeType.ALL)
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
    @Column(name = "roles")
    private List<Role> roles;

    @OneToMany(
            targetEntity = Task.class,
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Task> tasks;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project ownerProject;
}
