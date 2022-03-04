package wsb.employeemanagement.skill.mapper;

import org.springframework.stereotype.Component;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.skill.domain.dto.SkillDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SkillMapper {

    public SkillDto mapSkillToDto(Skill skill) {
        return new SkillDto(
                skill.getId(),
                skill.getSkillName(),
                skill.getSkillCategory(),
                skill.getSkillLevel(),
                skill.getEmployees()
        );
    }

    public Skill mapDtoToSkill(SkillDto skillDto) {
        return new Skill(
                skillDto.getId(),
                skillDto.getSkillName(),
                skillDto.getSkillCategory(),
                skillDto.getSkillLevel(),
                skillDto.getEmployees()
        );
    }

    public List<SkillDto> mapSkillListToDto(List<Skill> skills) {
        return skills.stream()
                .map(skill -> mapSkillToDto(skill))
                .collect(Collectors.toList());
    }
}
