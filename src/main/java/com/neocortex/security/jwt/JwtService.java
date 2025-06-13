package com.neocortex.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for handling JWT operations such as token generation,
 * validation, and extraction of claims.
 */
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String rawSecretKey;

    @Value("${application.security.jwt.expiration}")
    private long accessTokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    private Key secretKey;

    /**
     * Initializes the signing key after properties are injected.
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(rawSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT access token with the given user ID as subject.
     *
     * @param userId the user's UUID as a string
     * @return generated JWT access token
     */
    public String generateAccessToken(String userId) {
        return buildToken(Map.of(), userId, accessTokenExpiration);
    }

    /**
     * Generates a JWT refresh token with the given user ID as subject.
     *
     * @param userId the user's UUID as a string
     * @return generated JWT refresh token
     */
    public String generateRefreshToken(String userId) {
        return buildToken(Map.of(), userId, refreshTokenExpiration);
    }

    /**
     * Generates a JWT token with extra claims and the given subject.
     *
     * @param extraClaims additional claims to include
     * @param userId      the subject of the token (typically the user ID)
     * @param expiration  expiration duration in milliseconds
     * @return generated JWT token
     */
    private String buildToken(Map<String, Object> extraClaims, String userId, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the user ID (subject) from the token.
     *
     * @param token JWT token
     * @return user ID as string
     */
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks if the provided token is valid for the given user ID.
     *
     * @param userId user ID to validate
     * @param token  JWT token
     * @return true if token is valid and belongs to the user
     */
    public boolean isTokenValid(String userId, String token) {
        try {
            return userId.equals(extractUserId(token)) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if the token is expired.
     *
     * @param token JWT token
     * @return true if token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Extracts a specific claim from the token using a resolver function.
     *
     * @param token          JWT token
     * @param claimsResolver function to extract the desired claim
     * @param <T>            type of the claim
     * @return extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token.
     *
     * @param token JWT token
     * @return all claims in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
