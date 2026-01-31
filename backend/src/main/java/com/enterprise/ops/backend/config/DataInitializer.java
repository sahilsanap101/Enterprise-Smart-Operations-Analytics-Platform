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
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (userRepository.findByEmail("admin@company.com").isEmpty()) {
                userRepository.save(User.builder()
                        .email("admin@company.com")
                        .password(passwordEncoder.encode("Admin@123"))
                        .role(Role.ADMIN)
                        .active(true)
                        .build());
            }

            if (userRepository.findByEmail("manager@company.com").isEmpty()) {
                userRepository.save(User.builder()
                        .email("manager@company.com")
                        .password(passwordEncoder.encode("Manager@123"))
                        .role(Role.MANAGER)
                        .active(true)
                        .build());
            }

            if (userRepository.findByEmail("employee@company.com").isEmpty()) {
                userRepository.save(User.builder()
                        .email("employee@company.com")
                        .password(passwordEncoder.encode("Employee@123"))
                        .role(Role.EMPLOYEE)
                        .active(true)
                        .build());
            }
        };
    }
}