package com.example.torqueanalyzer.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
/**
 * DTO de respuesta para la carga y procesamiento del archivo CSV de telemetría.
 *
 * Se usa como payload principal que devuelve el backend al cliente. Incluye la
 * configuración del log, la lista de columnas, los registros procesados y los
 * eventos detectados para facilitar la representación gráfica de la información.
 */
public class TorqueLogResponse {

    private ConfigDto config;

    private List<ColumnDefinition> columns;

    private List<Map<String, Object>> records;

    private List<EventDto> events;

}