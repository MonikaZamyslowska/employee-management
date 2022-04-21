package wsb.employeemanagement.task.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Grade;
import wsb.employeemanagement.project.domain.Project;
import wsb.employeemanagement.skill.domain.Skill;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

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
    private OpenCloseStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private Grade grade;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private List<TaskRequest> taskRequests;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_skill",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private List<Skill> preferredSkillList;

    @Override
    public String toString() {
        return "poziom: " + grade +
                ", dostępność: " + capacity + "%" +
                ", umiejętności: " +
                preferredSkillList.stream()
                        .map(skill -> skill.toString())
                        .collect(Collectors.joining("|", "(", ")"));
    }
}
