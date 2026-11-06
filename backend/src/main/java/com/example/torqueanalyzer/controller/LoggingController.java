package com.example.torqueanalyzer.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/logs")
public class LoggingController {

    @PostMapping("/level")
    public String changeLogLevel(@RequestParam String packagePath, @RequestParam String level) {
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            
            Level newLevel = Level.toLevel(level.toUpperCase(), null);
            
            if (newLevel == null) {
                return "Error: Nivel de log no válido. Usar TRACE, DEBUG, INFO, WARN o ERROR.";
            }

            loggerContext.getLogger(packagePath).setLevel(newLevel);
            
            return "Nivel de log para '" + packagePath + "' cambiado exitosamente a " + newLevel;
            
        } catch (ClassCastException e) {
            return "Error: La implementación actual de SLF4J no es Logback.";
        }
    }
}
