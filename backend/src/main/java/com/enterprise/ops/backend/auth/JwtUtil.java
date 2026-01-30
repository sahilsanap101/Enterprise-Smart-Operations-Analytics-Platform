package com.enterprise.ops.backend.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key signingKey;
    private final long expirationTime;

    public JwtUtil(
            @Value("${jwt.secret:mySuperSecretKey12345678901234567890}") String secret,
            @Value("${jwt.expiration:86400000}") long expirationTime
    ) {
        if (!StringUtils.hasText(secret) || secret.length() < 32) {
            throw new IllegalStateException(
                    "JWT secret must be at least 32 characters"
            );
        }

        if (expirationTime <= 0) {
            throw new IllegalStateException(
                    "JWT expiration must be positive"
            );
        }

        this.signingKey =
                Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;

        System.out.println("✅ JWT CONFIG LOADED SUCCESSFULLY");
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setClaims(Map.of("role", role))
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + expirationTime)
                )
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}