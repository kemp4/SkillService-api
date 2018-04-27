package pl.kemp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kemp.models.Skill;
import pl.kemp.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public void createUser(String skillName) {
        //TODO
    }
    @GetMapping("/users/{id}")
    public Skill getUser(@RequestParam String id) {
        return null;//TODO
    }
    @GetMapping("/users/alldetails/{userId}")
    public Skill getUserWithAllDetails(@RequestParam String id) {
        return null;//TODO
    }
    @PutMapping("/users/alldetails")
    public Skill updateDetails() {
        return null;//TODO
    }
    @GetMapping("/users/details/{id}")
    public Skill getUserWithDetails(@RequestParam String id) {
        return null;//TODO
    }
    @PutMapping("/users/skills")
    public Skill addNewSillToUser() {
        return null;//TODO
    }
}
