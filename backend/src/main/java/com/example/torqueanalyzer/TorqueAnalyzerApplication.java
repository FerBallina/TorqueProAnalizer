package com.example.torqueanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.torqueanalyzer.config.TorqueProperties;

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