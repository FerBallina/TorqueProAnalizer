package com.example.torqueanalyzer.repository;

import com.example.torqueanalyzer.model.TorqueTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de persistencia para la entidad `TorqueTelemetry`.
 *
 * Permite guardar, consultar y administrar registros de telemetría usando Spring
 * Data JPA. Es la capa de acceso a datos responsable de almacenar los eventos
 * en la base de datos.
 */
@Repository
public interface TorqueTelemetryRepository extends JpaRepository<TorqueTelemetry, Long> {
}
