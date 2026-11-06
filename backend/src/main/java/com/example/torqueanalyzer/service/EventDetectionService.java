package com.example.torqueanalyzer.service;

import com.example.torqueanalyzer.dto.EventDto;
import com.example.torqueanalyzer.model.Threshold;
import com.example.torqueanalyzer.model.TorqueLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de detectar eventos, alertas o anomalías dentro del log.
 *
 * Recibe el `TorqueLog` y los thresholds configurados para evaluar cada sensor.
 * En el futuro puede devolver una lista de eventos con severidad, fila y valor
 * asociado para renderizar alertas o métricas al cliente.
 */
@Service
public class EventDetectionService {

    public List<EventDto> detect(
            TorqueLog log,
            Map<String, Threshold> thresholds
    ) {

        return new ArrayList<>();

    }

}