package pl.kemp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kemp.models.dto.*;
import pl.kemp.services.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users")
    public void createUser(@RequestBody UserNewDTO userNew, HttpServletResponse response) {
        String location =userService.createUser(userNew);
        response.setHeader("Location",location);
    }

    @GetMapping("/users/{id}")
    public UserCreatedDTO getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/alldetails/{userId}")
    public DetailsFullDTO getUserWithAllDetails(@PathVariable String userId) {
        return userService.getAllUserDetailsById(userId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/users/details")
    public void updateDetails(@RequestBody DetailsNewDTO details, HttpServletResponse response) {

        String location =userService.changeUserDetails(details);
        response.setHeader("Location",location);
    }

    @GetMapping("/users/details/{id}")
    public DetailsDTO getUserWithDetails(@PathVariable String id) {
        return userService.getUserDetailsById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/users/skills")
    public UserFullDTO addNewSkillToUser(@RequestBody SaveSkillsRequest skill) {
        return userService.addSkillToUser(skill);
    }
}
