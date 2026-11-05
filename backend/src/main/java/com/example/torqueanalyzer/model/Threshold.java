package com.example.torqueanalyzer.model;

import lombok.Data;

@Data
public class Threshold {

    private Double min;
    private Double max;

    private String warningColor;
    private String dangerColor;

}