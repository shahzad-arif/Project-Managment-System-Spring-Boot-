package com.pms.pms.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtProvider {
    // Secret key for signing the JWT
    static SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    // Method to generate JWT token
    public static String generateToken(Authentication authentication) {
        String email = authentication.getName();

        // Getting the authorities and converting them into a comma-separated string
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String authoritiesString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours expiration
                .claim("email", email) // Store the email in the "email" claim
                .claim("authorities", authoritiesString) // Store the authorities in a claim
                .signWith(secretKey) // Sign the token with the secret key
                .compact();
    }
    public static String getEmailFromToken(String jwt){
        jwt= jwt.substring(7);
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();

        return claims.get("email", String.class);


    }
}
