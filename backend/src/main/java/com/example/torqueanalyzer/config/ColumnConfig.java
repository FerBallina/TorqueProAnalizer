package com.example.torqueanalyzer.config;

import lombok.Data;

@Data
/**
 * Representa la configuración de columnas numéricas que se usan para interpretar
 * el archivo de telemetría y generar gráficos o cálculos de rendimiento.
 *
 * Define los campos más importantes del log: tiempo, RPM, velocidad, temperatura
 * del agua y coordenadas GPS. Estos valores se usan al mapear columnas del CSV
 * y al calcular métricas del vehículo.
 */
public class ColumnConfig {

    private Integer time;
    private Integer rpm;
    private Integer speed;
    private Integer coolant;

    private Integer latitude;
    private Integer longitude;

}