package com.example.torqueanalyzer.repository;

import com.example.torqueanalyzer.model.TorqueTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorqueTelemetryRepository extends JpaRepository<TorqueTelemetry, Long> {
}
