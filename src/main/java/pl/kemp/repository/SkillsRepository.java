package pl.kemp.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import pl.kemp.models.Skill;

@Repository
public interface SkillsRepository {
    List<Skill> getAllSkills();
    void addSkill();
}
