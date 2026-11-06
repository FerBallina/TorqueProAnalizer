package com.example.torqueanalyzer.model;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
/**
 * Modelo que representa una fila individual del archivo CSV de Torque.
 *
 * Cada instancia contiene un mapa `values` con nombre de columna y valor asociado.
 * Es la unidad mínima de información que luego se procesa, transforma y valida por
 * el backend.
 */
public class TorqueRecord {

    private Map<String,Object> values =
            new LinkedHashMap<>();

}