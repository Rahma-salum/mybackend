package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.User;
import com.example.medical_shop_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserAPI {

    @Autowired
    private  UserService userService;



    // Example: Find user by email (login simulation)
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(401).build();  // Unauthorized
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create new user (generic) — you might want to create specific endpoints for Customer and Pharmacist
    @PostMapping(consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
