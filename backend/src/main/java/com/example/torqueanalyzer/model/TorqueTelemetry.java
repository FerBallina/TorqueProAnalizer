package com.example.torqueanalyzer.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Map;

@Entity
@Table(name = "torque_telemetry")
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

    // TODO getters and setters
}
