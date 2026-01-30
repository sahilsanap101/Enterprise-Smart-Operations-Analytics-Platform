package com.enterprise.ops.backend.config;

import com.enterprise.ops.backend.user.Role;
import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByEmail("admin@company.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@company.com");
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            if (userRepository.findByEmail("manager@company.com").isEmpty()) {
                User manager = new User();
                manager.setEmail("manager@company.com");
                manager.setPassword(passwordEncoder.encode("Manager@123"));
                manager.setRole(Role.MANAGER);
                userRepository.save(manager);
            }

            if (userRepository.findByEmail("employee@company.com").isEmpty()) {
                User employee = new User();
                employee.setEmail("employee@company.com");
                employee.setPassword(passwordEncoder.encode("Employee@123"));
                employee.setRole(Role.EMPLOYEE);
                userRepository.save(employee);
            }
        };
    }
}
