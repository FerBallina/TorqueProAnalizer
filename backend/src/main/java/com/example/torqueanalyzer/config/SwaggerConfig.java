package com.example.torqueanalyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${app.swagger.server-url:}")
    private String customServerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openApi = new OpenAPI();
        
        if (!customServerUrl.isEmpty()) {
            openApi.servers(List.of(new Server().url(customServerUrl)));
        }
        
        return openApi;
    }
}
