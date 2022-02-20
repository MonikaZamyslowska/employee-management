package wsb.employeemanagement.skill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.skill.domain.SkillCategory;
import wsb.employeemanagement.skill.domain.SkillLevel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {
    private String skillName;
    private SkillCategory skillCategory;
    private SkillLevel skillLevel;
    private List<Employee> employees;
}
