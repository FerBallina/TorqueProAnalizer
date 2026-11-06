package com.example.torqueanalyzer.controller;

import com.example.torqueanalyzer.dto.TorqueLogResponse;
import com.example.torqueanalyzer.service.TorqueLogService;
import com.example.torqueanalyzer.service.TorqueProcessorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j 
public class LogController {

    private final TorqueLogService torqueLogService;
    private final TorqueProcessorService torqueProcessorService;

    @GetMapping(
        value="/log/ping")
    @SecurityRequirements
    public String ping() {
        return "pong";
    }

    @GetMapping(
        value="/log/liveData")
    @SecurityRequirements
    public String liveData() {
        return "search in query";
    }
    
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
        return torqueLogService.process(file);
    }

    @GetMapping("/log/torque-live")
    @SecurityRequirements
    public String recibirDataTiempoReal(@RequestParam Map<String, String> allParams) {
        return torqueProcessorService.processRealTimeData(allParams);
    }

}