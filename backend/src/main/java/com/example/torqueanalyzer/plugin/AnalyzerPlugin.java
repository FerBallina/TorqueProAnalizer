package com.example.torqueanalyzer.plugin;

import com.example.torqueanalyzer.model.TorqueLog;

/**
 * Contrato para plugins que analizan logs de telemetría.
 *
 * Cualquier implementación debe exponer un nombre y definir cómo procesa un
 * `TorqueLog`. Este patrón permite extender el comportamiento del backend sin
 * acoplarlo a una sola lógica de análisis.
 */
public interface AnalyzerPlugin {

    String getName();

    void analyze(
            TorqueLog log);

}