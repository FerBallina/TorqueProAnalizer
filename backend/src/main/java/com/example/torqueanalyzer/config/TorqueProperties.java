package com.example.torqueanalyzer.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.example.torqueanalyzer.model.Threshold;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "torque")
public class TorqueProperties {

    private FieldConfig fields;

    private List<Integer> graphColumns =
            new ArrayList<>();

    private Map<String, Threshold> thresholds =
            new HashMap<>();

}