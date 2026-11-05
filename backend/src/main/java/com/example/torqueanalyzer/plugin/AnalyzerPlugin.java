package com.example.torqueanalyzer.plugin;

import com.example.torqueanalyzer.model.TorqueLog;

public interface AnalyzerPlugin {

    String getName();

    void analyze(
            TorqueLog log);

}