package com.example.torqueanalyzer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Servicio responsable de generar, validar y extraer claims del JWT.
 *
 * Se usa para emitir tokens de sesión al hacer login y para verificar que un
 * usuario autenticado conserva permisos válidos antes de acceder a endpoints
 * protegidos. Además guarda el rol del usuario dentro del token.
 */
@Service
public class JwtService {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    private static final long EXPIRATION_TIME = 36000000; 

    // 1. Generar el token (se usa en el Login)
    public String generarToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // Guardamos el rol (ej: ROLE_ADMIN)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // 2. Validar si el token es legítimo y no expiró
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) SECRET_KEY) 
                .build()
                .parseSignedClaims(token)
                .getPayload(); 
    }

}
