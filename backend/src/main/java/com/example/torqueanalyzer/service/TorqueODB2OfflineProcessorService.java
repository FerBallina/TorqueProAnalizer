package com.example.torqueanalyzer.service;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
@ConditionalOnProperty(name = "telemetry.odb2.enabled", havingValue = "false")
public class TorqueODB2OfflineProcessorService implements TorqueODB2TelemetryService {

    @Override
    public String processRealTimeData(Map<String, String> allParams) {
        log.warn("Módulo ODB2 Desactivado de forma segura. Ignorando datos en vivo.");
        return "OK!"; 
    }
}
