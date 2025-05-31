package com.organization.crm.modules.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${SECRET}")
    private String SECRET;

    private Key getSecret(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractId(String token){
        return extractClaims(token).getSubject();
    }

    public String generateToken(String id){
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 6 * 24 * 60 * 60 * 1000))
                .signWith(getSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
}
