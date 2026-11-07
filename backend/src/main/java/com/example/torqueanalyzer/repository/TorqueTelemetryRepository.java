package com.example.torqueanalyzer.repository;

import com.example.torqueanalyzer.model.TorqueTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * Repositorio de persistencia para la entidad `TorqueTelemetry`.
 *
 * Permite guardar, consultar y administrar registros de telemetría usando Spring
 * Data JPA. Es la capa de acceso a datos responsable de almacenar los eventos
 * en la base de datos.
 * 
 * el findBySessionIdOrderByDeviceTimeAsc busca por session en la db
 */
@Repository
public interface TorqueTelemetryRepository extends JpaRepository<TorqueTelemetry, Long> {
    
    List<TorqueTelemetry> findBySessionIdOrderByDeviceTimeAsc(String sessionId);

    // Usamos una consulta nativa SQL (nativeQuery = true) que PostgreSQL procesa al toque
    @Query(value = "SELECT session_id as sessionId, " +
                   "MIN(device_time) as minTime, " +
                   "MAX(device_time) as maxTime, " +
                   "COUNT(*) as recordCount " +
                   "FROM torque_telemetry " +
                   "WHERE email = :email " +
                   "AND device_time >= :fromTime " +
                   "AND device_time <= :toTime " +
                   "GROUP BY session_id " +
                   "ORDER BY MIN(device_time) DESC", 
           nativeQuery = true)
    List<SessionProjection> findSessionsByEmailAndDateRange(
            @Param("email") String email, 
            @Param("fromTime") Long fromTime, 
            @Param("toTime") Long toTime
    );

    // Interfaz de proyección interna para capturar las columnas de la consulta nativa
    interface SessionProjection {
        String getSessionId();
        Long getMinTime();
        Long getMaxTime();
        Long getRecordCount();
    }
}
