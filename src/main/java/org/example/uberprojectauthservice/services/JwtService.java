package org.example.uberprojectauthservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken(Map<String, Object> payload, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry);
        final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


        return Jwts.builder()
                .issuedAt(now)
                .claims(payload)
                .expiration(expiryDate)
                .subject(email)
                .signWith(getSignKey()) // HS256 inferred from HMAC key
                .compact();
    }

    private Claims extractAllPayloads(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private Boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = extractEmail(token);
        return (userEmailFetchedFromToken.equals(email) && !isTokenExpired(token));
    }

    private String extractPhoneNumber(String token) {
        Claims claims = extractAllPayloads(token);
        String phoneNumber = (String) claims.get("phoneNumber");
        return phoneNumber;
    }

    private Object extractPayload(String token, String payloadKey) {
        Claims claims = extractAllPayloads(token);
        return (Object) claims.get(payloadKey);

    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("password", "admin");
        mp.put("email", "a@b.com");
        mp.put("phoneNumber", "123456789");
        String result = generateToken(mp, "aryan");
        System.out.println("Generated token: " + result);
        System.out.println("Extract Phone Number: " + extractPhoneNumber(result));
        System.out.println("Extract Phone Number: " + extractPayload(result, "email").toString());
    }
}
