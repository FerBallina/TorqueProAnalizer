package com.example.torqueanalyzer.service;

import com.example.torqueanalyzer.config.TorqueProperties;
import com.example.torqueanalyzer.dto.*;
import com.example.torqueanalyzer.model.TorqueLog;
import com.example.torqueanalyzer.model.TorqueRecord;
import com.example.torqueanalyzer.parser.TorqueCsvParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.IntStream;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

/**
 * Servicio central del procesamiento del archivo de logs.
 *
 * Toma el CSV cargado, lo parsea, calcula tiempos relativos, arma la respuesta
 * de configuración y devuelve rows/columnas/eventos listos para el frontend.
 * Además coordina con `EventDetectionService` para identificar anomalías.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TorqueLogService {

        private final TorqueCsvParser parser;
        private final TorqueProperties properties;
        private final EventDetectionService eventDetectionService;

        public TorqueLogResponse process(
                        MultipartFile file) throws Exception {

                TorqueLog torqueLog = parser.parse(file);

                calculateElapsedSeconds(
                        torqueLog,
                        properties
                                .getFields()
                                .getTime());

                TorqueLogResponse response = new TorqueLogResponse();
                ConfigDto config = buildConfig(torqueLog);
                response.setConfig(config);
                response.setColumns(
                        IntStream
                        .range(
                                0,
                                torqueLog.getHeaders().size())
                        .mapToObj(i -> {
                                ColumnDefinition col = new ColumnDefinition();
                                col.setPosition(i);
                                col.setName(torqueLog.getHeaders().get(i));
                                return col;
                        })
                        .toList());

                response.setRecords(
                        torqueLog.getRecords()
                                .stream()
                                .filter(r -> {
                                        Map<String, Object> recordMap = r.getValues();
                                        String gpsSpeed = (String) recordMap
                                                        .get("GPS Speed (Meters/second)");
                                        return gpsSpeed != null && !"GPS Speed (Meters/second)"
                                                        .equals(gpsSpeed);
                                })
                                .map(r -> {
                                        Map<String, Object> recordMap = r.getValues();
                                        String gpsSpeed = (String) recordMap
                                                        .get("GPS Speed (Meters/second)");

                                        double valorOriginal = Double.parseDouble(gpsSpeed);
                                        double valorCalculado = valorOriginal * 3.6;
                                        recordMap.put("GPS Speed (Meters/second)",
                                                        String.valueOf(valorCalculado));
                                        return recordMap;
                                })
                                .toList());
                response.setEvents(
                                eventDetectionService.detect(
                                        torqueLog,
                                        properties.getThresholds()));
                log.info("Terminado de procesar el archivo con {} registros", torqueLog.getRecords().size());
                return response;
        }

        private ConfigDto buildConfig(
                        TorqueLog torqueLog) {
                ConfigDto dto = new ConfigDto();

                dto.setRpmField(properties.getFields().getRpm());
                dto.setTimeField(properties.getFields().getTime());
                dto.setSpeedField(properties.getFields().getSpeed());
                dto.setCoolantField(properties.getFields().getCoolant());
                dto.setLatitudeField(properties.getFields().getLatitude());
                dto.setLongitudeField(properties.getFields().getLongitude());
                return dto;
        }

        private static final DateTimeFormatter DEVICE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
                        "dd-MMM-yyyy HH:mm:ss.SSS",
                        Locale.ENGLISH);

        private LocalDateTime parseDeviceTime(
                        String value) {
                String normalized = value
                                .replace("ene.", "Jan")
                                .replace("feb.", "Feb")
                                .replace("mar.", "Mar")
                                .replace("abr.", "Apr")
                                .replace("may.", "May")
                                .replace("jun.", "Jun")
                                .replace("jul.", "Jul")
                                .replace("ago.", "Aug")
                                .replace("sep.", "Sep")
                                .replace("oct.", "Oct")
                                .replace("nov.", "Nov")
                                .replace("dic.", "Dec");
                return LocalDateTime.parse(
                                normalized,
                                DEVICE_TIME_FORMATTER);
        }

        private void calculateElapsedSeconds(
                        TorqueLog torqueLog,
                        String deviceTimeField) {
                if (!torqueLog.getHeaders()
                                .contains(deviceTimeField)) {
                        return;
                }
                LocalDateTime first = null;
                for (TorqueRecord record : torqueLog.getRecords()) {
                        if (" Device Time".equals(String.valueOf(
                                record.getValues()
                                        .get(deviceTimeField)))) {
                                continue;
                        }
                        String raw = String.valueOf(
                                record.getValues()
                                        .get(deviceTimeField));
                        LocalDateTime current;
                        try {
                                current = parseDeviceTime(raw);
                        } catch (Exception e) {
                                log.warn("Error parsing device time: {}", raw);
                                throw e;
                        }
                        if (first == null) {
                                first = current;
                        }
                        double elapsedSeconds = Duration.between(
                                        first,
                                        current).toMillis() / 1000.0;
                                record
                                .getValues()
                                .put(
                                        "elapsedSeconds",
                                        elapsedSeconds);
                }
        }
}