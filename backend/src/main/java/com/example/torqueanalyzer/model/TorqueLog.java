package com.example.torqueanalyzer.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TorqueLog {

    private List<String> headers =
            new ArrayList<>();

    private List<TorqueRecord> records =
            new ArrayList<>();

    public void info(String string, int size, int size2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'info'");
    }

}