package com.example.torqueanalyzer.repository;

import com.example.torqueanalyzer.model.TorqueTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


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

    // 1. Trae los mails únicos y cuenta cuántas sesiones (viajes) tiene cada uno
    @Query(value = "SELECT email, COUNT(DISTINCT session_id) as sesiones_count FROM torque_telemetry GROUP BY email", nativeQuery = true)
    List<Map<String, Object>> obtenerResumenMailsYCountSesiones();

    // 1. Trae los mails únicos y cuenta cuántas sesiones (viajes) tiene cada uno
    @Query(value = "SELECT email, session_id, COUNT(session_id) FROM torque_telemetry GROUP BY email, session_id", nativeQuery = true)
    List<Map<String, Object>> obtenerResumenMailsYSesiones();

    // 2. Borrar por Email (Requiere @Modifying para avisar a JPA que altera datos)
    @Modifying
    @Query(value = "DELETE FROM torque_telemetry WHERE email = :email", nativeQuery = true)
    int borrarPorEmail(@Param("email") String email);

    // 3. Borrar por ID de Sesión específico
    @Modifying
    @Query(value = "DELETE FROM torque_telemetry WHERE session_id = :sessionId", nativeQuery = true)
    int borrarPorSession(@Param("sessionId") String sessionId);

    // Interfaz de proyección interna para capturar las columnas de la consulta nativa
    interface SessionProjection {
        String getSessionId();
        Long getMinTime();
        Long getMaxTime();
        Long getRecordCount();
    }
}
