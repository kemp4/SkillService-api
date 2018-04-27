package pl.kemp.services;

import java.util.List;
import org.springframework.stereotype.Service;
import pl.kemp.models.Skill;

@Service
public interface SkillsService {
    List<Skill> getAllSkills();
    void addNewSkill(String skillName);
}
