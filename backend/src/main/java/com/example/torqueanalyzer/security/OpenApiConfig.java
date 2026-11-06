package com.example.torqueanalyzer.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

@Configuration
public class OpenApiConfig {

        @Value("${app.swagger.server-url:}")
    private String customServerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        final String nombreEsquema = "tokenBearerAuth";

        OpenAPI openApi = new OpenAPI();
        
        if (!customServerUrl.isEmpty()) {
            openApi.servers(List.of(new Server().url(customServerUrl)));
        }
        
        openApi
                .info(new Info()
                        .title("API de Administración de Logs")
                        .version("1.0.0")
                        .description("Consola de administración protegida con JWT"))
                .addSecurityItem(new SecurityRequirement().addList(nombreEsquema))
                .components(new Components()
                        .addSecuritySchemes(nombreEsquema, new SecurityScheme()
                                .name(nombreEsquema)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

        return openApi;
    }
}

