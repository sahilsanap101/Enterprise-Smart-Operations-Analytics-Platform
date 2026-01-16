package com.enterprise.ops.backend.user;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/*
 * Service layer contains business logic.
 * Controllers should NOT contain logic.
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
     * Creates a new user with encrypted password
     */

    public User createUser(String email, String rawPassword, Role role) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .role(role)
                .active(true)
                .build();

        return userRepository.save(user);
    }
}
