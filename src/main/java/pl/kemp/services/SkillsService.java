package pl.kemp.services;

import java.util.List;
import org.springframework.stereotype.Service;
import pl.kemp.models.Skill;
import pl.kemp.models.dto.SkillNewDTO;

@Service
public interface SkillsService {
    List<Skill> getAllSkills();
    void addNewSkill(SkillNewDTO skillName);
}
