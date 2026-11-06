package com.example.torqueanalyzer.controller;

import com.example.torqueanalyzer.dto.SessionHistoryDto;
import com.example.torqueanalyzer.dto.TorqueLogResponse;
import com.example.torqueanalyzer.service.TorqueCSVProcessorService;
import com.example.torqueanalyzer.service.TorqueDBProcessorService;
import com.example.torqueanalyzer.service.TorqueODB2ProcessorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador principal para las rutas de carga de logs y datos de telemetría.
 *
 * Expone endpoints para probar conectividad, subir archivos CSV y procesar datos
 * en tiempo real. Centraliza la interacción con `TorqueLogService` y
 * `TorqueProcessorService` para entregar respuestas listas para el frontend.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j 
public class LogController {

    private final TorqueCSVProcessorService torqueCSVProcessorService;
    private final TorqueODB2ProcessorService torqueODB2ProcessorService;
    private final TorqueDBProcessorService torqueDBProcessorService;

    @Operation(summary = "Test Ping", description = "es para testear si el backend esta activo")
    @GetMapping(
        value="/log/ping")
    @SecurityRequirements
    public String ping() {
        log.debug("Ping endpoint reached");
        return "pong";
    }

    @Operation(summary = "Historial de sesiones", description = "Devuelve las sesiones agrupadas de un usuario en un rango de fechas formato YYYY-MM-DD")
    @GetMapping(value="/log/history")
    @SecurityRequirements
    public ResponseEntity<List<SessionHistoryDto>> getHistory(
            @RequestParam("email") String email,
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate) {
            
            return torqueDBProcessorService.getSessionHistory(email, fromDate, toDate);
    }

    @Operation(summary = "Buscar data desde la DB", description = "Recibe data de un id y busca en la DB")
    @GetMapping(
        value="/log/liveData")
    @SecurityRequirements
    public ResponseEntity<TorqueLogResponse> liveData(@RequestParam("id") String id) {
        System.out.println("liveData endpoint reached with id: " + id);
        return torqueDBProcessorService.getData(id);
    }
    
    @Operation(summary = "Upload CSV", description = "recibe la data de un file y la procesa")
    @PostMapping(
        value = "/log/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @SecurityRequirements
    public TorqueLogResponse upload(
            @RequestParam("file")
            MultipartFile file
    ) throws Exception {
        return torqueCSVProcessorService.process(file);
    }

    @Operation(summary = "Live ODB2 data", description = "recibe en vivo la data desde el odb2")
    @GetMapping("/log/torque-live")
    @SecurityRequirements
    public String recibirDataTiempoReal(@RequestParam Map<String, String> allParams) {
        return torqueODB2ProcessorService.processRealTimeData(allParams);
    }

}