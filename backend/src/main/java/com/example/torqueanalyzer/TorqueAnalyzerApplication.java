package com.example.torqueanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.torqueanalyzer.config.TorqueProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
/*
@OpenAPIDefinition(
    servers = {
        @Server(url = "https://bookish-telegram-xrrw5xj6wvpx3qw9-8080.app.github.dev")
    }
)
*/
@SpringBootApplication
@EnableConfigurationProperties(
        TorqueProperties.class)
public class TorqueAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                TorqueAnalyzerApplication.class,
                args);
    }

}