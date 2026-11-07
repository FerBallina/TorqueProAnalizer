package com.example.torqueanalyzer.service;

import com.example.torqueanalyzer.repository.TorqueTelemetryRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "telemetry.odb2.enabled", havingValue = "true", matchIfMissing = true)
public class TorqueDBProcessorAdminService implements TorqueDBAdminService {

    private final TorqueTelemetryRepository repository;

    public TorqueDBProcessorAdminService(TorqueTelemetryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Map<String, Object>> obtenerResumenMails() {
        return repository.obtenerResumenMailsYCountSesiones();
    }

    @Override
    public List<Map<String, Object>> obtenerResumenMailsDetalle() {
        return repository.obtenerResumenMailsYSesiones();
    }

    @Override
    @Transactional
    public String eliminarPorEmail(String email) {
        int filasBorradas = repository.borrarPorEmail(email);
        return "Se eliminaron " + filasBorradas + " registros asociados al email: " + email;
    }

    @Override
    @Transactional
    public String eliminarPorSession(String sessionId) {
        int filasBorradas = repository.borrarPorSession(sessionId);
        return "Se eliminaron " + filasBorradas + " registros de la sesión: " + sessionId;
    }
}
