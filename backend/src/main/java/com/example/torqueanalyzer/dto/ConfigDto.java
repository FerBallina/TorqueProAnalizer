package com.example.torqueanalyzer.dto;

import lombok.Data;

@Data
public class ConfigDto {

    private Boolean autoScaleEnabled;

    private String rpmField;

    private String speedField;

    private String coolantField;

    private String latitudeField;

    private String longitudeField;

    private String timeField;


}