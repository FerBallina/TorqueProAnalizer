package com.example.torqueanalyzer.service;

import com.example.torqueanalyzer.dto.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de buscar en la db todos los registros relacionados con el id del usuario.
 */
@Service
@Slf4j
public class TorqueDBProcessorService {

        public ResponseEntity<TorqueLogResponse> getData(String id){
                
                // 1. Instanciamos tu ConfigDto real con todos sus campos
                ConfigDto mockConfig = new ConfigDto();
                mockConfig.setAutoScaleEnabled(true);
                mockConfig.setRpmField("rpm");
                mockConfig.setSpeedField("speed");
                mockConfig.setCoolantField("coolant");
                mockConfig.setLatitudeField("latitude");
                mockConfig.setLongitudeField("longitude");
                mockConfig.setTimeField("timestamp");

                // 2. Instanciamos tus ColumnDefinition reales con posición y nombre
                List<ColumnDefinition> mockColumns = new ArrayList<>();
                
                String[] fields = {"timestamp", "rpm", "speed", "coolant", "latitude", "longitude"};
                for (int i = 0; i < fields.length; i++) {
                ColumnDefinition col = new ColumnDefinition();
                col.setPosition(i);
                col.setName(fields[i]);
                mockColumns.add(col);
                }

                // 3. Generamos los registros simulados (10 puntos de datos para las gráficas)
                List<Map<String, Object>> mockRecords = new ArrayList<>();
                long baseTime = System.currentTimeMillis();
                double startLat = -34.6180; 
                double startLon = -58.4410;

                for (int i = 0; i < 30; i++) { // Corregido el límite del bucle a 10 fijo
                Map<String, Object> record = new HashMap<>();
                record.put("timestamp", baseTime + (i * 1000));
                record.put("rpm", 800 + (i * 150));
                record.put("speed", i * 8);
                record.put("coolant", 85 + (i % 3));
                record.put("latitude", startLat + (i * 0.0001));
                record.put("longitude", startLon + (i * 0.0001));
                mockRecords.add(record);
                }

                // 4. Lista de eventos vacía
                List<EventDto> mockEvents = new ArrayList<>();

                // 5. Armamos tu objeto TorqueLogResponse real
                TorqueLogResponse response = new TorqueLogResponse();
                response.setConfig(mockConfig);
                response.setColumns(mockColumns);
                response.setRecords(mockRecords);
                response.setEvents(mockEvents);

                log.debug("Encontrados {} records para el id {}", response.getRecords().size(), id);

                return ResponseEntity.ok(response);
        }
}