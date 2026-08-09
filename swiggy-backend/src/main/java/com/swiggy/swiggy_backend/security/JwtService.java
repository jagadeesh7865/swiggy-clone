package com.swiggy.swiggy_backend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Generate JWT Token
     */
    public String generateToken(String username) {

        return generateToken(new HashMap<>(), username);

    }

    /**
     * Generate JWT with Extra Claims
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            String username) {

        return Jwts.builder()

                .claims(extraClaims)

                .subject(username)

                .issuedAt(new Date(System.currentTimeMillis()))

                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))

                .signWith(getSignInKey())

                .compact();

    }

    /**
     * Extract Username
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    /**
     * Extract Expiration Date
     */
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    /**
     * Extract Any Claim
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        final Claims claims = extractAllClaims(token);

        return resolver.apply(claims);

    }

    /**
     * Validate Token
     */
    public boolean isTokenValid(
            String token,
            String username) {

        final String user = extractUsername(token);

        return user.equals(username)

                && !isTokenExpired(token);

    }

    /**
     * Check Expired
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token)

                .before(new Date());

    }

    /**
     * Extract Claims
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSignInKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }

    /**
     * Secret Key
     */
    private SecretKey getSignInKey() {

        return Keys.hmacShaKeyFor(secret.getBytes());

    }

}