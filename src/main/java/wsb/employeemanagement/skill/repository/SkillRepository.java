package wsb.employeemanagement.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.employeemanagement.skill.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    void removeById(long skillId);
}
