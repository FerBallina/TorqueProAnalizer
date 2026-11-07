package com.example.torqueanalyzer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración principal de seguridad HTTP del backend.
 *
 * Aquí se definen los endpoints públicos, se habilita JWT para proteger las
 * peticiones restantes y se declara que cualquier ruta no explícitamente pública
 * debe requerir autenticación. También se aplica el `JwtFilter` antes del
 * `UsernamePasswordAuthenticationFilter` para validar cada request.
 *
 * Puntos importantes:
 * - `/v3/api-docs/**`, `/swagger-ui/**` y `/swagger-ui.html` quedan con
 *   `permitAll()` para permitir ver la documentación sin autenticación.
 * - `/api/auth/login` y `/api/log/**` también están abiertos.
 * - El resto usa `anyRequest().authenticated()`, por lo que exige JWT válido.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Requerido para APIs REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                .requestMatchers("/api/auth/login","/api/log/**").permitAll() 
                
                .requestMatchers("/api/admin/**").hasRole("ADMIN") 
                
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 

        return http.build();
    }
}
