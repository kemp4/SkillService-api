package pl.kemp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kemp.models.Skill;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kemp.services.SkillsService;


import java.util.List;

@RestController
public class SkillController {

    @Autowired
    private SkillsService skillsService;

    @GetMapping("/skills")
    public List<Skill> getAllSkills(){
        List<Skill> result = skillsService.getAllSkills();
        return result;
    }
    @PostMapping("/skills")
    public Skill addSkill(String skillName) {
        skillsService.addNewSkill(skillName);
        return null;//TODO
    }
}
