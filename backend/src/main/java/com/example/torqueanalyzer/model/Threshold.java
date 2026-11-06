package com.example.torqueanalyzer.model;

import lombok.Data;

@Data
/**
 * Modelo que define el rango operativo de un sensor o valor medido.
 *
 * Se utiliza para marcar cuando una medida está dentro del rango normal, en
 * advertencia o en peligro. Los colores reportados ayudan a la UI a diferenciar
 * el nivel de severidad visualmente.
 */
public class Threshold {

    private Double min;
    private Double max;

    private String warningColor;
    private String dangerColor;

}