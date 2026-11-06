package com.example.torqueanalyzer.config;

import lombok.Data;

@Data
/**
 * Describe el nombre exacto de cada campo clave dentro del CSV de telemetría.
 *
 * Se usa para relacionar la configuración del archivo con los nombres reales de
 * columnas como `time`, `rpm`, `speed`, `coolant`, `latitude` y `longitude`.
 * Permite que la lógica del backend no dependa de nombres hardcodeados.
 */
public class FieldConfig {

    private String time;

    private String rpm;

    private String speed;

    private String coolant;

    private String latitude;

    private String longitude;

}