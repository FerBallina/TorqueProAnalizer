package com.example.torqueanalyzer.model;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
public class TorqueRecordLive {

    private Map<String,String> values =
            new LinkedHashMap<>();

}