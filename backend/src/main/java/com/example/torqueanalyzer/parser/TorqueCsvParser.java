package com.example.torqueanalyzer.parser;

import com.example.torqueanalyzer.model.TorqueLog;
import com.example.torqueanalyzer.model.TorqueRecord;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@Slf4j
public class TorqueCsvParser {

    public TorqueLog parse(
            MultipartFile file
    ) throws Exception {

        TorqueLog torqueLog  = new TorqueLog();

        try (
                CSVReader reader =
                        new CSVReader(
                                new InputStreamReader(
                                        file.getInputStream(),
                                        StandardCharsets.UTF_8
                                )
                        )
        ) {

            String[] headers =
                    reader.readNext();

            if (headers == null) {
                throw new IllegalArgumentException(
                        "CSV vacío"
                );
            }

            torqueLog.setHeaders(
                        Arrays.stream(headers)
                                .map(String::trim)
                                .toList()
                );
        
        torqueLog.getHeaders().forEach(System.out::println);

            String[] row;

            while (
                    (row = reader.readNext()) != null
            ) {

                TorqueRecord record =
                        new TorqueRecord();

                for (
                        int i = 0;
                        i < headers.length;
                        i++
                ) {

                    String value =
                            i < row.length
                                    ? row[i]
                                    : "";

                    String header =
                                headers[i].trim();

                        record.getValues().put(
                                header,
                                value
                        );

                }

                torqueLog.getRecords()
                        .add(record);

            }

        }

        log.info(
                "CSV cargado. Headers={}, Records={}",
                torqueLog.getHeaders().size(),
                torqueLog.getRecords().size()
        );
        for (int i = 0; i < torqueLog.getHeaders().size(); i++) {

                System.out.println(
                        i + " -> " +
                        torqueLog.getHeaders().get(i)
                );

        }               

        return torqueLog;

    }

}