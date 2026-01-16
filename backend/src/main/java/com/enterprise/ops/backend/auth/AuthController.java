package com.enterprise.ops.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;

/*
 * Controller responsible for authentication operations
 * like register and login.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection (recommended)
    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * POST /auth/login
     *
     * Authenticates user and returns JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 1️⃣ Fetch user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // 2️⃣ Compare raw password with encrypted password
        boolean passwordMatches =
                passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3️⃣ Generate JWT token
        String token = JwtUtil.generateToken(user.getEmail());

        // 4️⃣ Return token to client
        return ResponseEntity.ok(token);
    }
}
