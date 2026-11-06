package com.example.torqueanalyzer.service;

import com.example.torqueanalyzer.dto.*;
import com.example.torqueanalyzer.model.TorqueTelemetry;
import com.example.torqueanalyzer.repository.TorqueTelemetryRepository;
import com.example.torqueanalyzer.repository.TorqueTelemetryRepository.SessionProjection;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Servicio encargado de buscar en la db todos los registros relacionados con el id del usuario.
 */
@Service
@Slf4j
// 1. Vinculamos el prefijo de tu YAML de forma nativa
@ConfigurationProperties(prefix = "torquelive") 
public class TorqueDBProcessorService {

        private final TorqueTelemetryRepository repository;

        // 2. Spring va a buscar automáticamente el nodo "fields" bajo "torqueLive"
        // Inicializamos con un HashMap vacío para asegurar que NUNCA sea null, pase lo que pase
        private Map<String, String> fields = new HashMap<>();

        public TorqueDBProcessorService(TorqueTelemetryRepository repository) {
                this.repository = repository;
        }

        // 3. Setter obligatorio que necesita Spring para inyectar las propiedades desde el YAML
        public void setFields(Map<String, String> fields) {
                this.fields = fields;
        }

        public ResponseEntity<TorqueLogResponse> getData(String id){
                log.info("Buscando registros en DB para la sesión: {}", id);
                
                List<TorqueTelemetry> telemetrias = repository.findBySessionIdOrderByDeviceTimeAsc(id);

                if (telemetrias.isEmpty()) {
                        log.warn("No se encontraron registros de telemetría para el sessionId: {}", id);
                        return ResponseEntity.notFound().build();
                }

                Set<String> llavesUnicasMapeadas = new HashSet<>();
                llavesUnicasMapeadas.add("timestamp");

                List<Map<String, Object>> records = new ArrayList<>();

                for (TorqueTelemetry telemetria : telemetrias) {
                        Map<String, Object> record = new HashMap<>();
                        record.put("timestamp", telemetria.getDeviceTime());

                        Map<String, String> params = telemetria.getAllParameters();
                        log.debug("Cantidad de PID encontrados:{}",params.size());

                        if (params != null) {
                                for (Map.Entry<String, String> entry : params.entrySet()) {
                                        String pidKey = entry.getKey();
                                        String pidValue = entry.getValue();

                                        if ("session".equals(pidKey) || "eml".equals(pidKey) || "time".equals(pidKey) || "v".equals(pidKey) || "id".equals(pidKey)) {
                                                continue;
                                        }

                                        // 4. Usamos la variable 'fields' que ahora se llena desde el YAML de forma segura
                                        String finalKeyName = this.fields.getOrDefault(pidKey, pidKey);
                                        
                                        if ("Latitude".equalsIgnoreCase(finalKeyName)) finalKeyName = "latitude";
                                        if ("Longitude".equalsIgnoreCase(finalKeyName)) finalKeyName = "longitude";
                                        
                                        if ("k4".equals(pidKey)) finalKeyName = "rpm";
                                        if ("kd".equals(pidKey)) finalKeyName = "speed";
                                        if ("k5".equals(pidKey)) finalKeyName = "coolant";

                                        llavesUnicasMapeadas.add(finalKeyName);
                                        record.put(finalKeyName, parseNumericSafe(pidValue));
                                }
                        }
                        records.add(record);
                }

                List<ColumnDefinition> columns = new ArrayList<>();
                int position = 0;
                for (String nombreColumna : llavesUnicasMapeadas) {
                        ColumnDefinition col = new ColumnDefinition();
                        col.setPosition(position++);
                        col.setName(nombreColumna);
                        columns.add(col);
                }

                ConfigDto config = new ConfigDto();
                config.setAutoScaleEnabled(true);
                config.setTimeField("timestamp");
                config.setRpmField("rpm");
                config.setSpeedField("speed");
                config.setCoolantField("coolant");
                config.setLatitudeField("latitude");
                config.setLongitudeField("longitude");

                List<EventDto> events = new ArrayList<>();
                TorqueLogResponse response = new TorqueLogResponse();
                response.setConfig(config);
                response.setColumns(columns);
                response.setRecords(records);
                response.setEvents(events);

                log.info("Procesados con éxito {} registros desde DB para la sesión {}", records.size(), id);

                return ResponseEntity.ok(response);
        }

        private Object parseNumericSafe(String value) {
                if (value == null || value.trim().isEmpty()) {
                        return 0.0;
                }
                try {
                        return Double.parseDouble(value);
                } catch (NumberFormatException e) {
                        return value;
                }
        }

        public ResponseEntity<List<SessionHistoryDto>> getSessionHistory(String email, String fromDate, String toDate) {
                log.info("Buscando historial de sesiones para: {} entre {} y {}", email, fromDate, toDate);
                try {
                        // Parseamos las fechas ISO que vienen del front. 
                        // Ej. de formato esperado: "2026-11-06T00:00:00"
                        // 1. Parseamos como fecha pura (sin hora)
                        LocalDate start = LocalDate.parse(fromDate); // Recibe "2026-11-01"
                        LocalDate end = LocalDate.parse(toDate);     // Recibe "2026-11-06"

                        // 2. Usamos la zona horaria del servidor para calcular los milisegundos exactos
                        ZoneId zone = ZoneId.systemDefault();

                        // Primer milisegundo del día de inicio (00:00:00.000)
                        long fromMillis = start.atStartOfDay(zone).toInstant().toEpochMilli();

                        // Último milisegundo del día de fin (23:59:59.999)
                        long toMillis = end.atTime(23, 59, 59, 999).atZone(zone).toInstant().toEpochMilli();

                        // 1. Obtenemos las filas agrupadas desde la consulta nativa de Postgres
                        List<SessionProjection> proyecciones = repository.findSessionsByEmailAndDateRange(email, fromMillis, toMillis);
                        
                        // 2. Mapeamos las proyecciones a tus DTOs finales convirtiendo los tiempos a texto legible
                        List<SessionHistoryDto> historial = new ArrayList<>();
                        for (SessionProjection p : proyecciones) {
                                SessionHistoryDto dto = new SessionHistoryDto(
                                        p.getSessionId(),
                                        "Dispositivo", // Fallback fijo o podés remover el deviceId de tu DTO si no lo usás
                                        p.getMinTime(),
                                        p.getMaxTime(),
                                        p.getRecordCount()
                                );
                                historial.add(dto);
                        }

                        return ResponseEntity.ok(historial);
                } catch (Exception e) {
                        log.error("Error crítico al procesar el rango de fechas para el historial", e);
                        return ResponseEntity.badRequest().build();
                }

        }
}
