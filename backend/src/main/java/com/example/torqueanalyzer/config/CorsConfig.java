package com.example.torqueanalyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configura CORS para que el backend acepte peticiones desde clientes web o
 * frontends externos sin bloquear el acceso por políticas del navegador.
 *
 * Es importante porque el proyecto puede servir una API REST y un dashboard
 * distinto; aquí se habilita `/**` para cualquier endpoint y se permiten métodos,
 * headers y credenciales según la necesidad del cliente.
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry
                    .addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(false);

            }
        };
    }
}