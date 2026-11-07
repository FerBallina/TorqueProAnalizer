package com.example.torqueanalyzer.service;

import java.util.List;
import java.util.Map;

public interface TorqueDBAdminService {
    List<Map<String, Object>> obtenerResumenMails();
    List<Map<String, Object>> obtenerResumenMailsDetalle();
    String eliminarPorEmail(String email);
    String eliminarPorSession(String sessionId);
}
