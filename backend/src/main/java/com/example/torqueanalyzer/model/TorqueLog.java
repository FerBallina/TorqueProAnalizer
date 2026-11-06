package com.example.torqueanalyzer.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
/**
 * Representa un archivo de telemetría completo cargado desde CSV.
 *
 * Guarda los encabezados del archivo y cada una de las filas como `TorqueRecord`.
 * Esta estructura centraliza la información antes de transformarla en la respuesta
 * final o de ser analizada por servicios especializados.
 */
public class TorqueLog {

    private List<String> headers =
            new ArrayList<>();

    private List<TorqueRecord> records =
            new ArrayList<>();

    /*public void info(String string, int size, int size2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'info'");
    }*/

}