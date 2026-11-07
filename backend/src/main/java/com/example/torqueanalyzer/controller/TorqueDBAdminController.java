package com.example.torqueanalyzer.controller;

import com.example.torqueanalyzer.service.TorqueDBAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/database")
public class TorqueDBAdminController {

    private final TorqueDBAdminService adminService;

    public TorqueDBAdminController(TorqueDBAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Listar mails y cantidad de sesiones", security = { @SecurityRequirement(name = "tokenBearerAuth") })
    public ResponseEntity<List<Map<String, Object>>> getSummary() {
        return ResponseEntity.ok(adminService.obtenerResumenMails());
    }

    @GetMapping("/summaryDetailed")
    @Operation(summary = "Listar mails y cantidad de sesiones", security = { @SecurityRequirement(name = "tokenBearerAuth") })
    public ResponseEntity<List<Map<String, Object>>> getSummaryDetailed() {
        return ResponseEntity.ok(adminService.obtenerResumenMailsDetalle());
    }

    @DeleteMapping("/clean-by-email")
    @Operation(summary = "Borrar todos los registros de un email", security = { @SecurityRequirement(name = "tokenBearerAuth") })
    public ResponseEntity<String> deleteByEmail(@RequestParam String email) {
        return ResponseEntity.ok(adminService.eliminarPorEmail(email));
    }

    @DeleteMapping("/clean-by-session")
    @Operation(summary = "Borrar todos los registros de una sesión específica", security = { @SecurityRequirement(name = "tokenBearerAuth") })
    public ResponseEntity<String> deleteBySession(@RequestParam String sessionId) {
        return ResponseEntity.ok(adminService.eliminarPorSession(sessionId));
    }
}
