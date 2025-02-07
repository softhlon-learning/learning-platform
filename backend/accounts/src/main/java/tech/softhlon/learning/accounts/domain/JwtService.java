// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * JWT service implementation.
 */
@Component
public class JwtService {

    private static final String AUTHORIZATION = "Authorization";
    private static final String ACCOUNT_ID = "accountId";
    private static final String MD5 = "MD5";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init() {

        this.key = Keys.hmacShaKeyFor(
              secret.getBytes());

    }

    /**
     * Get all claims from token.
     * @param token JWT token
     * @return Claims
     */
    public Claims getAllClaimsFromToken(
          String token) {

        return Jwts.parser()
              .setSigningKey(key).build()
              .parseClaimsJws(token)
              .getBody();

    }

    /**
     * Check if token is valid.
     * @param token JWT token
     * @return True if token is valid
     */
    public Boolean isTokenValid(
          String token) {

        try {
            return !isTokenExpired(token);
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Extract token from cookie.
     * @param request HttpServletRequest
     * @return JWT token
     */
    public String extractToken(
          HttpServletRequest request) {

        var cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTHORIZATION)) {
                return cookie.getValue();
            }
        }

        return null;

    }

    /**
     * Fetch JWT token.
     * @param token JWT token
     * @return JWT token hash
     * @throws NoSuchAlgorithmException
     */
    public String tokenHash(
          String token) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(MD5);
        md.update(token.getBytes());
        byte[] digest = md.digest();

        return DatatypeConverter
              .printHexBinary(digest)
              .toLowerCase();

    }

    /**
     * Generate JWT token
     * @param accountId Account Id
     * @param email     User's email
     * @return JWT token
     */
    public String generateToken(
          UUID accountId,
          String email) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(
              ACCOUNT_ID,
              accountId.toString());

        return doGenerateToken(claims, email);

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Boolean isTokenExpired(
          String token) {

        final Date expiration =
              getAllClaimsFromToken(token)
                    .getExpiration();

        return expiration.before(new Date());

    }

    private String doGenerateToken(
          Map<String, Object> claims,
          String username) {

        long expirationTimeLong = Long.parseLong(expirationTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(createdDate)
              .setExpiration(expirationDate)
              .signWith(key)
              .compact();

    }

}
