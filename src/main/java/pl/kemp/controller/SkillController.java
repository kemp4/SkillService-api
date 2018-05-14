package pl.kemp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kemp.models.Skill;
import pl.kemp.models.dto.SkillNewDTO;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/skills")
    public void addSkill(@RequestBody SkillNewDTO skillNewDTO) {
        skillsService.addNewSkill(skillNewDTO);
     }
}
