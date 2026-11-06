package com.example.torqueanalyzer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import com.example.torqueanalyzer.security.JwtService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${spring.security.user.name:Default}")
    private String adminUser;

    @Value("${spring.security.user.password:Default}")
    private String adminPass;

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @SecurityRequirements
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (adminUser.equals(request.getUsername()) && adminPass.equals(request.getPassword())) {            
            String token = jwtService.generarToken(request.getUsername(), "ROLE_ADMIN");    
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
    }
}

// --- DTOs ---
class LoginRequest {
    private String username;
    private String password;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

class AuthResponse {
    private String token;
    public AuthResponse(String token) { this.token = token; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
