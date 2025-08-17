package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Customer;
import com.example.medical_shop_backend.models.User;
import com.example.medical_shop_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserAPI {

    @Autowired
    private  UserService userService;


    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAllById(@PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()){
            return new ResponseEntity<>(userOptional,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No user with that Id", HttpStatus.NOT_FOUND);
        }
    }



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
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody User user) {
        try {
            User saved = userService.saveUser(user);  // Customer extends User
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            if ("EMAIL_EXISTS".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
