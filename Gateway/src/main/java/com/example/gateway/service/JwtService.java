package com.example.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    // Encryption key HS256
    @Value("${application.security.jwt.key.hs256}")
    private String SECRET_KEY;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    public String extractMail(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            log.error("Error while extracting user mail from token: {}", e.getMessage());
            log.error("Token: {}", token);
        }

        return null;
    }

    // Claim is a pair of value (a name of value AND the value) inside te Payload of JSON
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            log.error("Error while extracting claim from token: {}", e.getMessage());
        }

        return null;
    }

    public String generateToken(UserDetails userDetails) {
        try {
            return generateToken(new HashMap<>(), userDetails, expiration);
        } catch (Exception e) {
            log.error("Error while generate token: {}", e.getMessage());
        }

        return null;
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        try {
            return Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Generate token failed: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<>(), userDetails, expiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String mail = extractMail(token);
            return mail.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Error while validating token: {}", e.getMessage());
        }

        return false;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Parameter with a token and build it with function getSigningKey()
    private Claims extractAllClaims(String token) {
        try {
            // Use the JWT parser builder to create a parser
            return Jwts
                    .parserBuilder()
                    // Set the signing key used to verify the token
                    .setSigningKey(getSigningKey())
                    // Build the parser
                    .build()
                    // Parse the token and retrieve the body, which contains the claims
                    .parseClaimsJws(token)
                    // Get the body of the parsed token, which contains the claims
                    .getBody();
        } catch (Exception e) {
            log.error("Error while extracting all claims from token: {}", e.getMessage());
        }

        return null;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        // Creates a new SecretKey instance for use with HMAC-SHA algorithms based
        // on the specified key byte array.
        try {
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            log.error("Error while getting signing key: {}", e.getMessage());
        }

        return null;
    }

}
