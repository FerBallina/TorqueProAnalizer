package com.example.torqueanalyzer.dto;

import lombok.Data;

@Data
/**
 * DTO con la configuración de columnas y sensores que la aplicación usa para
 * interpretar los datos del vehículo.
 * Son los valores que esperaria que no falten para poder dibujar el gps y el primer grafico
 *
 * Incluye los nombres de campos del CSV para RPM, velocidad, coolant, latitud,
 * longitud y tiempo; además guarda si el gráfico tiene autoescalado. Sirve para
 * devolver un resumen útil al cliente después del procesamiento del archivo.
 */
public class ConfigDto {

    private Boolean autoScaleEnabled;

    private String rpmField;

    private String speedField;

    private String coolantField;

    private String latitudeField;

    private String longitudeField;

    private String timeField;


}