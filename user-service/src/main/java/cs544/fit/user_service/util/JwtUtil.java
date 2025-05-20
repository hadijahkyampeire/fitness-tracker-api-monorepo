package cs544.fit.user_service.util;

import cs544.fit.user_service.security.MyUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private SecretKey secret;
    private final long expiration = 5 * 60 * 60; // 5 hours
    private final long refreshExpiration = 10 * 60 * 60 * 24; // 10 days

    private final UserDetailsService userDetailsService;

    @PostConstruct
    public void initKey() {
        // Only called once at startup — secure key
        this.secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Autowired
    public JwtUtil(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String email, Long userId, String roleName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        claims.put("userId", userId);
        claims.put("role", roleName);
        return doGenerateToken(claims, email, expiration);
    }

    public String generateRefreshToken(String email, Long userId, String roleName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        claims.put("userId", userId);
        claims.put("role", roleName);
        return doGenerateToken(claims, email, refreshExpiration);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, long expirationSeconds) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(secret, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        return parseClaims(token);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isAccessToken(String token) {
        try {
            return "access".equals(getAllClaimsFromToken(token).get("type"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            return "refresh".equals(getAllClaimsFromToken(token).get("type"));
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        if (!isAccessToken(token)) {
            return null;
        }
        String username = getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
