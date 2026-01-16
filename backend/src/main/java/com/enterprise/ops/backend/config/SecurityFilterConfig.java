package com.enterprise.ops.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Spring Security configuration for REST APIs.
 * This setup is stateless and JWT-ready.
 */
@Configuration
public class SecurityFilterConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF because we are using REST APIs (JSON)
            .csrf(csrf -> csrf.disable())

            // Disable default Spring Security login page
            .formLogin(form -> form.disable())

            // Disable HTTP Basic authentication
            .httpBasic(basic -> basic.disable())

            // VERY IMPORTANT: Make the app stateless (no sessions)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow ALL auth-related endpoints
                .requestMatchers("/auth/**").permitAll()

                // Any other endpoint must be authenticated (later via JWT)
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
