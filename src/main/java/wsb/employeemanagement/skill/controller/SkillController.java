package wsb.employeemanagement.skill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsb.employeemanagement.skill.domain.dto.SkillDto;
import wsb.employeemanagement.skill.mapper.SkillMapper;
import wsb.employeemanagement.skill.service.SkillService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("*")
@RequestMapping("/skills")
public class SkillController {
    private SkillService skillService;
    private SkillMapper skillMapper;

    @Autowired
    public SkillController(SkillService skillService, SkillMapper skillMapper) {
        this.skillService = skillService;
        this.skillMapper = skillMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public List<SkillDto> createSkill(@RequestBody SkillDto skillDto) {
        return skillMapper.mapSkillListToDto(skillService.createSetSkill(skillMapper.mapDtoToSkill(skillDto)));
    }

    @GetMapping
    public List<SkillDto> getSkills() {
        return skillMapper.mapSkillListToDto(skillService.getAllSkills());
    }

    @GetMapping("{skillId}")
    public SkillDto getSkillById(@PathVariable long skillId) {
        return skillMapper.mapSkillToDto(skillService.getSkillById(skillId));
    }

    @DeleteMapping("{skillId}")
    public void deleteSkill(@PathVariable long skillId) {
        skillService.deleteSkill(skillId);
    }
}
