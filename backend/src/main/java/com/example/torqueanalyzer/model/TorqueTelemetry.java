package com.example.torqueanalyzer.model;

import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Map;

/**
 * Entidad JPA que persiste la telemetría recibida por el backend.
 *
 * Guarda el email del usuario, la sesión, el timestamp del dispositivo y un JSON
 * con todos los parámetros recibidos. Sirve como almacenamiento de la información
 * en PostgreSQL para posteriores análisis o auditoría.
 */
@Entity
@Table(name = "torque_telemetry")
@Data
public class TorqueTelemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String sessionId;
    private Long deviceTime;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> allParameters;

    public TorqueTelemetry() {}

    public TorqueTelemetry(String email, String sessionId, Long deviceTime, Map<String, String> allParameters) {
        this.email = email;
        this.sessionId = sessionId;
        this.deviceTime = deviceTime;
        this.allParameters = allParameters;
    }
}
