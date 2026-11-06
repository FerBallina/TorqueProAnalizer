package com.example.torqueanalyzer.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.example.torqueanalyzer.model.Threshold;

import lombok.Data;

@Data
/**
 * Contenedor de propiedades externas de configuración de Torque Analyzer.
 *
 * Lee la sección `torque.*` del `application.yml` y expone en memoria los campos
 * esperados del CSV (`fields`), las columnas disponibles para gráficos
 * (`graphColumns`) y los umbrales por sensor (`thresholds`). Es una pieza
 * central para que el backend no tenga valores mágicos dentro del código.
 */
@ConfigurationProperties(prefix = "torque")
public class TorqueProperties {

    private FieldConfig fields;

    private List<Integer> graphColumns =
            new ArrayList<>();

    private Map<String, Threshold> thresholds =
            new HashMap<>();

}