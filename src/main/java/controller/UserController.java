package controller;

import ch.zli.vs.pulse.Model.User;
import ch.zli.vs.pulse.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }



    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return service.getUser(id);
    }
}
