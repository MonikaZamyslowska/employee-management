package wsb.employeemanagement.skill.domain.dto;

import lombok.*;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.skill.domain.SkillCategory;
import wsb.employeemanagement.skill.domain.SkillLevel;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {
    private Long id;
    private String skillName;
    private SkillCategory skillCategory;
    private SkillLevel skillLevel;
}
