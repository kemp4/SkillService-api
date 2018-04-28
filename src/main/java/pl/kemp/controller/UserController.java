package pl.kemp.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kemp.models.Skill;
import pl.kemp.models.dto.*;
import pl.kemp.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users")
    public void createUser(@RequestBody UserNewDTO userNew) {
       userService.createUser(userNew);
    }
    @GetMapping("/users/{id}")
    public UserCreatedDTO getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/alldetails/{userId}")
    public DetailsFullDTO getUserWithAllDetails(@PathVariable String userId) {
        return userService.getAllUserDetailsById(userId);
    }

    @PutMapping("/users/details")
    public UserFullDTO updateDetails(@RequestBody DetailsNewDTO details) {
        return userService.changeUserDetails(details);
    }

    @GetMapping("/users/details/{id}")
    public DetailsDTO getUserWithDetails(@PathVariable String id) {
        return userService.getUserDetailsById(id);
    }

    @PutMapping("/users/skills")
    public UserFullDTO addNewSkillToUser(@RequestBody SaveSkillsRequest skill) {
        return userService.addSkillToUser(skill);
    }
}
