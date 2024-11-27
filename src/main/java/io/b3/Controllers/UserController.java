package io.b3.Controllers;

import io.b3.Models.User;
import io.b3.Repositories.UserRepository;
import io.b3.Services.EmailService;
import io.b3.Services.SecurityService; // Import the SecurityService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        user.setPassword(securityService.hashPassword(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, @RequestParam String to) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null && securityService.checkPassword(user.getPassword(), foundUser.getPassword())) {
            emailService.sendEmail("jeyabalan764@gmail.com","You have a new Test","Attend the Test without fail");

            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
