package com.example.torqueanalyzer.service;

import java.util.Map;
/*
* Interfaz para poder desconectar la DB en caso de que no se necesite traer nada y solamente se
* este utilizando para procesar files
* en yaml hay una flag para poder usarlo
*
* Termina levantando TorqueODB2ProcessorService  o TorqueODB2OfflineProcessorService  dependiendo del flag
*/
public interface TorqueODB2TelemetryService {
    String processRealTimeData(Map<String, String> allParams);
}
