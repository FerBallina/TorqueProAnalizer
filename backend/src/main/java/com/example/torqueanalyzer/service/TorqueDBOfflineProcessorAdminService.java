package com.example.torqueanalyzer.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "telemetry.odb2.enabled", havingValue = "false")
public class TorqueDBOfflineProcessorAdminService implements TorqueDBAdminService {

    @Override
    public List<Map<String, Object>> obtenerResumenMails() { return List.of(); }

    @Override
    public List<Map<String, Object>> obtenerResumenMailsDetalle() {
        return List.of();
    }

    @Override
    public String eliminarPorEmail(String email) { return "Módulo de base de datos desactivado."; }

    @Override
    public String eliminarPorSession(String sessionId) { return "Módulo de base de datos desactivado."; }

}
