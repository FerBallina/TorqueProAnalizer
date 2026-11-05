package com.example.torqueanalyzer.controller;

import com.example.torqueanalyzer.dto.TorqueLogResponse;
import com.example.torqueanalyzer.service.TorqueLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/log")
@RequiredArgsConstructor
@Slf4j 
public class LogController {

    private final TorqueLogService torqueLogService;

    public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TorqueLogResponse upload(
            @RequestParam("file")
            MultipartFile file
    ) throws Exception {

        return torqueLogService.process(file);

    }

}