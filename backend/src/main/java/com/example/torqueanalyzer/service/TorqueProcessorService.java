package com.example.torqueanalyzer.service;

import com.example.torqueanalyzer.model.TorqueTelemetry;
import com.example.torqueanalyzer.repository.TorqueTelemetryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
/*
clase para generar la telemetria de torque y guardarla en la base de datos.
posee un buffer de tipo cola para almacenar los registros de telemetría antes de guardarlos en la base de datos.
duracion de la cola de buffer es configurable mediante la propiedad "telemetria.volcado-ms" en el archivo de configuración YAML de la aplicación.
*/
/**
 * Servicio de procesamiento en tiempo real para telemetría del vehículo.
 *
 * Recibe datos en vivo desde el dispositivo, los encola en memoria y luego los
 * vuelca periódicamente a PostgreSQL usando un `@Scheduled`. La cola evita perder
 * mensajes si la base de datos está momentáneamente caída.
 * 
 * usa TorqueTelemetryRepository que extiende de JpaRepository clase encargada de guardar en postgres
 */
@Service
public class TorqueProcessorService {

    private static final Logger log = LoggerFactory.getLogger(TorqueProcessorService.class);
    private final TorqueTelemetryRepository repository;

    private final Queue<TorqueTelemetry> bufferCola = new ConcurrentLinkedQueue<>();

    public TorqueProcessorService(TorqueTelemetryRepository repository) {
        this.repository = repository;
    }

    public String processRealTimeData(Map<String, String> allParams) {
        if (allParams.containsKey("eml")) {
            try {
                String email = allParams.get("eml");
                String session = allParams.get("session");
                Long time = allParams.get("time") != null ? Long.parseLong(allParams.get("time")) : System.currentTimeMillis();

                TorqueTelemetry registro = new TorqueTelemetry(email, session, time, allParams);
                bufferCola.add(registro);

                log.debug("Registro encolado. Tamaño actual del buffer: {}", bufferCola.size());
            } catch (Exception e) {
                log.error("Error al encolar los datos de Torque: ", e);
            }
        }
        return "OK!";
    }

    @Scheduled(fixedDelayString = "${telemetria.volcado-ms}")
    @Transactional
    public void vaciarBufferAPostgres() {
        if (bufferCola.isEmpty()) {
            return;
        }

        log.info("Iniciando volcado a Postgres. Registros a procesar: {}", bufferCola.size());
        List<TorqueTelemetry> loteParaGuardar = new ArrayList<>();
        TorqueTelemetry elemento;

        while ((elemento = bufferCola.poll()) != null) {
            loteParaGuardar.add(elemento);
        }

        try {
            repository.saveAll(loteParaGuardar);
            log.info("Se guardaron exitosamente {} registros en Postgres.", loteParaGuardar.size());
        } catch (Exception e) {
            log.error("Error crítico al guardar el lote en Postgres. Reencolando datos...", e);
            // Si la base de datos se cayó, devuelvo los datos al buffer para no perder telemetría
            bufferCola.addAll(loteParaGuardar);
        }
    }
}
