package com.example.torqueanalyzer.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/*
* Representa cada session encontrada en la base de datos
*/

public class SessionHistoryDto {
    private String sessionId;
    private String deviceId;
    private String startTime;
    private String endTime;
    private Long recordCount;

    // Formateador estático para dejar las fechas lindas en el Front (ej: 06/11/2026 18:05:21)
    private static final DateTimeFormatter formatter = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

    public SessionHistoryDto(String sessionId, String deviceId, Long minTime, Long maxTime, Long recordCount) {
        this.sessionId = sessionId;
        this.deviceId = deviceId;
        this.recordCount = recordCount;
        
        // Convertimos los milisegundos UTC de la DB a texto formateado en la zona horaria local
        this.startTime = formatter.format(Instant.ofEpochMilli(minTime));
        this.endTime = formatter.format(Instant.ofEpochMilli(maxTime));
    }

    // Getters y Setters obligatorios para Jackson
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public Long getRecordCount() { return recordCount; }
    public void setRecordCount(Long recordCount) { this.recordCount = recordCount; }
}
