package com.example.torqueanalyzer.model;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
public class TorqueRecord {

    private Map<String,Object> values =
            new LinkedHashMap<>();

}