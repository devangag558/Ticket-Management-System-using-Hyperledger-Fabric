//package com.code36.userSignUp.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.MacAlgorithm;
//import javax.crypto.SecretKey;
//import org.springframework.stereotype.Component;
//import java.util.Date;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//    private static final String SECRET_KEY = "cGxhbnNlbGRvbWZyaWdodvbmNhbXBmb3Jlc3Rjb3VudG0"; // Store securely
//
//    private SecretKey getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String generateToken(String subject, Map<String, Object> claims) {
//        SecretKey key = getSigningKey();
//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        
//        return Jwts.builder()
//                .claims(claims)
//                .subject(subject)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30-minute expiry
//                .signWith(key, algorithm)
//                .compact();
//    }
//
//    public String extractEmail(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(getSigningKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public boolean validateToken(String token, String email) {
//        final String extractedEmail = extractEmail(token);
//        return (extractedEmail.equals(email) && !isTokenExpired(token));
//    }
//}

package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SuppressWarnings("unused")
@Component
public class JwtUtil {
    private final String SECRET_KEY = "a25vd2xlZGdlZXhjZXB0dHJ1Y2t0aHJvd21lYXN1cmVzdWRkZW5seXN3aW1ncmFwaHM="; // Securely store this key
    private final Set<String> tokenBlacklist = ConcurrentHashMap.newKeySet(); // In-memory token blacklist


    
    // Generate signing key
    private SecretKey getSigningKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate token with role
    public String generateToken(String email, String role) {
        SecretKey key = getSigningKey();
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        String jti = UUID.randomUUID().toString(); // Unique Token ID

        return Jwts.builder()
                .claim("role", role)  // Embed role inside token
                .claim("jti", jti) 
                .subject(email)       // Set email as subject
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 5* 6 * 60 * 1000)) // 5-minute expiry
                .signWith(key, algorithm)
                .compact();
    }

    // Extract claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())  // Verify with signing key
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract email from token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    // Extract jti
    public String extractJti(String token) {
//    	System.out.println(extractClaim(token, claims -> claims.get("jti", String.class)));
        return extractClaim(token, claims -> claims.get("jti", String.class));
    }

    // Extract role from token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Validate token
//    public boolean validateToken(String token, String email) {
//        return email.equals(extractEmail(token)) && !isTokenExpired(token);
//    }
    
    public void blacklistToken(String token) {
        String jti = extractJti(token);
        if (jti != null) {
            tokenBlacklist.add(jti);
            System.out.println("Token Blacklisted: " + jti);
        } else {
            System.out.println("Failed to extract JTI for token: " + token);
        }
        System.out.println("Current Blacklist: " + tokenBlacklist);
    }
    
 // Validate token with jti check
    public boolean validateToken(String token, String email) {
        String jti = extractJti(token);
        System.out.println("JTI to be checked - "+jti);

        // NEW: Reject blacklisted tokens
        if (jti != null && tokenBlacklist.contains(jti)) {
            System.out.println("Token is blacklisted: " + token);
            return false;
        }
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }
    
}
