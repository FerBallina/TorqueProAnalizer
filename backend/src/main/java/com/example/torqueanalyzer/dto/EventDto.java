package com.example.torqueanalyzer.dto;

import lombok.Data;

@Data
/**
 * DTO para representar un evento detectado durante el análisis del log.
 *
 * Cada evento describe la fila, el sensor implicado, el valor observado, el
 * umbral relacionado y la severidad resultante. Esto permite devolver
 * alertas o indicadores visuales al front-end.
 */
public class EventDto {

    private Long row;

    private String sensor;

    private Double value;

    private Double threshold;

    private String severity;

}