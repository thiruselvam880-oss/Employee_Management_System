package org.example.employee_mangement_system.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECKRET_KEY_STRING="UjMdsioOROslnzNLH849XOZpBQbp4nA1";
    private static final SecretKey SECRET_KEY= Keys.hmacShaKeyFor(SECKRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(UserDetails userDetails)
    {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(SECRET_KEY,Jwts.SIG.HS256)
                .compact()
                ;
    }
    public  boolean validateToken(String token,UserDetails userDetails)
    {
        return extractUsername(token).equals(userDetails.getUsername());
    }
    public String extractUsername(String token)
    {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
