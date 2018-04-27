package pl.kemp.services.implementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kemp.models.Skill;
import pl.kemp.repository.SkillsRepository;
import pl.kemp.services.SkillsService;

@Service
public class SkillServiceImpl implements SkillsService {

    @Autowired
    private SkillsRepository skillsRepository;

    @Override
    public List<Skill> getAllSkills() {
        return skillsRepository.getAllSkills();
    }

    @Override
    public void addNewSkill(String skillName) {

    }
}
