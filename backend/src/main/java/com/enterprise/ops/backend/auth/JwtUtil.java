package com.enterprise.ops.backend.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/*
 * Utility class for JWT token generation.
 * Uses jjwt 0.11+ recommended secure key handling.
 */
public class JwtUtil {

    // Secret key used to sign JWT
    // In production, ALWAYS store this in env variables
    private static final String SECRET_KEY = "enterprise-smart-ops-secret-key-123456";

    // Token validity: 10 hours
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 10;

    /*
     * Generate JWT token for authenticated user
     */
    public static String generateToken(String email) {

        // Convert secret string into a secure HMAC key
        Key key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
                // Subject = unique user identifier
                .setSubject(email)

                // Token creation time
                .setIssuedAt(new Date())

                // Token expiry time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

                // Sign JWT with secure key and algorithm
                .signWith(key, SignatureAlgorithm.HS256)

                // Build final token
                .compact();
    }
}
