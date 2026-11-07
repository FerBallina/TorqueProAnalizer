package com.example.torqueanalyzer.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.torqueanalyzer.dto.SessionHistoryDto;
import com.example.torqueanalyzer.dto.TorqueLogResponse;
/*
* Interfaz para poder desconectar la DB en caso de que no se necesite traer nada y solamente se
* este utilizando para procesar files
* en yaml hay una flag para poder usarlo
*
* Termina levantando TorqueODB2ProcessorService  o TorqueODB2OfflineProcessorService  dependiendo del flag
*/
public interface TorqueDBTelemetryService {
    ResponseEntity<TorqueLogResponse> getData(String id);
    ResponseEntity<List<SessionHistoryDto>> getSessionHistory(String email, String fromDate, String toDate);
}
