package com.example.medical_shop_backend.configuration;

import com.example.medical_shop_backend.models.Admin;
import com.example.medical_shop_backend.models.Role;
import com.example.medical_shop_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupRunner {

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository) {
        return args -> {
            String adminEmail = "admin@medicalshop.com";

            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                Admin admin = new Admin();
                admin.setName("Super Admin");
                admin.setEmail(adminEmail);
                admin.setPassword("admin123"); // 🔐 You should hash this!
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
                System.out.println("✅ Admin user created.");
            } else {
                System.out.println("ℹ️ Admin user already exists.");
            }
        };
    }
}
