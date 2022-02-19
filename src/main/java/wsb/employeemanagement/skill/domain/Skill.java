package wsb.employeemanagement.skill.domain;

import lombok.Getter;
import lombok.Setter;
import wsb.employeemanagement.employee.domain.Employee;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    @Column(name = "skill_name")
    private String skillName;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_category")
    private SkillCategory skillCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevel skillLevel;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "employee_skills",
            joinColumns = {@JoinColumn(name = "skill_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees;
}
