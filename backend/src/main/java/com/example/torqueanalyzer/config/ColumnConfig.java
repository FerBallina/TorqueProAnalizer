package com.example.torqueanalyzer.config;

import lombok.Data;

@Data
public class ColumnConfig {

    private Integer time;
    private Integer rpm;
    private Integer speed;
    private Integer coolant;

    private Integer latitude;
    private Integer longitude;

}