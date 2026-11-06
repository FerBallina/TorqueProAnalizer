package com.example.torqueanalyzer.dto;

import lombok.Data;

@Data
/**
 * DTO que representa una columna del archivo de log en el contexto de la API.
 *
 * Guarda la posición y el nombre de la columna para que el front-end o el backend
 * pueda identificar qué dato está en cada columna del CSV original.
 */
public class ColumnDefinition {

    private Integer position;

    private String name;

}