package wsb.employeemanagement.skill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.employeemanagement.exception.SkillNotFoundException;
import wsb.employeemanagement.skill.domain.Skill;
import wsb.employeemanagement.skill.domain.SkillLevel;
import wsb.employeemanagement.skill.repository.SkillRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Transactional
    public List<Skill> createSetSkill(Skill skill) {
        return Stream.of(SkillLevel.values())
                .map(level -> new Skill(skill.getSkillName(), skill.getSkillCategory(), level))
                .map(skill1 -> skillRepository.save(skill1))
                .collect(Collectors.toList());
    }

    public Skill getSkillById(long skillId) {
        return skillRepository.findById(skillId).orElseThrow(SkillNotFoundException::new);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Transactional
    public void deleteSkill(long skillId) {
        skillRepository.removeById(skillId);
    }
}
