package murkeev.controller;

import murkeev.model.User;
import murkeev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/me/")
    public ResponseEntity<?> getUserData(@RequestHeader("Authorization") String token) {
        return userService.getUserData(token);
    }

    @PostMapping("/registration/")
    public ResponseEntity<?> registration(@RequestBody User user) {
        return userService.registration(user);
    }

    @PostMapping("/authorization/")
    public ResponseEntity<?> authorization(@RequestBody User user) {
        return userService.authorization(user);
    }
}
