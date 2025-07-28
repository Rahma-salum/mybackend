package com.example.medical_shop_backend.services;

import com.example.medical_shop_backend.models.User;
import com.example.medical_shop_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;


    // Find user by email (for login)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Save a new user (Customer or Pharmacist)
    public User saveUser(User user) {
        return userRepository.save(user);
    }


}
