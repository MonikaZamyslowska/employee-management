package wsb.employeemanagement.skill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsb.employeemanagement.exception.SkillNotFoundException;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.skill.repository.SkillRepository;

import java.util.List;

@Service
public class SkillService {
    private SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill getSkillById(long skillId) {
        return skillRepository.findById(skillId).orElseThrow(SkillNotFoundException::new);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public void deleteSkill(long skillId) {
        skillRepository.removeById(skillId);
    }
}
