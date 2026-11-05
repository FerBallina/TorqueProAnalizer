package com.example.torqueanalyzer.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TorqueLogResponse {

    private ConfigDto config;

    private List<ColumnDefinition> columns;

    private List<Map<String, Object>> records;

    private List<EventDto> events;

}