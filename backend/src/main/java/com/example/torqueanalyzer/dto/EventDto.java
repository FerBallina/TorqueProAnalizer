package com.example.torqueanalyzer.dto;

import lombok.Data;

@Data
public class EventDto {

    private Long row;

    private String sensor;

    private Double value;

    private Double threshold;

    private String severity;

}