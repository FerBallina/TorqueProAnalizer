package com.example.torqueanalyzer.service;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.torqueanalyzer.dto.SessionHistoryDto;
import com.example.torqueanalyzer.dto.TorqueLogResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
@ConditionalOnProperty(name = "telemetry.odb2.enabled", havingValue = "false")
public class TorqueDBOfflineProcessorService implements TorqueDBTelemetryService {

    @Override
    public ResponseEntity<TorqueLogResponse> getData(String id) {
        log.warn("Módulo DB Desactivado de forma segura. no se puede querear.");
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<SessionHistoryDto>> getSessionHistory(String email, String fromDate, String toDate) {
        log.warn("Módulo DB Desactivado de forma segura. no se puede querear.");
        return ResponseEntity.badRequest().build();
    }
}
