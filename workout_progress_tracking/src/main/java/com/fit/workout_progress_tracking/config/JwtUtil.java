package com.fit.workout_progress_tracking.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtUtil {
    private final SecretKey secret;

    // Hardcoded Base64-encoded secret key (must match user service)
    private static final String SECRET_KEY = "eW91ci12ZXJ5LWxvbmcTc2VjcmV0LWtleS1zdHJpbmctZm9yLWhzNTEyLXNpZ25pbmctNjQtYnl0ZXMtbG9uZw==";

    public JwtUtil() {
        try {
            this.secret = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid hardcoded JWT secret key: must be valid Base64", e);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return "access".equals(claims.get("type"));
        } catch (Exception e) {
            System.out.println("Error checking token type: " + e.getMessage());
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }
}
